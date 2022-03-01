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

@SuppressWarnings("serial")
public class GameDisplay extends JPanel {
	private Main main;
	private Renderer renderer;
	private Data data;
	private int panelX;
	private int panelY;
	private int centreX;
	private int centreY;
	private float scale;
	private CelestialBody centre;

	public GameDisplay(Main main, Renderer renderer, Data data) {
		this.main = main;
		this.renderer = renderer;
		this.data = data;
		setBackground(new Color(25, 25, 25));
		setBounds(0, 50, main.getWidth(), main.getHeight() - 50);

		panelX = 2100;
		panelY = 2100;
		centreX = 2500;
		centreY = 2400;
		scale = 1;
	}

	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		super.paintComponent(g);
		if (getCentre() != null) {
			panelX = centre.getX() - getWidth() / 2;
			panelY = centre.getY() - getHeight() / 2;
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
		double size;
		int objX;
		int objY;
		for (Star i : data.getStars()) {
			size = Math.max(i.getRelSize() * Maths.pixelPerSR * scale, i.getRelSize());
			objX = (int) (((i.getX() - centreX) * scale) - size + (getWidth() / 2));
			objY = (int) (((i.getY() - centreY) * scale) - size + (getHeight() / 2));
			g.setColor(i.getColour());
			g.fill((Shape) new Ellipse2D.Double(objX, objY, 2 * size, 2 * size));
			for (Trabant j : i.getTrabants()) {
				size = j.getRelSize() * Maths.pixelPerER * scale;
				j.detPos(this);
				if (size > 1) {
					objX = (int) (((j.getX() - centreX) * scale) - size + (getWidth() / 2));
					objY = (int) (((j.getY() - centreY) * scale) - size + (getHeight() / 2));
					g.setColor(Color.blue);
					g.fill((Shape) new Ellipse2D.Double(objX, objY, 2 * size, 2 * size));
				} else if (size > 0.2) {
					objX = (int) (((j.getX() - centreX) * scale) + (getWidth() / 2));
					objY = (int) (((j.getY() - centreY) * scale) + (getHeight() / 2));
					g.setColor(i.getColour());
					g.fill((Shape) new Ellipse2D.Double(objX - 1, objY - 1, 2, 2));
				} else if (size > 0.1) {
					objX = (int) (((j.getX() - centreX) * scale) + (getWidth() / 2));
					objY = (int) (((j.getY() - centreY) * scale) + (getHeight() / 2));
					g.setColor(i.getColour());
					g.fill((Shape) new Ellipse2D.Double(objX, objY, 1, 1));
				}
				for (SubTrabant k : j.getSubTrabants()) {
					size = k.getRelSize() * Maths.pixelPerMR * scale;
					k.detPos(this);
					if (size > 1) {
						objX = (int) (((k.getX() - centreX) * scale) - size + (getWidth() / 2));
						objY = (int) (((k.getY() - centreY) * scale) - size + (getHeight() / 2));
						g.setColor(Color.yellow);
						g.fill((Shape) new Ellipse2D.Double(objX, objY, 2 * size, 2 * size));
					} else if (size > 0.5) {
						objX = (int) (((k.getX() - centreX) * scale) + (getWidth() / 2));
						objY = (int) (((k.getY() - centreY) * scale) + (getHeight() / 2));
						g.setColor(i.getColour());
						g.fill((Shape) new Ellipse2D.Double(objX - 1, objY - 1, 2, 2));
					} else if (size > 0.3) {
						objX = (int) (((k.getX() - centreX) * scale) + (getWidth() / 2));
						objY = (int) (((k.getY() - centreY) * scale) + (getHeight() / 2));
						g.setColor(i.getColour());
						g.fill((Shape) new Ellipse2D.Double(objX, objY, 1, 1));
					}
				}
			}
		}
	}

	public void move(int x, int y) {
//		System.out.print("CXB: " + centreX);
		panelX += (int) (x / Math.min(scale, 5));
		centreX += (int) (x / Math.min(scale, 5));
		panelY += (int) (y / Math.min(scale, 5));
		centreY += (int) (y / Math.min(scale, 5));
//		System.out.println(", CXA: " + centreX + ", X: " + x + ", Scale: " + scale);
	}

	public int getcentreX() {
		return centreX;
	}

	public int getcentreY() {
		return centreY;
	}

	public CelestialBody getCentre() {
		return centre;
	}

	public void setCentre(CelestialBody centre) {
		this.centre = centre;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
