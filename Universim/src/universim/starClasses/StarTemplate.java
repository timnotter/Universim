package universim.starClasses;

//import java.util.ArrayList;
//
//import universim.planetClasses.EarthLike;

abstract class StarTemplate /*implements Star*/{
//	Deprecated
	/*
	private int x;
	private int y;
	private double size;		//In Solar Radius
	private int temperature;	//In Kelvin
	private double mass;		//In Solar Mass
	private String classification;
	private ArrayList<Trabant> trabants;
	
	public StarTemplate(int x, int y, double size, int temperature, double mass) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.temperature = temperature;
		this.mass = mass;
		trabants = new ArrayList<Trabant>();
//		System.out.println("Set x: " + x + ", y: " + y);
	}
	
	public void addTrabant() {
//		trabants.add(new Planet(int size, int mass, double orbitRadius, boolean habitability, Star parentStar) {)
	}
	
	public abstract int temperature();
	public abstract double size(int temperature);
	
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
	public int getTemperature() {
		return temperature;
	}

	@Override
	public int setTemperature(int temperature) {
		this.temperature = temperature;
		return temperature;
	}
	
	@Override
	public String getClassification() {
		return classification;
	}
	
	@Override
	public String setClassification(String classification) {
		this.classification = classification;
		return classification;
	}

	@Override
	public void calculateClassification() {
		//TODO
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Trabant> getTrabants() {
		return trabants;
	}

	@Override
	public ArrayList<Trabant> addTrabant(Trabant t) {
		t.setParentStar(this);
		trabants.add(t);
//		System.out.println("StarTemplate addTrabant");
		return trabants;
	}
	
	@Override
	public ArrayList<Trabant> addTrabant(double size, double mass, double orbitRadius, boolean habitability) {
		Trabant t = new EarthLike(size, mass, orbitRadius, habitability);
		t.setParentStar(this);
		trabants.add(t);
		return trabants;
	}
	*/
}
