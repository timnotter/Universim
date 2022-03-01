package universim.abstractClasses;

import universim.ui.GameDisplay;

public abstract class Body {
	protected int x;
	protected int y;
	
	public Body(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int setX(int x) {
		this.x = x;
		return x;
	}
	public int setY(int y) {
		this.y = y;
		return y;
	}
	public abstract void update(GameDisplay gameDisplay);
}
