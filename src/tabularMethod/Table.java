package tabularMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Table {
	private ArrayList<ArrayList<ImplicantGroup>> table = new ArrayList<>();
	private ArrayList<Implicant> primes = new ArrayList<>();
	private ArrayList<Integer> expression = new ArrayList<>();
	private ArrayList<String> Ans = new ArrayList<>();
	private ArrayList<ArrayList<String>> allAnswers = new ArrayList<>();
	private HashMap<Integer, Integer> hashAnswer = new HashMap<>();
	private boolean[] taken;
	int counter = 0, maxBinaryLength = 0;

	public void initialize(String expression, String dontCare) {
		ArrayList<Implicant> initialExpression = new ArrayList<>();
		Pattern numPattern = Pattern.compile("\\d+");
		String totalExpression = expression + " " + dontCare;
		Matcher numMatcher = numPattern.matcher(totalExpression);
		while (numMatcher.find()) {
			String num = numMatcher.group();
			Integer temp = Integer.valueOf(num);
			String binary = Integer.toBinaryString(temp);
			maxBinaryLength = Math.max(maxBinaryLength, binary.length());
		}
		numMatcher = numPattern.matcher(totalExpression);
		while (numMatcher.find()) {
			HashSet<Integer> decimalNum = new HashSet<>();
			String num = numMatcher.group();
			Integer temp = Integer.valueOf(num);
			if (hashAnswer.get(temp) != null)
				throw new RuntimeException("Duplicates!!");
			hashAnswer.put(temp, 0);
			decimalNum.add(temp);
			counter++;
			String binary = Integer.toBinaryString(temp);
			for (int i = binary.length(); i < maxBinaryLength; i++)
				binary = "0" + binary;
			initialExpression.add(new Implicant(binary, decimalNum, false));
		}
		numMatcher = numPattern.matcher(expression);
		while (numMatcher.find()) {
			Integer temp = Integer.valueOf(numMatcher.group());
			this.expression.add(temp);
		}
		for (int i = 0; i < Math.pow(2, maxBinaryLength); i++)
			Ans.add("");
		table.add(divideIntoGroups(initialExpression));
	}

	private ArrayList<ImplicantGroup> divideIntoGroups(ArrayList<Implicant> initial) {
		ArrayList<ImplicantGroup> ret = new ArrayList<>();
		for (int i = 0; i <= maxBinaryLength; i++) {
			ImplicantGroup temp = new ImplicantGroup();
			for (int j = 0; j < initial.size(); j++) {
				if (initial.get(j).countOnes() == i)
					temp.group.add(initial.get(j));
			}
			ret.add(temp);
		}
		return ret;
	}

	public void minimizeMyTable() {

		while (true) {
			ArrayList<ImplicantGroup> newGroup = new ArrayList<>();
			ArrayList<ImplicantGroup> currentList = table.get(table.size() - 1);
			ImplicantGroup temp;
			for (int i = 0; i < currentList.size() - 1; i++) {
				temp = ImplicantGroup.createGroup(currentList.get(i), currentList.get(i + 1));
				if (temp.size() != 0)
					newGroup.add(temp);
			}
			if (newGroup.size() != 0)
				table.add(newGroup);
			else
				break;
		}
		for (int i = 0; i < table.size(); i++) {
			for (ImplicantGroup j : table.get(i)) {
				for (Implicant k : j.group) {
					if (k.taken)
						k.binary += "  \u2713";
					else
						primes.add(k);
				}
			}
		}
		taken = new boolean[primes.size()];
		Arrays.fill(taken, new Boolean(false));
	}

	void checkAnswer(int cnt) {
		for (int i = 0; i < expression.size(); i++)
			if (hashAnswer.get(expression.get(i)) == 0)
				return;
		if (cnt < Ans.size()) {
			allAnswers.clear();
			Ans = new ArrayList<String>();
			for (int i = 0; i < taken.length; i++) {
				if (taken[i] == true)
					Ans.add(primes.get(i).binary);
			}
			allAnswers.add(Ans);
		} else if (cnt == Ans.size()) {
			Ans = new ArrayList<String>();
			for (int i = 0; i < taken.length; i++) {
				if (taken[i] == true)
					Ans.add(primes.get(i).binary);
			}
			allAnswers.add(Ans);
		}
	}

	public void getAns(int i, int cnt) {
		if (i == primes.size()) {
			checkAnswer(cnt);
			return;
		}

		for (int j : primes.get(i).terms) {
			hashAnswer.put(j, hashAnswer.get(j) + 1);
		}
		taken[i] = true;
		getAns(i + 1, cnt + 1);
		for (int j : primes.get(i).terms)
			hashAnswer.put(j, hashAnswer.get(j) - 1);
		taken[i] = false;
		getAns(i + 1, cnt);
	}

	public String printMyTable() {
		if (counter == 0)
			throw new RuntimeException("Empty Expression");

		else if (counter == Math.pow(2, maxBinaryLength))
			throw new RuntimeException("Full Expression");

		String ret = "";
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).size(); j++) {
				for (int k = 0; k < table.get(i).get(j).size(); k++) {
					ArrayList<Integer> temp = new ArrayList<>(table.get(i).get(j).group.get(k).terms);
					for (int h = 0; h < temp.size(); h++)
						ret += temp.get(h).toString() + " ";
					ret += "|" + table.get(i).get(j).group.get(k).binary + '\n';
				}
				ret += "-------------------------\n";
			}
			ret += "=========================================\n";
		}
		getAns(0, 0);
		HashSet<ArrayList<String>> duplicatesRemover = new HashSet<>(allAnswers);
		allAnswers = new ArrayList<>(duplicatesRemover);
		for (ArrayList<String> i : allAnswers) {
			for (int j = 0; j < i.size(); j++) {
				for (int k = 0; k < i.get(j).length(); k++) {
					if (i.get(j).charAt(k) == '-')
						continue;
					ret += (char) (k + (int) 'A');
					if (i.get(j).charAt(k) == '0')
						ret += "`";
				}
				if (j != i.size() - 1)
					ret += " + ";
			}
			ret += '\n';
		}
		return ret;
	}
}