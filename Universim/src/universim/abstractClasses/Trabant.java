package universim.abstractClasses;

import java.util.ArrayList;

import universim.generalClasses.Maths;
import universim.ui.GameDisplay;

public abstract class Trabant extends CelestialBody {
	protected Star parentStar;
	private double relX; 					// Relative x to parentStar in AU
	private double relY; 					// Relative y to parentStar in AU
	protected double relXM;					// Relative x to parentStar in m
	protected double relYM;					// Relative y to parentStar in m
	protected double temperature; 			// In Kelvin
	protected double orbitRadius;			// In AU
	protected double currDist; 				// Current distance from parentStar in meter
	protected double speedMS; 				// Speed in m/s around parentStar
	protected double speedXMS; 				// Speed in x-axis
	protected double speedYMS; 				// Speed in y-axis
	protected double speed; 				// Frequency of orbits - orbits per day
//	protected double speedDeg;				//Change in orbit - degree/day									//Deprecated
//	protected double deg; 					// Current degree of orbit to starting position 				//Deprecated
	protected int period; 					// Orbital period - day/orbit - rounded for practical reasons
	protected ArrayList<SubTrabant> subTrabants;

	protected double minDist;
	protected double maxDist;
	protected double startDist;
//	protected double minSpeed;
//	protected double maxSpeed;
//	protected double startSpeed;

	public Trabant(double size, double mass, double orbitRadius, Star parentStar) {
		super(0, 0, size, mass);
		this.orbitRadius = orbitRadius;
		this.parentStar = parentStar;
		subTrabants = new ArrayList();
//		System.out.println("Create Trabant");
		if (parentStar != null) {
			setXY();
			calculateSpeed();
		}

		oRR = Maths.AU; // Orbit Radius Reference - AU
		rR = Maths.EM; // Radius Reference - Earth Radius
		mR = Maths.EMASS; // Mass Reference - Earth Mass
	}

	public void setXY() {
		x = y = 0;
		relX = orbitRadius;
		relY = 0;
		relXM = relX * oRR;
		relYM = 0;
		currDist = orbitRadius * oRR;
		startDist = minDist = maxDist = currDist;
	}

	public void calculateSpeed() {
		// Calculate speed of perfectly round orbit at this radius
		if (parentStar == null)
			return;
		double parentMassKg = parentStar.getMass();
		double orbitLength = 2 * Math.PI * currDist;
		speedMS = Math.sqrt(Maths.G * parentMassKg / currDist); // Speed int m/s
		speed = speedMS / orbitLength * 86400; // orbits/s * second per day on round orbit
		period = (int) (1 / speed);
		speedXMS = 0;
		speedYMS = speedMS;
//		startSpeed = minSpeed = maxSpeed = speedMS;
	}

	@Override
	public void update() {
//		System.out.printf("pX: %d, pY: %d, pSize: %f, posLX, %d, posLY, %d, posRX: %d, posRY: %d\n", pX, pY, pSize, pX-(int)pSize, pY-(int)pSize, pX+1+(int)pSize, pY+1+(int)pSize);

//		System.out.println(relX + ", " + relY);
		relXM = relX * oRR;
		relYM = relY * oRR;
		currDist = Math.sqrt(relXM * relXM + relYM * relYM);
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

		
		double totAcc = Maths.G * parentStar.getMass() / (currDist * currDist); // Gravitational pull of parentStar
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
			System.out.printf("relXM: %f, relYM: %f, currDist: %f, totAcc: %f, xAcc: %f, yAcc: %f, yAcc: %f\n", relXM,
					relYM, currDist, totAcc, xAcc, yAcc, totAcc * totAcc - (xAcc * xAcc));
			if(Double.isNaN(xAcc))
				xAcc = 0;
			else
				yAcc = 0;
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

	@Override
	public double getX() {
		return (parentStar.getX() + ((relX * oRR * Maths.multAU) / Maths.lightyear));
	}

	@Override
	public double getY() {
		return (parentStar.getY() + ((relY * oRR * Maths.multAU) / Maths.lightyear));
	}
}
