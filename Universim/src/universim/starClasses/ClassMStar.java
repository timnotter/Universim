package universim.starClasses;

import universim.abstractClasses.Star;

public class ClassMStar extends Star{

	public ClassMStar(int x, int y, int size, int temperature, int mass) {
		super(x, y, size, temperature, mass);
	}

	public int temperature() {
		return 0;
	}

	public double size(int temperature) {
		return 0;
	}

}
