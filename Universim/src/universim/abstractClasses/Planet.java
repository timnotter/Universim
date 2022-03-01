package universim.abstractClasses;


public abstract class Planet extends Trabant{
	protected int population;
	protected boolean habitability;
	
	
	public Planet(double size, double mass, double orbitRadius, boolean habitability, Star parentStar) {
		super(size, mass, orbitRadius, parentStar);
		this.habitability = habitability;
	}
	public int getPopulation() {
		return population;
	}
	public int setPopulation(int population) {
		this.population = population;
		return population;
	}
	public boolean getHabitability() {
		return habitability;
	}
	public boolean setHabitability(boolean habitability) {
		this.habitability = habitability;
		return habitability;
	}
}
