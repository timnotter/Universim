package universim.planetClasses;

import universim.abstractClasses.Planet;
import universim.abstractClasses.Star;

public class EarthLike extends Planet{

	public EarthLike(double size, double mass, double orbitRadius, boolean habitability, Star parentStar) {
		super(size, mass, orbitRadius, habitability, parentStar);
		super.setHabitability(true);
	}
	
	public EarthLike(double size, double mass, double orbitRadius, boolean habitability) {
		this(size, mass, orbitRadius, habitability, null);
//		System.out.println("Constructor EarthLike");
	}

}
