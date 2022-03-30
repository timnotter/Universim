package universim.abstractClasses;

import java.awt.Color;
import java.util.ArrayList;

import universim.generalClasses.Maths;
import universim.ui.GameDisplay;

public abstract class SubTrabant extends CelestialBody {
	protected Star parentStar;
	protected Trabant parentTrabant;
	protected double relX;					//Relative x to parentStar in AU
	protected double relY;					//Relative y to parentStar in AU
	protected double relXM;					//Relative x to parentStar in m
	protected double relYM;					//Relative y to parentStar in m
	protected double temperature; 			//In Kelvin
//	protected double orbitRadius; 			//In EM
	protected double apoapsis;				//In EM
	protected double periapsis;				//In EM
	protected double currDist; 				//Current distance from parenTrabant in oRR
	protected double currDistM;				//Current distance from parenTrabant in meter
	protected double speed; 				//Frequency of orbits - orbits per day
	protected double speedMS; 				//Speed in m/s around parentTrabant
	protected double speedXMS; 				//Speed in x-axis
	protected double speedYMS;				//Speed in y-axis
//	protected double speedDeg; 				//Change in orbit - degree/day									//Deprecated
//	protected double deg; 					//Current degree of orbit to starting position					//Deprecated
	protected int period; 					//Orbital period - day/orbit - rounded for practical reasons
	protected int orbit;					//1 if orbit is anti clockwise, -1 if orbit is clockwise
	protected Color colour;
	protected String type;
	protected ArrayList<SubTrabant> subTrabants;

	//Debugging
	protected double minDist;
	protected double oldMinDist;
	protected double maxDist;
	protected double oldMaxDist;
	protected int dayCounter;
	protected double maxDrift;
	protected double minDrift;
	protected double midDrift;
	protected int driftCounter;
	protected double startDist;
	protected double minSpeed;
	protected double maxSpeed;
	protected double startSpeed;
	
	public SubTrabant(double size, double mass, double apoapsis, double periapsis, Trabant parentTrabant, boolean antiClock) {
		super(0, 0, size, mass);
		this.apoapsis = apoapsis;
		this.periapsis = periapsis;
//		orbitRadius = apoapsis;
		this.parentTrabant = parentTrabant;
		if(antiClock)
			orbit = 1;
		else
			orbit = -1;	
		dayCounter = 0;

		oRR = Maths.EM; // Orbit Radius Reference - Distance Earth Moon
		rR = Maths.MR; // Radius Reference - Moon Radius
		mR = Maths.MMASS; // Mass Reference - Moon Mass

		if (parentTrabant != null) {
			setXY();
			calculateSpeed();
			parentStar = parentTrabant.getParentStar();
		}
	}

	public void setXY() {
		x = y = 0;
		//Determine "start" on elliptical orbit
//		currDist = Math.random() * (apoapsis-periapsis) + periapsis;
		currDist = periapsis;
		currDistM = currDist * oRR;
//		double angle = (Math.random() * 2 * Math.PI);
		double angle = Math.PI;
		relX = currDist * Math.cos(angle);
		relY = (-1) * currDist * Math.sin(angle);
		relXM = relX * oRR;
		relYM = relY * oRR;
		calculateSpeed();
		speedXMS = (orbit) * (-1) * speedMS * Math.sin(angle);
		speedYMS = (orbit) * (-1) * speedMS * Math.cos(angle);
//		System.out.printf("Angle: %f, currDist: %f, relX: %f, relY: %f, s: %f, sX: %f, sY: %f\n", angle, currDist, relX, relY, speedMS, speedXMS, speedYMS);
		
//		startDist = minDist = maxDist = currDist;
	}

	public void calculateSpeed() {
//		TODO Speed calculation seems to be the problem
		
		if (parentTrabant == null)
			return;
//		speedMS = Math.sqrt(Maths.G * (parentTrabant.getMass()+mass) * (2/currDistM - 1/(apoapsis*oRR)));
		speedMS = Math.sqrt(Maths.G * parentTrabant.getMass() * ((2/(currDistM))-(1/((apoapsis+periapsis)*oRR/2))));
//		speedMS *= 0.99;
//		System.out.printf("CurrDist: %f, speedMS: %f\n", currDist, speedMS);
//		startSpeed = minSpeed = maxSpeed = speedMS;
		
//		TODO try decreasing velocity
		
	}

	@Override
	public void update() {
		driftCalc();

		currDist = Math.sqrt(relX * relX + relY * relY);
		currDistM = currDist * oRR;
		double totAcc = Maths.G * parentTrabant.getMass() / (currDistM * currDistM); // Gravitational pull of parentStar
		double xAcc, yAcc;
		if (relY == 0 && relX == 0) {
			System.out.println("Moon collision");
			return;
		} else if (relY == 0) {
			xAcc = totAcc;
			yAcc = 0;
		} else if (relX == 0) {
			xAcc = 0;
			yAcc = totAcc;
		} else {
			//Old Way
//			xAcc = Math.sqrt(totAcc * totAcc * relXM * relXM / (relYM * relYM + relXM * relXM));
//			yAcc = Math.sqrt(totAcc * totAcc - (xAcc * xAcc));
			//Simplified way
			xAcc = -(relX * totAcc/currDist);
			yAcc = -(relY * totAcc/currDist);
//			System.out.printf("totAcc: %f, relX: %f, currDist: %f, xAcc: %f\n", totAcc, relX, currDist, xAcc);
		}

		if (Double.isNaN(xAcc) || Double.isNaN(yAcc)) {
			System.out.println("NaN in SubTrabant");
			System.out.printf("relXM: %f, relYM: %f, currDist: %f, totAcc: %f, xAcc: %f, yAcc: %f, yAcc: %f\n", relXM,
					relYM, currDist, totAcc, xAcc, yAcc, totAcc * totAcc - (xAcc * xAcc));
			if(Double.isNaN(xAcc))
				xAcc = 0;
			else
				yAcc = 0;
//			throw new IllegalStateException();
		}
		//Only used by old way
//		if (relX > 0) {
//			if (relY > 0) {
//				// Bottom right quadrant
//				xAcc = -Math.abs(xAcc);
//				yAcc = -Math.abs(yAcc);
//			} else {
//				// Top right quadrant
//				xAcc = -Math.abs(xAcc);
//			}
//		} else {
//			if (relY > 0) {
//				// Bottom left quadrant
//				yAcc = -Math.abs(yAcc);
//			} else {
//				// Top left quadrant
//			}
//		}
//		System.out.printf("relXM: %f, relYM: %f, currDist: %f, totAcc: %f, xAcc: %f, yAcc: %f, yAcc^2: %f\n", relXM, relYM, currDist, totAcc, xAcc, yAcc, totAcc*totAcc-(xAcc*xAcc));
//		System.out.printf("speedXMS: %f, speedYMS: %f\n", speedXMS, speedYMS);

		relX += (speedXMS * Maths.secondsPerTick / oRR);
		relY += (speedYMS * Maths.secondsPerTick / oRR);
		relXM = relX * oRR;
		relYM = relY * oRR;

		speedXMS += xAcc * Maths.secondsPerTick;
		speedYMS += yAcc * Maths.secondsPerTick;
		speedMS = Math.sqrt(speedXMS * speedXMS + speedYMS * speedYMS);
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
		parentTrabant = t;
		if (t == null)
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
	//Deprecated
	public double getOrbitRadius() {
		return apoapsis;
	}
	//Deprecated
	public double setOrbitRadius(double apoapsis) {
		this.apoapsis = apoapsis;
		return apoapsis;
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
		return (parentTrabant.getX() + ((relX * oRR * Maths.multEM) / Maths.lightyear));
	}

	@Override
	public double getY() {
		return (parentTrabant.getY() + ((relY * oRR * Maths.multEM) / Maths.lightyear));
	}
	
	public void driftCalc() {
//		System.out.println(relX + ", " + relY);
		
		//Analysis of drift
		if(currDist>maxDist)
			maxDist = currDist;
		if(currDist<minDist)
			minDist = currDist;
		dayCounter++;
		
		if(dayCounter>=Maths.ticksPerDay) {
			double drift = (maxDist - oldMaxDist) * oRR;
			if(drift<minDrift)
				minDrift = drift;
			if(drift>maxDrift)
				maxDrift = drift;
			driftCounter++;
			if(driftCounter==1) {
				midDrift = 0;
				maxDrift = 0;
				minDrift = 1000000000000d;
			}
			else if(driftCounter==2) {
				midDrift = drift;
			}
			else {
				midDrift = ((driftCounter-2) * midDrift + drift)/(driftCounter-1);
			}
			System.out.printf("CurrDrift: %f, currDist: %f, minDrift: %f, maxDrift: %f, midDrift: %f\n", drift, currDist, minDrift, maxDrift, midDrift);
			oldMaxDist = maxDist;
			oldMinDist = minDist;
			dayCounter = 0;
		}
		
//		System.out.printf("CurrDist: %f, startDist: %f, minDist: %f, maxDist: %f\n", currDist, startDist, minDist, maxDist);
//		if(speedMS>maxSpeed)
//			maxSpeed = speedMS;
//		if(speedMS<minSpeed)
//			minSpeed = speedMS;
//		System.out.printf("speedMS: %f, startSpeed: %f, minSpeed: %f, maxSpeed: %f\n", speedMS, startSpeed, minSpeed, maxSpeed);
	}
}
