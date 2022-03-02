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
	protected double currDist;		//Current distance from parenTrabant in meter
	protected double speed;			//Frequency of orbits - orbits per day
	protected double speedMS;		//Speed in m/s
	protected double speedMSX;		//Speed in x-axis
	protected double speedMSY;		//Speed in y-axis
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
		
		oRR = Maths.EM;							//Orbit Radius Reference - Distance Earth Moon
		rR = Maths.MR;							//Radius Reference - Moon Radius
		mR = 7.342 * Math.pow(10, 22);			//Mass Reference - Moon Mass
	}
	
//	public void setXY() {		
//		int ranDeg = (int)(Math.random()*360);
//		double tempX = orbitRadius * Maths.pixelPerLY * Maths.refMR;
////		System.out.println("PPEM: " + Maths.pixelPerEM);
//		double tempY = 0;
//		
//		relSX = tempX*Math.cos(ranDeg) - tempY*Math.sin(ranDeg);
//		relSY = tempX*Math.sin(ranDeg) + tempY*Math.cos(ranDeg);
//		relX = relSX;
//		relY = relSY;
//		
//		if(parentTrabant!=null) {
//			x = (int)relX + parentTrabant.getX();
//			y = (int)relY + parentTrabant.getY();
//		}
//	}
	
	public void setXY() {
		x = parentTrabant.getX() + (orbitRadius * oRR / Maths.lightyear);
		y = parentTrabant.getY();		
		currDist = orbitRadius * oRR;
	}
	
	public void calculateSpeed() {
		//Calculate speed of perfectly round orbit at this radius
		if(parentTrabant==null)
			return;
		double parentMassKg = parentTrabant.getMass();
		double orbitRadiusM = orbitRadius * oRR;
		speedMS = Math.sqrt(Maths.G*parentMassKg/orbitRadiusM);			//Speed int m/s
		speedMS *= 0.95;
		double orbitLength = 2 * Math.PI * orbitRadiusM;
		speed = speedMS/orbitLength * 86400;							//orbits/s * second per day
		period = (int)(1/speed);
		speedMSX = 0;
		speedMSY = speedMS;
	}
	
	@Override
	public void update(GameDisplay gameDisplay) {
		x = x + (speedMSX + parentTrabant.speedMSX) * Maths.secondsPerTick / Maths.lightyear;
		y = y + (speedMSY + parentTrabant.speedMSY) * Maths.secondsPerTick / Maths.lightyear;
		
		double relX = (x - parentTrabant.getX()) * Maths.lightyear;
		double relY = (y - parentTrabant.getY()) * Maths.lightyear;
		currDist = Math.sqrt(relX * relX + relY * relY);
		double totAcc = Maths.G * parentTrabant.getMass() / (currDist * currDist);		//Gravitational pull of parentTrabant
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
	
//	@Override
//	public void update(GameDisplay gameDisplay) {
//		deg += speedDeg/24;				//Divided by 24 for hourly updates
//		if(deg>=360)
//			deg -= 360;
//		double degRad = deg / 180 * Math.PI;
//		
//		relX = relSX*Math.cos(degRad) - relSY*Math.sin(degRad);
//		relY = relSX*Math.sin(degRad) + relSY*Math.cos(degRad);
//		
//		x = (int)(relX * gameDisplay.getScale()) + parentTrabant.getX();
//		y = (int)(relY * gameDisplay.getScale()) + parentTrabant.getY();
//	}
	
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
