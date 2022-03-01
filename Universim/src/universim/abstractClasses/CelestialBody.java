package universim.abstractClasses;

public abstract class CelestialBody extends Body{
	protected double size;
	protected double mass;
	
//	Constants
	protected double oRR;			//Orbit Radius Reference
	protected double rR;			//Radius Reference
	protected double mR;			//Mass Reference
	
	public CelestialBody(int x, int y, double size, double mass) {
		super(x, y);
		this.size = size;
		this.mass = mass;
	}
	
	public double getRelSize() {
		return size;
	}
	public double getSize() {
		return (size*rR);
	}
	public double setSize(double size) {
		this.size = size;
		return size;
	}
	public double getRelMass() {
		return mass;
	}
	public double getMass() {
		return (mass*mR);
	}
	public double setMass(double mass) {
		this.mass = mass;
		return mass;
	}
}
