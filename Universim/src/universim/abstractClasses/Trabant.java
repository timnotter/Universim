package universim.abstractClasses;

import java.util.ArrayList;

import universim.generalClasses.Maths;
import universim.ui.GameDisplay;


public abstract class Trabant extends CelestialBody{
	protected Star parentStar;
	protected double relX;			//Relative y
	protected double relY;			//Relative x
//	protected int counter;
	
	protected double temperature;	//In Kelvin
	
	protected double orbitRadius;	//In AU
	protected double currDist;		//Current distance from parentStar in meter
	protected double speedMS;		//Speed in m/s
	protected double speedMSX;		//Speed in x-axis
	protected double speedMSY;		//Speed in y-axis
	protected double speed;			//Frequency of orbits - orbits per day
//	protected double speedDeg;		//Change in orbit - degree/day									//Deprecated
	protected double deg;			//Current degree of orbit to starting position					//Deprecated
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
		
		oRR = Maths.AU;							//Orbit Radius Reference - AU
		rR = Maths.EM;							//Radius Reference - Earth Radius
		mR = 5.97237 * Math.pow(10, 24);		//Mass Reference - Earth Mass
	}
	
	public void setXY() {
		x = parentStar.getX() + (orbitRadius * oRR / Maths.lightyear);
		y = parentStar.getY();		
		currDist = orbitRadius * oRR;
	}
	
	public void calculateSpeed() {
		//Calculate speed of perfectly round orbit at this radius
		if(parentStar==null)
			return;
		double parentMassKg = parentStar.getMass();
		double orbitRadiusM = orbitRadius * oRR;
		speedMS = Math.sqrt(Maths.G*parentMassKg/orbitRadiusM);			//Speed int m/s
		double orbitLength = 2 * Math.PI * orbitRadiusM;
		speed = speedMS/orbitLength * 86400;							//orbits/s * second per day
		period = (int)(1/speed);
		speedMSX = 0;
		speedMSY = speedMS;
	}
	
	@Override
	public void update(GameDisplay gameDisplay) {
		x = x + speedMSX * Maths.secondsPerTick / Maths.lightyear;
		y = y + speedMSY * Maths.secondsPerTick / Maths.lightyear;
		
		double relX = (x - parentStar.getX()) * Maths.lightyear;
		double relY = (y - parentStar.getY()) * Maths.lightyear;
		currDist = Math.sqrt(relX * relX + relY * relY);
		double totAcc = Maths.G * parentStar.getMass() / (currDist * currDist);		//Gravitational pull of parentStar		
		double xAcc, yAcc;
		if(relY==0&&relX==0) {
			System.out.println("Collision");
			return;
		}
		else if(relY==0) {
			xAcc = totAcc;
			yAcc = 0;
		}
		else if(relX==0) {
			xAcc = 0;
			yAcc = totAcc;
		}
		else {
			xAcc = Math.sqrt(totAcc*totAcc*relX*relX/(relY*relY+relX*relX));
			yAcc = Math.sqrt(totAcc*totAcc-(xAcc*xAcc));
		}
		
		if(Double.isNaN(xAcc)||Double.isNaN(yAcc)) {
			System.out.println("NaN");
			System.out.printf("relX: %f, relY: %f, currDist: %f, totAcc: %f, xAcc: %f, yAcc: %f, yAcc: %f\n", relX, relY, currDist, totAcc, xAcc, yAcc, totAcc*totAcc-(xAcc*xAcc));
			throw new IllegalStateException();
		}
		
		if(relX>0) {
			if(relY>0) {
				//Bottom right quadrant
				xAcc = -xAcc;
				yAcc = -yAcc;
			}
			else {
				//Top right quadrant
				xAcc = -xAcc;
			}
		}
		else {
			if(relY>0) {
				//Bottom left quadrant
				yAcc = -yAcc;
			}
			else {
				//Top left quadrant
			}
		}
		
		speedMSX += xAcc * Maths.secondsPerTick;		//Hourly updates
		speedMSY += yAcc * Maths.secondsPerTick;
		speed = Math.sqrt(speedMSX*speedMSX+speedMSY*speedMSY);
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
