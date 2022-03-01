package universim.moonClasses;


public abstract class MoonTemplate /*implements Moon*/{
//	Deprecated
	/*
	private int x;
	private int y;
	private double relX;		
	private double relY;
	private double relSX;		
	private double relSY;
	private int counter;
	private double size;		//Radius - in Moon Radius
	private double temperature;	//In Kelvin
	private double mass;		//In Moon Masses
	private double orbitRadius;	//In Earth-Moon Distance
	private double speed;		//Frequency of orbits - orbits per day
	private double speedDeg;	//Change in orbit - degree/day
	private double deg;			//Current degree of orbit to starting position
	private int period;			//Orbital period - day/orbit - rounded for practical reasons
	private int population;
	private boolean habitability;
	private Star parentStar;
	private Trabant parentTrabant;
	private String classification;
	
	public MoonTemplate(double size, double mass, double orbitRadius, boolean habitability, Trabant parentTrabant) {
		this.size = size;
		this.mass = mass; 
		this.orbitRadius = orbitRadius;
		this.habitability = habitability;
		this.parentStar = parentStar;
//		System.out.println("Constructor PlanetTemplate");
		calculateSpeed();
		population = 0;
		
		//Random starting position with correct distance
		int ranDeg = (int)(Math.random()*360);
		double tempX = orbitRadius * 20;
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
		double parentMassKg = parentTrabant.getMass() * 5.97237 * Math.pow(10, 24);
		double orbitRadiusM = orbitRadius * 382000000.0;
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
	public Trabant getParentTrabant() {
		return parentTrabant;
	}

	@Override
	public Trabant setParentTrabant(Trabant parent) {
		if(parent==null)
			return null;
		this.parentTrabant = parent;
		parentStar = parent.getParentStar();
		calculateSpeed();
		x = (int)relX + parentTrabant.getX();
		y = (int)relY + parentTrabant.getY();
		return parent;
	}

	@Override
	public Star getParentStar() {
		return parentStar;
	}

	@Override
	public Star setParentStar(Star parentStar) {
		this.parentStar = parentStar;
		return parentStar;
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
		
		x = (int)relX + parentTrabant.getX();
		y = (int)relY + parentTrabant.getY();
	}
	 */
}
