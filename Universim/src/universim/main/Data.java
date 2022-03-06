package universim.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.awt.Color;

import universim.abstractClasses.Star;
import universim.generalClasses.Maths;
import universim.moonClasses.BarrenMoon;
import universim.planetClasses.EarthLike;
import universim.starClasses.ClassGStar;
import universim.ui.Renderer;

public class Data {
	private Main main;
	private ArrayList<Star> stars;
	private Renderer renderer;
	private Map<Integer, Color> starColours;
	
	
	public Data(Main main) {
		this.main = main;
		stars = new ArrayList<Star>();
		renderer = main.getRenderer();
		starColours = new HashMap<Integer, Color>();
		fillStarColours();
		addStars();
	}
	
	public void fillStarColours(){
		try {
			File file = new File("src\\universim\\files\\StarColours.txt");
			Integer temperature;
			int red;
			int green;
			int blue;
			Scanner scanner = new Scanner(file);
			Scanner lineScanner;
		    while(scanner.hasNextLine()) {
		    	lineScanner = new Scanner(scanner.nextLine());
		    	temperature = lineScanner.nextInt();
//		    	System.out.println(scanner.next());	//K
//		    	System.out.println(scanner.next()); //deg
//		    	System.out.println(scanner.next()); //float
//		    	System.out.println(scanner.next()); //float
//		    	System.out.println(scanner.next()); //float
//		    	System.out.println(scanner.next()); //r float
//		    	System.out.println(scanner.next()); //g float
//		    	System.out.println(scanner.next()); //b float
		    	lineScanner.next();lineScanner.next();lineScanner.next();lineScanner.next();lineScanner.next();lineScanner.next();lineScanner.next();lineScanner.next();
		    	red = lineScanner.nextInt();
		    	green = lineScanner.nextInt();
		    	blue = lineScanner.nextInt();
//		    	System.out.println(scanner.next()); //hex
		    	getStarColours().put(temperature, new Color(red, green, blue));
		    	scanner.nextLine();
		    	
		    }
		    scanner.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
	}
	
//	Yerkes spectral classifications:
//	0 (or Ia+): Hypergiants
//	I: Supergiants
//		- Ia: Luminous supergiants
//		- Iab: Intermediate-size luminous supergiants
//		- Ib: Less luminous supergiants
//	II: Bright giants
//	III: Normal giants
//	IV: Subgiants
//	V: Main-sequence stars/ Dwarfs (standard)
//	VI: Subdwarfs
//	VII: White dwarfs
	
//	Solar Mass(SM): 2 * 10^30 kg
//	Solar Radius(SR): 6.957 * 10^8m
//	Solar Luminosity(SL): 1
	
//	Spectral classification:
//	O-Class: 30'000 K - 40'000 K,
//		Main-sequence:
//			Mass: >= 16 SM
//			Radius: >= 6.6 SR
//			Luminosity: >= 30'000 SL
//			0.00003% of main-sequence stars
//	B-Class: 10'000 - 30'000 K
//		Main-sequence:
//			Mass: 2.1 - 16 SM
//			Radius: 1.8 - 6.6 SR
//			Luminosity: 25 - 30'000 SL
//			0.13% of stars
//	A-Class: 7'500 – 10'000 K 	
//		Main-sequence:
//			Mass: 1.4 – 2.1 SM 
//			Radius: 1.4 – 1.8 SR
//			Luminosity: 5 – 25 SL
//			0.6% of stars
//	F-Class: 6'000 - 7'500 K 	
//		Main-sequence: 
//			Mass: 1.04 – 1.4 SM
//			Radius: 1.15 – 1.4 SR
//			Luminosity: 1.5 – 5 SL
//			3% of stars
//	G-Class: 5'200 – 6'000 K
//		Main-sequence:
//			Mass: 0.8 – 1.04 SM
//			Radius: 0.96 – 1.15 SR
//			Luminosity: 0.6 – 1.5 SL
//			7.6% of main-sequence stars
//	K-Class: 3'700 – 5'200 K
//		Main-sequence:
//			Mass: 0.45 – 0.8 SM
//			Radius: 0.7 – 0.96 SR
//			Luminosity: 0.08 – 0.6 SL
//			12.1% of main-sequence stars
//	M-Class: 2'400 – 3'700 K
//		Main-sequence: 
//			Mass: 0.08 – 0.45 SM
//			Radius: <= 0.7 SR
//			Luminosity: <= 0.08 SL
//			76.45% of main-sequence stars
	
	
	public void addStars() {
		//Star: x, y, size, temperature, mass
		//Trabant: size, mass, orbitRadius, habitability(, parentStar)
		//SubTrabant: size, mass, orbitRadius, habitability(, parentTrabant)
		//Sun
		stars.add(new ClassGStar(0, 0, 1, Maths.STEMP, 1));
		
//		stars.get(0).addTrabant(new EarthLike(0.3, 0.1, 0.4, false));
//		stars.get(0).getTrabants().get(0).addSubTrabant(new BarrenMoon(0.1, 0.05, 0.3, false));
//		
		stars.get(0).addTrabant(new EarthLike(1, 1, 1, false));
		stars.get(0).getTrabants().get(0).addSubTrabant(new BarrenMoon(1, 1, 1, false));
//		
//		stars.get(0).addTrabant(new EarthLike(6, 16, 2.5, false));
//		stars.get(0).getTrabants().get(2).addSubTrabant(new BarrenMoon(0.9, 0.3, 1.5, false));
//		stars.get(0).getTrabants().get(2).addSubTrabant(new BarrenMoon(1.5, 0.8, 3, false));
//		stars.get(0).getTrabants().get(2).addSubTrabant(new BarrenMoon(2.6, 1.6, 6, false));
		
		//Alpha Centauri
		stars.add(new ClassGStar(3, 2.7, 1.22, Maths.temperatureApprox(0.65), 1.1));
		
		//Set colour of each star
		for(Star i: stars) {
			i.setColour(starColours.get(100 * (int) (i.getTemperature() / 100)));
		}
//		System.out.printf("%f, %f, %f, %f, %f\n", Maths.refAU, Maths.refEM, Maths.refSR, Maths.refER, Maths.refMR);
	}
	
	public ArrayList<Star> getStars(){
		return stars;
	}
	
	public ArrayList<Star> addStar(Star star){
		stars.add(star);
		return stars;
	}
	
	public ArrayList<Star> removeStar(int star){
		stars.remove(star);
		return stars;
	}
	
	public ArrayList<Star> removeStar(Star star){
		stars.remove(star);
		return stars;
	}

	public Map<Integer, Color> getStarColours() {
		return starColours;
	}
}
