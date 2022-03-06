package universim.abstractClasses;

import java.util.ArrayList;

import java.awt.Color;

import universim.generalClasses.Maths;
import universim.ui.GameDisplay;

public abstract class Star extends CelestialBody{
	protected int temperature;
	protected ArrayList<Trabant> trabants;
	protected String classification;
	private Color colour;
	
	public Star(double x, double y, double size, int temperature, double mass) {
		super(x, y, size, mass);
		this.temperature = temperature;
		trabants = new ArrayList();
		
		oRR = 29000*Maths.lightyear;				//Distance to galactic core
		rR = Maths.SR;
		mR = Maths.SMASS;	
		
	}
	
	@Override
	public void update() {
		
	}

	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public ArrayList<Trabant> getTrabants() {
		return trabants;
	}
	public ArrayList<Trabant> addTrabant(Trabant t){
		trabants.add(t);
		t.setParentStar(this);
		return trabants;
	}
	public ArrayList<Trabant> addTrabant(double size, double mass, double orbitRadius, boolean habitability){
//		TODO
		return trabants;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public void calculateClassification() {
//		TODO
		classification = "G";
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
}
