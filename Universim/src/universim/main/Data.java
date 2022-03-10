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
		addSolarSystem();
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
		//Alpha Centauri
		stars.add(new ClassGStar(3, 2.7, 1.22, Maths.temperatureApprox(0.65), 1.1));
		
		//Set colour of each star
		for(Star i: stars) {
			i.setColour(starColours.get(100 * (int) (i.getTemperature() / 100)));
		}
	}
	
	public void addSolarSystem() {
		//Star: x, y, size, temperature, mass
		//Trabant: size, mass, orbitRadius, habitability(, parentStar) or size, mass, apoapsis, periapsis, habitability(, parenStar)
		//SubTrabant: size, mass, orbitRadius, habitability(, parentTrabant)
		//Sun
		stars.add(new ClassGStar(0, 0, 1, Maths.STEMP, 1));
		//Mercury
		stars.get(0).addTrabant(new EarthLike(0.3829, 0.055, 0.466692, 0.307499, false));
		//Venus
		stars.get(0).addTrabant(new EarthLike(0.9499, 0.815, 0.728213, 0.718440, false));
		
		//Earth
		stars.get(0).addTrabant(new EarthLike(1, 1, 1, false));
		stars.get(0).getTrabants().get(2).addSubTrabant(new BarrenMoon(1, 1, 1, false));
		
		//Mars
		stars.get(0).addTrabant(new EarthLike(0.532, 0.107, 1.666, 1.382, false));
		//Phobos
		stars.get(0).getTrabants().get(3).addSubTrabant(new BarrenMoon(11266.7/Maths.MR, 1.0659 * Math.pow(10, 16)/Maths.MMASS, 9517580/Maths.EM, 9234420/Maths.EM, false));
		//Deimos
		stars.get(0).getTrabants().get(3).addSubTrabant(new BarrenMoon(6200/Maths.MR, 1.4762 * Math.pow(10, 15), 23455500/Maths.EM, 23470900/Maths.EM, false));
		
		//Jupiter
		stars.get(0).addTrabant(new EarthLike(10.973, 317.8, 5.4588, 4.9501, false));
		//Io
		stars.get(0).getTrabants().get(4).addSubTrabant(new BarrenMoon(1821600/Maths.MR, 8.931938*Math.pow(10, 22)/Maths.MMASS, 423400000/Maths.EM, 420000000/Maths.EM, false));
		//Europa
		stars.get(0).getTrabants().get(4).addSubTrabant(new BarrenMoon(1560800/Maths.MR, 4.799844*Math.pow(10, 22)/Maths.MMASS, 676938000/Maths.EM, 664862000/Maths.EM, false));
		//Ganymede
		stars.get(0).getTrabants().get(4).addSubTrabant(new BarrenMoon(2634100/Maths.MR, 1.4819*Math.pow(10, 23)/Maths.MMASS, 1071600000/Maths.EM, 1069200000/Maths.EM, false));
		//Callisto
		stars.get(0).getTrabants().get(4).addSubTrabant(new BarrenMoon(2410300/Maths.MR, 1.075938*Math.pow(10, 23)/Maths.MMASS, 1897000000/Maths.EM, 1869000000/Maths.EM, false));
		
		//Saturn
		stars.get(0).addTrabant(new EarthLike(8.552, 95.159, 10.1238, 9.0412, false));
		//Mimas
		stars.get(0).getTrabants().get(5).addSubTrabant(new BarrenMoon(198200/Maths.MR, 3.7493*Math.pow(10, 19)/Maths.MMASS, 189176000/Maths.EM, 181902000/Maths.EM, false));
		//Enceladius
		stars.get(0).getTrabants().get(5).addSubTrabant(new BarrenMoon(252100/Maths.MR, 1.08022*Math.pow(10, 22)/Maths.MMASS, 239090000/Maths.EM, 236950000/Maths.EM, false));
		//Tethys
		stars.get(0).getTrabants().get(5).addSubTrabant(new BarrenMoon(531100/Maths.MR, 6.17449*Math.pow(10, 20)/Maths.MMASS, 294700000/Maths.EM, 294640000/Maths.EM, false));
		//Dione
		stars.get(0).getTrabants().get(5).addSubTrabant(new BarrenMoon(561400/Maths.MR, 1.095452*Math.pow(10, 21)/Maths.MMASS, 378230000/Maths.EM, 376570000/Maths.EM, false));
		//Rhea
		stars.get(0).getTrabants().get(5).addSubTrabant(new BarrenMoon(763800/Maths.MR, 2.306518*Math.pow(10, 21)/Maths.MMASS, 527570000/Maths.EM, 526510000/Maths.EM, false));
		//Titan
		stars.get(0).getTrabants().get(5).addSubTrabant(new BarrenMoon(2574730/Maths.MR, 1.3452*Math.pow(10, 23)/Maths.MMASS, 1257060000/Maths.EM, 1186680000/Maths.EM, false));
		//Iapetus
		stars.get(0).getTrabants().get(5).addSubTrabant(new BarrenMoon(734500/Maths.MR, 1.805635*Math.pow(10, 21)/Maths.MMASS, 3662100000l/Maths.EM, 3460500000l/Maths.EM, false));
		
		//Uranus
		stars.get(0).addTrabant(new EarthLike(25362000/Maths.ER, 14.536, 20.0965, 18.2861, false));
		//Puck
		stars.get(0).getTrabants().get(6).addSubTrabant(new BarrenMoon(81/Maths.MR, 1.805635*Math.pow(10, 21)/Maths.MMASS, 3662100000l/Maths.EM, 3460500000l/Maths.EM, false));
		
		//Neptune
		stars.get(0).addTrabant(new EarthLike(24622000/Maths.ER, 17.147, 30.33, 29.81, false));
		
		
		
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
