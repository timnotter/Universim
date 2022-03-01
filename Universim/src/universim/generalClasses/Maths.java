package universim.generalClasses;

public class Maths {
//	Constants
	public final static double lightyear = 9.460730473 * Math.pow(10, 15);
	public final static double lightminute = 1.798754748 * Math.pow(10, 10);
	public final static double lightsecond = 2.997924580 * Math.pow(10, 8);
	
	public static double EM = 384399000;
	public static double AU = 1.495979 * Math.pow(10, 11);
	
	public static double SR = 695700000;
	public static double ER = 6371000;
	public static double MR = 1737400;
	
	public final static double pixelPerAU = 500;							//Astronomical Unit
	public final static double pixelPerEM = pixelPerAU * (EM/AU) * 5;		//Earth-Moon distance
	
	public final static double pixelPerSR = 20;								//Solar radius
	public final static double pixelPerER = pixelPerSR * (ER/SR) * 5;		//Earth radius
	public final static double pixelPerMR = pixelPerER * (MR/ER) * 2;		//Moon radius
	
	public static int temperatureAprox(double b_r) {
		int temperature;
		temperature = (int)(4600*((1.0/(0.94*b_r+1.7))+(1.0/(0.94*b_r+0.62))));
		if(b_r>0) {
//			temperature -= 100;
		}
		else {
			temperature *= (-114.62617301674) * Math.pow(b_r, 3) - 25.50779203155 * Math.pow(b_r, 2) - 0.198105371027 * b_r + 0.9483230000015;
		}
		return temperature;
	}
	
	public static double luminosityAprox(double magnitude) {
		double luminosity;
//		Calculation
//		double L0 = 3.0128 * Math.pow(10, 28);
//		luminosity = L0 * Math.pow(10, -magnitude/2.512);
		
//		Alternatively in relation to sun
		double lumSun = 3.828 * Math.pow(10, 26);
		double magSun = 4.83;
		luminosity = lumSun * Math.pow(10, 0.4*(magSun-magnitude));
		return luminosity;
	}
	public static double luminosityAproxSun(double magnitude) {
		double relation;
//		In relation to sun
		double magSun = 4.83;
		relation = Math.pow(10, 0.4*(magSun-magnitude));
		return relation;
	}
	
	public static double radiusAprox(int temperature, double luminosity) {
		double radius;
		double sigma = 5.670374419 * Math.pow(10, -8);
		radius = Math.sqrt((luminosity/(4*Math.PI*sigma*Math.pow(temperature, 4))));
		return radius;
	}
	
	public static double radiusAproxSun(int temperature, double luminosity) {
		double radius;
		double sigma = 5.670374419 * Math.pow(10, -8);
		double solarRadius = 696700000;
		radius = Math.sqrt((luminosity/(4*Math.PI*sigma*Math.pow(temperature, 4))));
		double relation = radius/solarRadius;
		return relation;
	}
}

