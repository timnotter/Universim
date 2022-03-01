package universim.abstractClasses;

import java.util.ArrayList;

import universim.generalClasses.Maths;
import universim.ui.GameDisplay;


public abstract class SubTrabant extends CelestialBody{
	protected Star parentStar;
	protected Trabant parentTrabant;
	protected double relX;		
	protected double relY;
	protected double relSX;		
	protected double relSY;
	protected int counter;
	
	protected double temperature;	//In Kelvin
	
	protected double orbitRadius;	//In AU
	protected double speed;			//Frequency of orbits - orbits per day
	protected double speedDeg;		//Change in orbit - degree/day
	protected double deg;			//Current degree of orbit to starting position
	protected int period;			//Orbital period - day/orbit - rounded for practical reasons
	protected ArrayList<SubTrabant> subTrabants;
	
	public SubTrabant(double size, double mass, double orbitRadius, Trabant parentTrabant) {
		super(0, 0, size, mass);
		this.orbitRadius = orbitRadius;
		this.parentTrabant = parentTrabant;
		
		if(parentTrabant!=null) {
			setXY();
			calculateSpeed();
			parentStar = parentTrabant.getParentStar();
		}
		
		oRR = 384399000;					//Orbit Radius Reference - Distance Earth Moon
		rR = 1737400;						//Radius Reference - Moon Radius
		mR = 7.342 * Math.pow(10, 22);		//Mass Reference - Moon Mass
	}
	
	public void setXY() {		
		int ranDeg = (int)(Math.random()*360);
		double tempX = orbitRadius * Maths.pixelPerEM;
//		System.out.println("PPEM: " + Maths.pixelPerEM);
		double tempY = 0;
		
		relSX = tempX*Math.cos(ranDeg) - tempY*Math.sin(ranDeg);
		relSY = tempX*Math.sin(ranDeg) + tempY*Math.cos(ranDeg);
		relX = relSX;
		relY = relSY;
		
		if(parentTrabant!=null) {
			x = (int)relX + parentTrabant.getX();
			y = (int)relY + parentTrabant.getY();
		}
	}
	
	public void calculateSpeed() {
//		System.out.println("Calculate Speed");
		if(parentTrabant==null)
			return;
//		System.out.println("parentMass: " + parentStar.getMass());
//		System.out.println("orbitRadius: " + orbitRadius);
		double parentMassKg = parentTrabant.getMass();
		double orbitRadiusM = orbitRadius * oRR;
		double G = 6.67430 * Math.pow(10, -11);
//		System.out.println("G: " + G);
		double speedMS = Math.sqrt(G*parentMassKg/orbitRadiusM);		//Speed int m/s
		double orbitLength = 2 * Math.PI * orbitRadiusM;
//		System.out.println("speed: " + speedMS + "m/s");
//		System.out.println("orbitLength: " + orbitLength + "m");
		speed = speedMS/orbitLength * 86400;							//orbits/s * second per day
		speedDeg = 360*speed;
		period = (int)(1/speed);
//		System.out.println("Period: " + period + ", Speed: " + speed + ", SpeedDeg: " + speedDeg);
	}
	
	@Override
	public void update(GameDisplay gameDisplay) {
		deg += speedDeg/24;				//Divided by 24 for hourly updates
		if(deg>=360)
			deg -= 360;
		double degRad = deg / 180 * Math.PI;
		
		relX = relSX*Math.cos(degRad) - relSY*Math.sin(degRad);
		relY = relSX*Math.sin(degRad) + relSY*Math.cos(degRad);
		
		x = (int)(relX * gameDisplay.getScale()) + parentTrabant.getX();
		y = (int)(relY * gameDisplay.getScale()) + parentTrabant.getY();
	}
	
	public void detPos(GameDisplay gameDisplay) {
		if(parentTrabant==null)
			return;
		x = (int)(relX * gameDisplay.getScale()) + parentTrabant.getX();
		y = (int)(relY * gameDisplay.getScale()) + parentTrabant.getY();
	}
	
	public Star getParentStar() {
		return parentStar;
	}
	public Star setParentStar(Star parentStar) {
		this.parentStar = parentStar;
		return parentStar;
	}
	public Trabant getParentTrabant() {
		return parentTrabant;
	}
	public Trabant setParentTrabant(Trabant t) {
		parentTrabant  = t;
		if(t==null)
			return null;
		setXY();
		calculateSpeed();
		parentStar = parentTrabant.getParentStar();
		return t;
	}
	public double getSpeed() {
		return speed;
	}
	public double setSpeed(double speed) {
		this.speed = speed;
		return speed;
	}
	public double getOrbitRadius() {
		return orbitRadius;
	}
	public double setOrbitRadius(double orbitRadius) {
		this.orbitRadius = orbitRadius;
		return orbitRadius;
	}
}
