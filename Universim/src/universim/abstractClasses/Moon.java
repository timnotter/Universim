package universim.abstractClasses;

public abstract class Moon extends SubTrabant{
	protected boolean habitability;

	public Moon(double size, double mass, double orbitRadius, boolean habitability, Trabant parentTrabant) {
		this(size, mass, orbitRadius, orbitRadius, habitability, parentTrabant);
	}
	
	public Moon(double size, double mass, double apoapsis, double periapsis, boolean habitability, Trabant parentTrabant) {
		this(size, mass, apoapsis, periapsis, false, parentTrabant, true);
	}
	
	public Moon(double size, double mass, double apoapsis, double periapsis, boolean habitability, Trabant parentTrabant, boolean antiClock) {
		super(size, mass, apoapsis, periapsis, parentTrabant, antiClock);
		this.habitability = habitability;
	}

}
