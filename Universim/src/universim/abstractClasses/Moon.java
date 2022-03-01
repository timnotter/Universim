package universim.abstractClasses;

public abstract class Moon extends SubTrabant{
	protected boolean habitability;

	public Moon(double size, double mass, double orbitRadius, boolean habitability, Trabant parentTrabant) {
		super(size, mass, orbitRadius, parentTrabant);
		this.habitability = habitability;
	}

}
