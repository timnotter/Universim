package universim.planetClasses;

import java.util.ArrayList;

public class PlanetTemplate /*implements Planet*/{
//	Deprecated
	/*
	private int x;
	private int y;
	private double relX;		
	private double relY;
	private double relSX;		
	private double relSY;
	private int counter;
	private double size;		//Radius - in Earth Radius
	private double temperature;	//In Kelvin
	private double mass;		//In Earth Masses
	private double orbitRadius;	//In AU
	private double speed;		//Frequency of orbits - orbits per day
	private double speedDeg;	//Change in orbit - degree/day
	private double deg;			//Current degree of orbit to starting position
	private int period;			//Orbital period - day/orbit - rounded for practical reasons
	private int population;
	private boolean habitability;
	private Star parentStar;
	private String classification;
	private ArrayList<SubTrabant> subTrabants;
	
	public PlanetTemplate(double size, double mass, double orbitRadius, boolean habitability, Star parentStar) {
		this.size = size;
		this.mass = mass; 
		this.orbitRadius = orbitRadius;
		this.habitability = habitability;
		this.parentStar = parentStar;
//		System.out.println("Constructor PlanetTemplate");
		calculateSpeed();
		population = 0;
		
		subTrabants = new ArrayList();
		
		//Random starting position with correct distance
		int ranDeg = (int)(Math.random()*360);
		double tempX = orbitRadius * 250;
		double tempY = 0;
		
		relSX = tempX*Math.cos(ranDeg) - tempY*Math.sin(ranDeg);
		relSY = tempX*Math.sin(ranDeg) + tempY*Math.cos(ranDeg);
		relX = relSX;
		relY = relSY;
		
		if(parentStar!=null) {
			x = (int)relX + parentStar.getX();
			y = (int)relY + parentStar.getY();
		}
	}
	public PlanetTemplate(double size, double mass, double orbitRadius, boolean habitability) {
		this(size, mass, orbitRadius, habitability, null);
	}
	
	public void calculateSpeed() {
//		System.out.println("Calculate Speed");
		if(parentStar==null)
			return;
//		System.out.println("parentMass: " + parentStar.getMass());
//		System.out.println("orbitRadius: " + orbitRadius);
		double parentMassKg = parentStar.getMass() * 1.989 * Math.pow(10, 30);
		double orbitRadiusM = orbitRadius * 149597870700.0;
		double G = 6.67430 * Math.pow(10, -11);
//		System.out.println("G: " + G);
		double speedMS = Math.sqrt(G*parentMassKg/orbitRadiusM);		//Speed int m/s
		double orbitLength = 2 * Math.PI * orbitRadiusM;
//		System.out.println("speed: " + speedMS + "m/s");
//		System.out.println("orbitLength: " + orbitLength + "m");
		speed = speedMS/orbitLength * 86400;							//orbits/s * second per day
		speedDeg = 360*speed;
		period = (int)(1/speed);
		System.out.println("Period: " + period + ", Speed: " + speed + ", SpeedDeg: " + speedDeg);
		
	}

	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public int setX(int x) {
		this.x = x;
		return x;
	}
	
	@Override
	public int setY(int y) {
		this.y = y;
		return y;
	}

	@Override
	public void update() {
		deg += speedDeg/24;				//Divided by 24 for hourly updates
		if(deg>=360)
			deg -= 360;
		double degRad = deg / 180 * Math.PI;
		
		relX = relSX*Math.cos(degRad) - relSY*Math.sin(degRad);
		relY = relSX*Math.sin(degRad) + relSY*Math.cos(degRad);
		
		x = (int)relX + parentStar.getX();
		y = (int)relY + parentStar.getY();
	}

	@Override
	public double getSize() {
		return size;
	}

	@Override
	public double setSize(double size) {
		this.size = size;
		return size;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public double setMass(double mass) {
		this.mass = mass;
		return mass;
	}

	@Override
	public Star getParentStar() {
		return parentStar;
	}

	@Override
	public Star setParentStar(Star parentStar) {
		if(parentStar==null)
			return null;
		this.parentStar = parentStar;
//		System.out.println("PlanetTemplate setParentStar");
		calculateSpeed();
		x = (int)relX + parentStar.getX();
		y = (int)relY + parentStar.getY();
		return parentStar;
	}

	@Override
	public ArrayList<SubTrabant> getSubTrabants() {
		return subTrabants;
	}

	@Override
	public ArrayList<SubTrabant> addSubTrabant(SubTrabant t) {
		t.setParentTrabant(this);
		subTrabants.add(t);
		return subTrabants;
	}

	@Override
	public int getPopulation() {
		return population;
	}

	@Override
	public int setPopulation(int population) {
		this.population = population;
		return population;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public double setSpeed(double speed) {
		this.speed = speed;
		return speed;
	}

	@Override
	public double getOrbitRadius() {
		return orbitRadius;
	}

	@Override
	public double setOrbitRadius(double orbitRadius) {
		this.orbitRadius = orbitRadius;
		return orbitRadius;
	}

	@Override
	public boolean getHabitability() {
		return habitability;
	}

	@Override
	public boolean setHabitability(boolean habitability) {
		this.habitability = habitability;
		return habitability;
	}
	*/
}
