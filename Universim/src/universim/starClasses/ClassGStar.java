package universim.starClasses;

import universim.abstractClasses.Star;

public class ClassGStar extends Star{
	//Main-sequence star - yellow dwarf - 
	//Mass: 0.9 - 1.1 solar mass
	//Temperature: 5'300K - 6'000K
	public ClassGStar(int x, int y, int size, int temperature, int mass) {
		super(x, y, size, temperature, mass);
	}

	public int temperature() {
		return 0;
	}

	public double size(int temperature) {
		return 0;
	}
}
