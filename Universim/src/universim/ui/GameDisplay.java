package universim.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

import universim.abstractClasses.*;
import universim.generalClasses.Maths;
import universim.main.Data;
import universim.main.Main;

@SuppressWarnings({"serial", "unused"})
public class GameDisplay extends JPanel {
	protected Main main;
	protected Renderer renderer;
	protected Data data;
	protected CelestialBody centre;
	protected double centreX;
	protected double centreY;
	protected double scale;
	protected double pixelPerLY;
	protected double pixelPerAU;
	protected double pixelPerEM;
	protected double pixelPerSR;
	protected double pixelPerER;
	protected double pixelPerMR;

	public GameDisplay(Main main, Renderer renderer, Data data) {
		this.main = main;
		this.renderer = renderer;
		this.data = data;
		setBackground(new Color(25, 25, 25));
		setBounds(0, 50, main.getWidth(), main.getHeight() - 50 - renderer.getInsets().top);

		centreX = 0;
		centreY = 0;
		scale = 1;
		updateDistances();
	}

	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		super.paintComponent(g);
		// Update bounds to window size
		setBounds(0, 50, main.getWidth(), main.getHeight() - 50 - renderer.getInsets().top);
		
		// Draw stars
		draw(g);
	}
	
	public void draw(Graphics2D g) {
		double sSize, pSize, mSize;
		int sX, sY, pX, pY, mX, mY;
		for (Star i : data.getStars()) {
			sSize = i.getRelSize() * pixelPerSR;
			if (getCentre() != null) {
				centreX = centre.getX();
				centreY = centre.getY();
			}
			sX = (int)(((i.getX() - centreX)) * pixelPerLY) + ((getWidth() / 2) + 1);
			sY = (int)(((i.getY() - centreY)) * pixelPerLY) + ((getHeight() / 2) + 1);
			Color starColour = i.getColour();
			if(sSize>=2) {
				g.setColor(starColour);
				g.fill((Shape) new Ellipse2D.Double(sX - (int)sSize, sY - (int)sSize, 2 * (int)sSize + 1, 2 * (int)sSize + 1));
			}
			else if(sSize > 1D/100000000) {
				g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(sSize * 100000000), 255)));
				g.fill((Shape) new Ellipse2D.Double(sX-1, sY-1, 3, 3));
			}
			for (Trabant j : i.getTrabants()) {
				pSize = j.getRelSize() * pixelPerER;
				if (getCentre() != null) {
					centreX = centre.getX();
					centreY = centre.getY();
				}
				pX = (int)(((j.getX() - centreX)) * pixelPerLY) + ((getWidth() / 2) + 1);
				pY = (int)(((j.getY() - centreY)) * pixelPerLY) + ((getHeight() / 2) + 1);
//				System.out.printf("getX: %f, centreX: %f, equals: %b, getY: %f, centreY: %f\n", (j.getX())*1000000000, (centreX)*1000000000, j.getX()==centreX, j.getY(), centreY);
//				System.out.printf("centre==centre: %b\n", j.equals(centre));
//				System.out.printf("size: %f, x: %d, y: %d, centreX: %f, centreY: %f\n", pSize, pX, pY, centreX, centreY);
//				System.out.printf("pX: %d, pY: %d, pSize: %f, posLX, %d, posLY, %d, posRX: %d, posRY: %d\n", pX, pY, pSize, pX-(int)pSize, pY-(int)pSize, pX+1+(int)pSize, pY+1+(int)pSize);
				if (pSize > 1) {
					g.setColor(new Color(50, 190, 255));
					g.fill((Shape) new Ellipse2D.Double(pX - (int)pSize, pY - (int)pSize, 2 * (int)pSize + 1, 2 * (int)pSize + 1));
				}
				else if(pSize > 0.1) {
					g.setColor(starColour);
					g.fill((Shape) new Ellipse2D.Double(pX, pY, 1, 1));
				}
				else if(pSize > 1D/100) {
					g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(pSize * 255) * 10, 255)));
					g.fill((Shape) new Ellipse2D.Double(pX, pY, 1, 1));
				}
				for (SubTrabant k : j.getSubTrabants()) {
					mSize = k.getRelSize() * pixelPerMR;
					if (getCentre() != null) {
						centreX = centre.getX();
						centreY = centre.getY();
					}
					mX = (int)(((k.getX() - centreX)) * pixelPerLY) + ((getWidth() / 2) + 1);
					mY = (int)(((k.getY() - centreY)) * pixelPerLY) + ((getHeight() / 2) + 1);
					if (mSize > 1) {
						g.setColor(Color.yellow);
						g.fill((Shape) new Ellipse2D.Double(mX - (int)mSize, mY - (int)mSize, 2 * (int)mSize + 1, 2 * (int)mSize + 1));
					}
					else if(mSize > 0.33) {
						g.setColor(starColour);
						g.fill((Shape) new Ellipse2D.Double(mX, mY, 1, 1));
					}
					else if(mSize > 1D/100) {
						g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), (int)(mSize * 255) * 3));
						g.fill((Shape) new Ellipse2D.Double(mX, mY, 1, 1));
					}
				}
			}
		}
	}
	
	public void updateDistances() {
		pixelPerLY = Maths.pixelPerLY * scale;
		pixelPerAU = Maths.refAU * pixelPerLY;
		pixelPerEM = Maths.refEM * pixelPerLY;
		pixelPerSR = Maths.refSR * pixelPerLY;
		pixelPerER = Maths.refER * pixelPerLY;
		pixelPerMR = Maths.refMR * pixelPerLY;
//		System.out.printf("%f, %f, %f, %f, %f, %f\n", pixelPerLY, pixelPerAU, pixelPerEM, pixelPerSR, pixelPerER, pixelPerMR);
	}
	
	//Calculate certain orbits for system outputs
	public void calc() {
		for (Star i : data.getStars()) {
			double sSize = Math.max(i.getRelSize() * pixelPerSR, i.getRelSize());
//			System.out.printf("RelSize: %f, ppSR: %f\n", i.getRelSize(), pixelPerSR);
//			System.out.println(size + ", " + i.getRelSize() + ", " + scale);
			int sX = (int) ((((i.getX() - centreX)) * pixelPerLY) + (getWidth() / 2) + 1);
			int sY = (int) ((((i.getY() - centreY)) * pixelPerLY) + (getHeight() / 2) + 1);
//			System.out.printf("pX: %d, pY: %d, pSize: %f, posLX, %d, posLY, %d, posRX: %d, posRY: %d\n", sX, sY, sSize, sX-(int)sSize, sY-(int)sSize, sX+1+(int)sSize, sY+1+(int)sSize);
			for (Trabant j : i.getTrabants()) {
				double pSize = j.getRelSize() * pixelPerER;
				int pX = (int) ((j.getRelX() * pixelPerAU) + sX);
				int pY = (int) ((j.getRelY() * pixelPerAU) + sY);				
				System.out.printf("pX: %d, pY: %d, pSize: %f, posLX, %d, posLY, %d, posRX: %d, posRY: %d\n", pX, pY, pSize, pX-(int)pSize, pY-(int)pSize, pX+1+(int)pSize, pY+1+(int)pSize);
			}
		}
	}
	
	//Move viewpoint
	public void move(int x, int y) {
		centreX += (x/1000000.0/scale);
		centreY += (y/1000000.0/scale);
		System.out.println(centreX + ", " + centreY);
	}

	public double getcentreX() {
		return centreX;
	}

	public double getcentreY() {
		return centreY;
	}

	public CelestialBody getCentre() {
		return centre;
	}

	public void setCentre(CelestialBody centre) {
		this.centre = centre;
	}

	public double getScale() {
		return scale;
	}
	
	//Set size of the displayed
	public void setScale(double factor) {
		this.scale *= factor;
		scale = Math.max(Math.min(scale, 10000000), 0.0000000000000001);
		System.out.println(scale);
		updateDistances();
	}
}
