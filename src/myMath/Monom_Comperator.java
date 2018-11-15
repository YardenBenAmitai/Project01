package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {

	// ******** add your code below *********
	
	/**
	 * the method receives two Monoms and compares between them to see which has the greater power
	 * @param Monom m1, Monom m2
	 * @return the method returns 0 if they are equal, 1 if the power of m1 is greater than m2, and 2 if the power of m2 is greater than m1.
	 */
	public int compare (Monom m1, Monom m2){
		if (m1.get_power()<m2.get_power())
			return -1;
		if (m1.get_power()>m2.get_power())
			return 1;
		return 0;
	}

}
