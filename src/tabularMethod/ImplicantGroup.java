package tabularMethod;

import java.util.ArrayList;

public class ImplicantGroup {
	public ArrayList<Implicant> group = new ArrayList<>();
	public int numOfOnes = -1;

	public int size() {
		return this.group.size();
	}

	private boolean doneBefore(Implicant a) {
		for (int i = 0; i < group.size(); i++) {
			if (group.get(i).terms.equals(a.terms))
				return true;
		}
		return false;
	}

	public static ImplicantGroup createGroup(ImplicantGroup g1, ImplicantGroup g2) {
		ImplicantGroup ret = new ImplicantGroup();
		for (int i = 0; i < g1.size(); i++) {
			for (int j = 0; j < g2.size(); j++) {
				Implicant temp = Implicant.createImplicant(g1.group.get(i), g2.group.get(j));
				if (temp != null) {
					g1.group.get(i).taken = true;
					g2.group.get(j).taken = true;
					if (!ret.doneBefore(temp))
						ret.group.add(temp);
				}
			}
		}
		return ret;
	}

}