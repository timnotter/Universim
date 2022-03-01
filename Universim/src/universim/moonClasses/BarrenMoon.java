package universim.moonClasses;

import universim.abstractClasses.Moon;
import universim.abstractClasses.Trabant;

public class BarrenMoon extends Moon{
	public BarrenMoon(double size, double mass, double orbitRadius, boolean habitability, Trabant parentTrabant) {
		super(size, mass, orbitRadius, habitability, parentTrabant);
	}
	
	public BarrenMoon(double size, double mass, double orbitRadius, boolean habitability) {
		this(size, mass, orbitRadius, habitability, null);
//		System.out.println("Constructor EarthLike");
	}
}
