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
	protected double centreX;
	protected double centreY;
	protected double scale;
	protected double pixelPerLY;
	protected double pixelPerAU;
	protected double pixelPerEM;
	protected double pixelPerSR;
	protected double pixelPerER;
	protected double pixelPerMR;
	protected CelestialBody centre;

	public GameDisplay(Main main, Renderer renderer, Data data) {
		this.main = main;
		this.renderer = renderer;
		this.data = data;
		setBackground(new Color(25, 25, 25));
		setBounds(0, 50, main.getWidth(), main.getHeight() - 50);

		centreX = 2500;
		centreY = 2400;
		scale = 1;
		updateDistances();
	}

	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		super.paintComponent(g);
		if (getCentre() != null) {
			centreX = centre.getX();
			centreY = centre.getY();
		}
		// Update bounds to window size
		setBounds(0, 50, main.getWidth(), main.getHeight() - 50);

		// Draw edge
//		g.setColor(Color.black);
//		g.setStroke(new BasicStroke(10));
//		g.drawRect(0 - panelX, 0 - panelY, renderer.getWorldWidth() - 15, renderer.getWorldWidth() - 15);

		// Draw stars
		draw(g);
	}
	
	public void draw(Graphics2D g) {
//		TODO Displaying everything to scale
		double sSize, pSize, mSize;
		int sX, sY, pX, pY, mX, mY;
		for (Star i : data.getStars()) {
			sSize = Math.max(i.getRelSize() * pixelPerSR, i.getRelSize());
//			System.out.printf("RelSize: %f, ppSR: %f\n", i.getRelSize(), pixelPerSR);
//			System.out.println(size + ", " + i.getRelSize() + ", " + scale);
			sX = (int) ((((i.getX() - centreX)) * pixelPerLY) - sSize + (getWidth() / 2));
			sY = (int) ((((i.getY() - centreY)) * pixelPerLY) - sSize + (getHeight() / 2));
			g.setColor(i.getColour());
			g.fill((Shape) new Ellipse2D.Double(sX, sY, 2 * sSize, 2 * sSize));
			for (Trabant j : i.getTrabants()) {
				pSize = j.getRelSize() * pixelPerER;
				pSize *= 30;
				pX = (int) ((((j.getX() - i.getX()) * Maths.lightyear / Maths.AU * pixelPerAU) - pSize) + sX + sSize);
				pY = (int) ((((j.getY() - i.getY()) * Maths.lightyear / Maths.AU * pixelPerAU) - pSize) + sY + sSize);
				g.setColor(Color.blue);
				g.setColor(new Color(50, 190, 255));
				g.fill((Shape) new Ellipse2D.Double(pX, pY, 2 * pSize, 2 * pSize));
				for (SubTrabant k : j.getSubTrabants()) {
					mSize = k.getRelSize() * pixelPerMR;
					mSize *= 30;
//					System.out.println(mSize);
					mX = (int) ((((k.getX() - j.getX()) * Maths.lightyear / Maths.EM * pixelPerEM) - mSize) + pX + pSize);
					mY = (int) ((((k.getY() - j.getY()) * Maths.lightyear / Maths.EM * pixelPerEM) - mSize) + pY + pSize);
//					System.out.printf("pX: %d, pY: %d, mX: %d, mY: %d\n", pX, pY, mX, mY);
					g.setColor(Color.yellow);
					g.fill((Shape) new Ellipse2D.Double(mX, mY, 2 * mSize, 2 * mSize));
				}
			}
		}
	}

//	public void oldDraw(Graphics2D g) {
//		double size;
//		int objX;
//		int objY;
//		for (Star i : data.getStars()) {
//			size = Math.max(i.getRelSize() * Maths.pixelPerSR * scale, i.getRelSize());
//			objX = (int) (((i.getX() - centreX) * scale) - size + (getWidth() / 2));
//			objY = (int) (((i.getY() - centreY) * scale) - size + (getHeight() / 2));
//			g.setColor(i.getColour());
//			g.fill((Shape) new Ellipse2D.Double(objX, objY, 2 * size, 2 * size));
//			for (Trabant j : i.getTrabants()) {
//				size = j.getRelSize() * Maths.pixelPerER * scale;
//				j.detPos(this);
//				System.out.println(j.getX() + j.getY());
//				if (size > 1) {
//					objX = (int) (((j.getX() - centreX) * scale) - size + (getWidth() / 2));
//					objY = (int) (((j.getY() - centreY) * scale) - size + (getHeight() / 2));
//					g.setColor(Color.blue);
//					g.setColor(new Color(50,190, 255));
//					g.fill((Shape) new Ellipse2D.Double(objX, objY, 2 * size, 2 * size));
//				} else if (size > 0.2) {
//					objX = (int) (((j.getX() - centreX) * scale) + (getWidth() / 2));
//					objY = (int) (((j.getY() - centreY) * scale) + (getHeight() / 2));
//					g.setColor(i.getColour());
//					g.fill((Shape) new Ellipse2D.Double(objX - 1, objY - 1, 2, 2));
//				} else if (size > 0.1) {
//					objX = (int) (((j.getX() - centreX) * scale) + (getWidth() / 2));
//					objY = (int) (((j.getY() - centreY) * scale) + (getHeight() / 2));
//					g.setColor(i.getColour());
//					g.fill((Shape) new Ellipse2D.Double(objX, objY, 1, 1));
//				}
//				for (SubTrabant k : j.getSubTrabants()) {
//					size = k.getRelSize() * Maths.pixelPerMR * scale;
//					k.detPos(this);
//					if (size > 1) {
//						objX = (int) (((k.getX() - centreX) * scale) - size + (getWidth() / 2));
//						objY = (int) (((k.getY() - centreY) * scale) - size + (getHeight() / 2));
//						g.setColor(Color.yellow);
//						g.fill((Shape) new Ellipse2D.Double(objX, objY, 2 * size, 2 * size));
//					} else if (size > 0.5) {
//						objX = (int) (((k.getX() - centreX) * scale) + (getWidth() / 2));
//						objY = (int) (((k.getY() - centreY) * scale) + (getHeight() / 2));
//						g.setColor(i.getColour());
//						g.fill((Shape) new Ellipse2D.Double(objX - 1, objY - 1, 2, 2));
//					} else if (size > 0.3) {
//						objX = (int) (((k.getX() - centreX) * scale) + (getWidth() / 2));
//						objY = (int) (((k.getY() - centreY) * scale) + (getHeight() / 2));
//						g.setColor(i.getColour());
//						g.fill((Shape) new Ellipse2D.Double(objX, objY, 1, 1));
//					}
//				}
//			}
//		}
//	}
	
	public void updateDistances() {
		pixelPerLY = Maths.pixelPerLY * scale;
		pixelPerAU = Maths.refAU * pixelPerLY;
		pixelPerEM = Maths.refEM * pixelPerLY;
		pixelPerSR = Maths.refSR * pixelPerLY;
		pixelPerER = Maths.refER * pixelPerLY;
		pixelPerMR = Maths.refMR * pixelPerLY;
//		System.out.printf("%f, %f, %f, %f, %f, %f\n", pixelPerLY, pixelPerAU, pixelPerEM, pixelPerSR, pixelPerER, pixelPerMR);
	}

	public void move(int x, int y) {
		centreX += (x/1000000.0/scale);
		centreY += (y/1000000.0/scale);
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

	public void setScale(double factor) {
		this.scale *= factor;
		updateDistances();
	}
}
