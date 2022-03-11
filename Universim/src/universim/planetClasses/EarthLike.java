package universim.planetClasses;

import universim.abstractClasses.Planet;
import universim.abstractClasses.Star;

public class EarthLike extends Planet{

	public EarthLike(double size, double mass, double orbitRadius, boolean habitability, Star parentStar) {
		super(size, mass, orbitRadius, habitability, parentStar);
		super.type = "Earthlike";
	}
	
	public EarthLike(double size, double mass, double orbitRadius, boolean habitability) {
		this(size, mass, orbitRadius, habitability, null);
	}
	
	public EarthLike(double size, double mass, double apoapsis, double periapsis, boolean habitability, Star parentStar) {
		super(size, mass, apoapsis, periapsis, habitability, parentStar);
		super.type = "Earthlike";
	}
	
	public EarthLike(double size, double mass, double apoapsis, double periapsis, boolean habitability) {
		this(size, mass, apoapsis, periapsis, habitability, null);
	}
}
