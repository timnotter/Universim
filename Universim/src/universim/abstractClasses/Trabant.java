package universim.abstractClasses;

import java.util.ArrayList;

import universim.generalClasses.Maths;
import universim.ui.GameDisplay;


public abstract class Trabant extends CelestialBody{
	protected Star parentStar;
	protected double relX;		
	protected double relY;
	protected double relSX;		
	protected double relSY;
	protected int counter;
	protected boolean visible;
	
	protected double temperature;	//In Kelvin
	
	protected double orbitRadius;	//In AU
	protected double speed;			//Frequency of orbits - orbits per day
	protected double speedDeg;		//Change in orbit - degree/day
	protected double deg;			//Current degree of orbit to starting position
	protected int period;			//Orbital period - day/orbit - rounded for practical reasons
	protected ArrayList<SubTrabant> subTrabants;
	
	
	
	public Trabant(double size, double mass, double orbitRadius, Star parentStar) {
		super(0, 0, size, mass);
		this.orbitRadius = orbitRadius;
		this.parentStar = parentStar;
		subTrabants = new ArrayList();
//		System.out.println("Create Trabant");
		if(parentStar!=null) {
			setXY();
			calculateSpeed();
		}
		
		oRR = 1.495979 * Math.pow(10, 11);		//Orbit Radius Reference - AU
		rR = 6371000;							//Radius Reference - Earth Radius
		mR = 5.97237 * Math.pow(10, 24);		//Mass Reference - Earth Mass
	}
	
	public void setXY() {
//		System.out.println("SetXY");
		int ranDeg = (int)(Math.random()*360);
		double tempX = orbitRadius * Maths.pixelPerAU;
//		System.out.println("PPAU: " + Maths.pixelPerAU + ", tempX: " + tempX);
		double tempY = 0;
		
		relSX = tempX*Math.cos(ranDeg) - tempY*Math.sin(ranDeg);
		relSY = tempX*Math.sin(ranDeg) + tempY*Math.cos(ranDeg);
		relX = relSX;
		relY = relSY;
		
		if(parentStar!=null) {
			x = (int)relX + parentStar.getX();
			y = (int)relY + parentStar.getY();
		}
//		System.out.println("X: " + x + ", Y: " + y);
	}
	
	public void calculateSpeed() {
//		System.out.println("Calculate Speed");
		if(parentStar==null)
			return;
//		System.out.println("parentMass: " + parentStar.getMass());
//		System.out.println("orbitRadius: " + orbitRadius);
		double parentMassKg = parentStar.getMass();
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
	}
	
	public void detPos(GameDisplay gameDisplay) {
		if(parentStar==null)
			return;
		x = (int)(relX * gameDisplay.getScale()) + parentStar.getX();
		y = (int)(relY * gameDisplay.getScale()) + parentStar.getY();
	}
	
	public Star getParentStar() {
		return parentStar;
	}
	public Star setParentStar(Star parentStar) {
		if(parentStar==null)
			return null;
		this.parentStar = parentStar;
//		System.out.println("PlanetTemplate setParentStar");
		setXY();
		calculateSpeed();
		return parentStar;
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
	public ArrayList<SubTrabant> getSubTrabants(){
		return subTrabants;
	}
	public ArrayList<SubTrabant> addSubTrabant(SubTrabant t){
		t.setParentTrabant(this);
		subTrabants.add(t);
		return subTrabants;
	}
}
