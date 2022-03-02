package universim.abstractClasses;

import universim.ui.GameDisplay;

public abstract class Body {
	//Coordinates of object: 1 = 1 lightyear
	protected double x;
	protected double y;
	
	public Body(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double setX(int x) {
		this.x = x;
		return x;
	}
	public double setY(int y) {
		this.y = y;
		return y;
	}
	public abstract void update(GameDisplay gameDisplay);
}
