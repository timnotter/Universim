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

@SuppressWarnings({"serial"})
public class GameDisplay extends JPanel {
	protected Main main;
	protected Renderer renderer;
	protected Data data;
	protected int[] centreIndex;
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
		
		centreIndex = new int[3];
		centreIndex[0] = 0;
		centreIndex[1] = 0;
		centreIndex[2] = 0;
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
			if(sX-(int)sSize<getWidth()&&sX+(int)sSize>0&&sY-(int)sSize<getHeight()&&sY+(int)sSize>0) {
				if(sSize>=2) {
					g.setColor(starColour);
					g.fill((Shape) new Ellipse2D.Double(sX - (int)sSize, sY - (int)sSize, 2 * (int)sSize + 1, 2 * (int)sSize + 1));
				}
				else if(sSize > 1) {
					g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(sSize * 100000000), 255)));
//					g.drawRect(sX-1, sY-1, 3, 3);
					g.drawLine(sX-1, sY-1, sX+1, sY-1);
					g.drawLine(sX-1, sY+1, sX+1, sY+1);
					g.drawLine(sX-2, sY, sX+2, sY);
					g.drawLine(sX, sY-2, sX, sY+2);
	
				}
				else if(sSize > 1D/100000000) {
					g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(sSize * 100000000), 255)));
					g.fill((Shape) new Ellipse2D.Double(sX-1, sY-1, 3, 3));
				}
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
				if(pX-(int)pSize<getWidth()&&pX+(int)pSize>0&&pY-(int)pSize<getHeight()&&pY+(int)pSize>0) {
					if (pSize > 2) {
						g.setColor(new Color(50, 190, 255));
						g.fill((Shape) new Ellipse2D.Double(pX - (int)pSize, pY - (int)pSize, 2 * (int)pSize + 1, 2 * (int)pSize + 1));
					}
					else if(pSize > 0.01) {
						g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(105/2*pSize) + 150, 255)));
						g.fill((Shape) new Ellipse2D.Double(pX-1, pY-1, 3, 3));
					}
					else if(pSize > 0.0001) {
						g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(155 * pSize) * 100 + 100, 255)));
						g.drawLine(pX, pY, pX, pY);
					}
					else if(pSize > 0.000001) {
						g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(100 * pSize) * 10000, 255)));
						g.drawLine(pX, pY, pX, pY);
					}
				}
				for (SubTrabant k : j.getSubTrabants()) {
					mSize = k.getRelSize() * pixelPerMR;
					if (getCentre() != null) {
						centreX = centre.getX();
						centreY = centre.getY();
					}
					mX = (int)(((k.getX() - centreX)) * pixelPerLY) + ((getWidth() / 2) + 1);
					mY = (int)(((k.getY() - centreY)) * pixelPerLY) + ((getHeight() / 2) + 1);
//					System.out.printf("mX: %d, mY: %d, size: %f\n", mX, mY, mSize);
					if(mX-(int)mSize<getWidth()&&mX+(int)mSize>0&&mY-(int)mSize<getHeight()&&mY+(int)mSize>0) {
						if (mSize > 1) {
							g.setColor(Color.yellow);
							g.fill((Shape) new Ellipse2D.Double(mX - (int)mSize, mY - (int)mSize, 2 * (int)mSize + 1, 2 * (int)mSize + 1));
						}
						else if(mSize > 1.0/2550) {
							g.setColor(new Color(starColour.getRed(), starColour.getGreen(), starColour.getBlue(), Math.min((int)(mSize * 255) * 10 * 4, 255)));
							g.drawLine(mX, mY, mX, mY);
						}
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
	
	//Move viewpoint
	public void move(int x, int y) {
		centreX += (x/1000000.0/scale);
		centreY += (y/1000000.0/scale);
//		System.out.println(centreX + ", " + centreY);
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

	public void updateCentre() {
		//0:0:0 focuses on Star 1, 0:1:0 on Trabant 1 from Star 0, 0:1:1 on Subtrabant 1 from Trabant 1 from Star 1
//		System.out.printf("1: %d, 2: %d, 3: %d\n", centreIndex[0], centreIndex[1], centreIndex[2]);
		if(centreIndex[1]==0)
			centre = data.getStars().get(centreIndex[0]);
		else if(centreIndex[2]==0)
			centre = data.getStars().get(centreIndex[0]).getTrabants().get(centreIndex[1]-1);
		else
			centre = data.getStars().get(centreIndex[0]).getTrabants().get(centreIndex[1]-1).getSubTrabants().get(centreIndex[2]-1);
	}
	
	public void resetCentre() {
		centre = null;
	}
	
	public void nextSun(int inc) {				//Only works correct for inc==1
		if(centre==null) {
			centreIndex[1] = 0;
			centreIndex[2] = 0;
			updateCentre();
		}
		else {
			centreIndex[0] += inc;
			if(centreIndex[0]>=data.getStars().size()) {
				centreIndex[0] = 0;
			}
			else if(centreIndex[0]<0) {
				centreIndex[0] = data.getStars().size() - 1;
			}
			centreIndex[1] = 0;
			centreIndex[2] = 0;
			updateCentre();
		}
	}
	
	public void nextTrabant(int inc) {			//Only works correct for inc==1
		if(centre==null) {
			centreIndex[2] = 0;
			updateCentre();
		}
		else {
			centreIndex[1] += inc;
			if(centreIndex[1]>data.getStars().get(centreIndex[0]).getTrabants().size()) {
				centreIndex[1] = 0;
			}
			else if(centreIndex[1]<0) {
				centreIndex[1] = data.getStars().get(centreIndex[0]).getTrabants().size() - 1;
			}
			centreIndex[2] = 0;
			updateCentre();
		}
	}
	
	public void nextSubTrabant(int inc) {		//Only works correct for inc==1
		if(centre==null) {
			updateCentre();
		}
		else {
			if(centreIndex[1]==0)
				return;
			centreIndex[2] += inc;
			if(centreIndex[2]>data.getStars().get(centreIndex[0]).getTrabants().get(centreIndex[1]-1).getSubTrabants().size()) {
				centreIndex[2] = 0;
			}
			else if(centreIndex[2]<0) {
				centreIndex[2] = data.getStars().get(centreIndex[0]).getTrabants().get(centreIndex[1]-1).getSubTrabants().size()-1;
			}
			updateCentre();
		}
	}

	public double getScale() {
		return scale;
	}
	
	//Set size of the displayed
	public void setScale(double factor) {
		this.scale *= factor;
		scale = Math.max(Math.min(scale, 10000000), 0.0000000000000001);
//		System.out.println(scale);
//		System.out.println(System.nanoTime());
		updateDistances();
	}
}
