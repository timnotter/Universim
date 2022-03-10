package universim.moonClasses;

import universim.abstractClasses.Moon;
import universim.abstractClasses.Trabant;

public class BarrenMoon extends Moon{
	public BarrenMoon(double size, double mass, double orbitRadius, boolean habitability, Trabant parentTrabant) {
		super(size, mass, orbitRadius, habitability, parentTrabant);
	}
	
	public BarrenMoon(double size, double mass, double orbitRadius, boolean habitability) {
		this(size, mass, orbitRadius, habitability, null);
	}
	
	public BarrenMoon(double size, double mass, double apoapsis, double periapsis, boolean habitability, Trabant parentTrabant) {
		super(size, mass, apoapsis, periapsis, habitability, parentTrabant);
	}
	
	public BarrenMoon(double size, double mass, double apoapsis, double periapsis, boolean habitability) {
		this(size, mass, apoapsis, periapsis, habitability, null);
	}
	
	public BarrenMoon(double size, double mass, double apoapsis, double periapsis, boolean habitability, boolean antiClock) {
		this(size, mass, apoapsis, periapsis, habitability, null);
	}
}
