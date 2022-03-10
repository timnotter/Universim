package universim.abstractClasses;

import java.awt.Color;
import java.util.ArrayList;

import universim.generalClasses.Maths;
import universim.ui.GameDisplay;

public abstract class Trabant extends CelestialBody {
	protected Star parentStar;
	private double relX; 					//Relative x to parentStar in AU
	private double relY; 					//Relative y to parentStar in AU
	protected double relXM;					//Relative x to parentStar in m
	protected double relYM;					//Relative y to parentStar in m
	protected double temperature; 			//In Kelvin
	protected double orbitRadius;			//In AU
	protected double apoapsis;				//In AU
	protected double periapsis;				//In AU
	protected double currDist; 				//Current distance from parentStar in meter
	protected double speedMS; 				//Speed in m/s around parentStar
	protected double speedXMS; 				//Speed in x-axis
	protected double speedYMS; 				//Speed in y-axis
	protected double speed; 				//Frequency of orbits - orbits per day
//	protected double speedDeg;				//Change in orbit - degree/day									//Deprecated
//	protected double deg; 					//Current degree of orbit to starting position 					//Deprecated
	protected int period; 					//Orbital period - day/orbit - rounded for practical reasons
	private Color colour;
	protected ArrayList<SubTrabant> subTrabants;

//	protected double minDist;
//	protected double maxDist;
//	protected double startDist;
//	protected double minSpeed;
//	protected double maxSpeed;
//	protected double startSpeed;

	public Trabant(double size, double mass, double orbitRadius, Star parentStar) {
		this(size, mass, orbitRadius, orbitRadius, parentStar);
	}
	
	public Trabant(double size, double mass, double apoapsis, double periapsis, Star parentStar) {
		super(0, 0, size, mass);
		this.apoapsis = apoapsis;
		this.periapsis = periapsis;
		orbitRadius = apoapsis;
		this.parentStar = parentStar;
		subTrabants = new ArrayList();
		oRR = Maths.AU; // Orbit Radius Reference - AU
		rR = Maths.EM; // Radius Reference - Earth Radius
		mR = Maths.EMASS; // Mass Reference - Earth Mass
		
		if (parentStar != null) {
			setXY();
		}
	}

	public void setXY() {
		x = y = 0;
		//Determine "start" on elliptical orbit
		currDist = Math.random() * (apoapsis-periapsis) + periapsis;
		calculateSpeed();
		double angle = (Math.random() * 2 * Math.PI);
		relX = currDist * Math.cos(angle);
		relY = (-1) * currDist * Math.sin(angle);
		relXM = relX * oRR;
		relYM = relY * oRR;
		speedXMS = (-1) * speedMS * Math.sin(angle);
		speedYMS = (-1) * speedMS * Math.cos(angle);
//		System.out.printf("Angle: %f, currDist: %f, relX: %f, relY: %f, s: %f, sX: %f, sY: %f\n", angle, currDist, relX, relY, speedMS, speedXMS, speedYMS);
		
//		startDist = minDist = maxDist = currDist;
	}

	public void calculateSpeed() {
		if (parentStar == null)
			return;
		speedMS = Math.sqrt(Maths.G * parentStar.getMass() * ((2/(currDist*oRR))-(1/((apoapsis+periapsis)*oRR/2))));
//		System.out.println(speedMS);
//		startSpeed = minSpeed = maxSpeed = speedMS;
	}

	@Override
	public void update() {
		currDist = Math.sqrt(relX * relX + relY * relY);
//		if(currDist>maxDist)
//			maxDist = currDist;
//		if(currDist<minDist)
//			minDist = currDist;
//		if(Math.random()>=0.9)
//			System.out.printf("CurrDist: %f, startDist: %f, minDist: %f, maxDist: %f\n", currDist, startDist, minDist, maxDist);
//		if(speedMS>maxSpeed)
//			maxSpeed = speedMS;
//		if(speedMS<minSpeed)
//			minSpeed = speedMS;
//		System.out.printf("speedMS: %f, startSpeed: %f, minSpeed: %f, maxSpeed: %f\n", speedMS, startSpeed, minSpeed, maxSpeed);

		
		double totAcc = Maths.G * parentStar.getMass() / (currDist * oRR * currDist * oRR); // Gravitational pull of parentStar
		double xAcc, yAcc;
		if (relY == 0 && relX == 0) {
			System.out.println("Earth Collision");
			return;
		} else if (relY == 0) {
			xAcc = totAcc;
			yAcc = 0;
		} else if (relX == 0) {
			xAcc = 0;
			yAcc = totAcc;
		} else {
			xAcc = Math.sqrt(totAcc * totAcc * relXM * relXM / (relYM * relYM + relXM * relXM));
			yAcc = Math.sqrt(totAcc * totAcc - (xAcc * xAcc));
		}

		if (Double.isNaN(xAcc) || Double.isNaN(yAcc)) {
			System.out.println("NaN in Trabant");
			System.out.printf("relXM: %f, relYM: %f, currDist: %f, totAcc: %f, xAcc: %f, yAcc: %f,  speedMS: %f\n", relXM,
					relYM, currDist, totAcc, xAcc, yAcc, speedMS);
			if(Double.isNaN(xAcc))
				xAcc = 0;
			else
				yAcc = 0;
//			System.out.println(xAcc + ", " + yAcc);
//			throw new IllegalStateException();
		}
		if (relX > 0) {
			if (relY > 0) {
				// Bottom right quadrant
				xAcc = -xAcc;
				yAcc = -yAcc;
			} else {
				// Top right quadrant
				xAcc = -xAcc;
			}
		} else {
			if (relY > 0) {
				// Bottom left quadrant
				yAcc = -yAcc;
			} else {
				// Top left quadrant
			}
		}
//		System.out.printf("relXM: %f, relYM: %f, currDist: %f, totAcc: %f, xAcc: %f, yAcc: %f, yAcc: %f\n", relXM, relYM, currDist, totAcc, xAcc, yAcc, totAcc*totAcc-(xAcc*xAcc));
//		System.out.printf("speedXMS: %f, speedYMS: %f\n", speedXMS, speedYMS);
		relX += (speedXMS * Maths.secondsPerTick / oRR);
		relY += (speedYMS * Maths.secondsPerTick / oRR);
		relXM = relX * oRR;
		relYM = relY * oRR;

		speedXMS += xAcc * Maths.secondsPerTick;
		speedYMS += yAcc * Maths.secondsPerTick;
		speedMS = Math.sqrt(speedXMS * speedXMS + speedYMS * speedYMS);
	}

	public Star getParentStar() {
		return parentStar;
	}

	public Star setParentStar(Star parentStar) {
		if (parentStar == null)
			return null;
		this.parentStar = parentStar;
		setXY();
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

	public ArrayList<SubTrabant> getSubTrabants() {
		return subTrabants;
	}

	public ArrayList<SubTrabant> addSubTrabant(SubTrabant t) {
		t.setParentTrabant(this);
		subTrabants.add(t);
		return subTrabants;
	}

	public double getRelX() {
		return relX;
	}

	public void setRelX(double relX) {
		this.relX = relX;
	}

	public double getRelY() {
		return relY;
	}

	public void setRelY(double relY) {
		this.relY = relY;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	@Override
	public double getX() {
		return (parentStar.getX() + ((relX * oRR * Maths.multAU) / Maths.lightyear));
	}

	@Override
	public double getY() {
		return (parentStar.getY() + ((relY * oRR * Maths.multAU) / Maths.lightyear));
	}
}
