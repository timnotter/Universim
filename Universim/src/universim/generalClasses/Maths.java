package universim.generalClasses;

import universim.dataAnalysis.Dot;

public class Maths {
	//Constants
	public static final double lightyear = 9.460730473 * Math.pow(10, 15);
	public static final double lightminute = 1.798754748 * Math.pow(10, 10);
	public static final double lightsecond = 2.997924580 * Math.pow(10, 8);
	public static final double AU = 1.495979 * Math.pow(10, 11);				//Distance Sun-Earth
	public static final double EM = 384399000;									//Distance Earth-Moon
	public static final double SR = 695700000;									//Solar radius
	public static final double ER = 6371000;									//Terran radius
	public static final double MR = 1737400;									//Lunar radius
	public static final double SMASS = 1.9885 * Math.pow(10, 30);				//Solar mass
	public static final double EMASS = 5.97237 * Math.pow(10, 24);				//Terran mass
	public static final double MMASS = 7.342 * Math.pow(10, 22);				//Lunar mass
	public static final double SLUM = 3.828 * Math.pow(10, 26);					//Solar luminosity
	public static final double SMAG = 4.83;										//Solar magnitude
	public static final int STEMP = 5772;										//Solar surface temperature
	public static final double G = 6.67430 * Math.pow(10, -11);					//Gravitational constant G
	public static final double SIGMA = 5.670374419 * Math.pow(10, -8);			//Stefan-Boltzmann constant
	//Displayed size of units of length with scale 1
	public static final double pixelPerLY = 10000000;
	public static final float multAU = 1;
	public static final float multEM = multAU * 4;
	public static final float multSR = 2;
	public static final float multER = multSR * 2;
	public static final float multMR = multER * 1;
	//Relative sizes compared to 1 lightyear
	public static final double refLY = 1;
	public static final double refAU = refLY * (AU/lightyear);
	public static final double refEM = refLY * (EM/lightyear) * multEM;
	public static final double refSR = refLY * (SR/lightyear) * multSR;
	public static final double refER = refLY * (ER/lightyear) * multER;
	public static final double refMR = refLY * (MR/lightyear) * multMR;
	//Amount of updates per in game second - correctness of physics calculations
	public static final double secondsPerTick = 0.1;
	public static final double ticksPerDay = (24 * 60 * 60) / secondsPerTick;
	
	//Methods for starAnalysis
	public static int temperatureApprox(double b_r) {
		int temperature;
		temperature = (int)(4600*((1.0/(0.94*b_r+1.7))+(1.0/(0.94*b_r+0.62))));
		//If b_r value is positive - formula is about correct
		if(b_r>0) {
//			temperature -= 100;
		}
		//If b_r value is negative - formula has to be adjusted
		else {
			temperature *= (-114.62617301674) * Math.pow(b_r, 3) - 25.50779203155 * Math.pow(b_r, 2) - 0.198105371027 * b_r + 0.9483230000015;
		}
		return temperature;
	}
	
	public static double luminosityApprox(double magnitude) {
		double luminosity;
//		Calculation 1 - Deprecated by other method
//		double L0 = 3.0128 * Math.pow(10, 28);
//		luminosity = L0 * Math.pow(10, -magnitude/2.512);
		
//		Alternative: In relation to sun
		luminosity = SLUM * Math.pow(10, 0.4*(SMAG-magnitude));
		return luminosity;
	}
	
	public static double radiusApprox(int temperature, double luminosity) {
		double radius;
		radius = Math.sqrt((luminosity/(4*Math.PI*SIGMA*Math.pow(temperature, 4))));
		return radius;
	}

	public static double radiusApproxSun(int temperature, double luminosity) {
		double radius;
		radius = Math.sqrt((luminosity/(4*Math.PI*SIGMA*Math.pow(temperature, 4))));
		double relation = radius/SR;
		return relation;
	}
	
	//Arbitrary bounds for white dwarf
	public static boolean isWhiteDwarf(Dot i) {
		if(i.mg>5&&i.bp_rp<0)
			return true;
		if(i.mg>9&&i.bp_rp<2)
			return true;
		if(i.mg>13&&i.bp_rp<3)
			return true;
		return false;
	}
}

