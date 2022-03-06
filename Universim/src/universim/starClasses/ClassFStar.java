package universim.starClasses;

import universim.abstractClasses.Star;

public class ClassFStar extends Star{

	public ClassFStar(double x, double y, double size, int temperature, double mass) {
		super(x, y, size, temperature, mass);
	}

	public int temperature() {
		return 0;
	}

	public double size(int temperature) {
		return 0;
	}
	
}
