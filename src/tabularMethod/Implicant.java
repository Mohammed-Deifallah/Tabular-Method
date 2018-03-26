package tabularMethod;

import java.util.HashSet;
import java.util.Set;

public class Implicant {
	public String binary = new String();
	public Set<Integer> terms = new HashSet<>();
	public boolean taken = false;

	public Implicant(String binary, Set<Integer> terms, boolean taken) {
		this.binary = binary;
		this.terms = terms;
		this.taken = taken;
	}

	public int countOnes() {
		int counter = 0;
		for (int i = 0; i < this.binary.length(); i++)
			if (this.binary.charAt(i) == '1')
				counter++;
		return counter;
	}

	public static Implicant createImplicant(Implicant first, Implicant second) {
		String newBinary = "";
		String x = first.binary;
		String y = second.binary;
		int counter = 0;
		for (int i = 0; i < x.length(); i++) {
			if ((x.charAt(i) == '-' && y.charAt(i) != '-') || (x.charAt(i) != '-' && y.charAt(i) == '-')) {
				return null;
			} else if ((x.charAt(i) == '1' && y.charAt(i) == '0') || (x.charAt(i) == '0' && y.charAt(i) == '1')) {
				counter++;
				newBinary += '-';
			} else
				newBinary += (x.charAt(i));
			if (counter > 1)
				return null;
		}
		Set<Integer> newTerms = new HashSet<>();
		for (int element : first.terms)
			newTerms.add(element);

		for (int element : second.terms)
			newTerms.add(element);

		return new Implicant(newBinary, newTerms, false);
	}
}