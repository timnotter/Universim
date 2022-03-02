package universim.dataAnalysis;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import universim.generalClasses.Maths;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class starAnalysis {
	public static ArrayList<Dot> dots = new ArrayList();
	public static JFrame jframe;
	public static JPanel jpanel;
	public static int[][] pixel;
	
	public static void draw() {
		jframe= new JFrame();
		jframe.setTitle("Universim");
		jframe.setSize(500, 800);
		jframe.setMinimumSize(new Dimension(500, 800));
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setLayout(null);
		jframe.setVisible(true);
		
		jpanel = new JPanel();
		jpanel.setBackground(Color.white);
		jpanel.setBounds(0, 0, jframe.getWidth(), jframe.getHeight());
		jframe.add(jpanel);
		
		pixel = new int[500][800];
	}
	
	public static void update() {
		Graphics2D g = (Graphics2D)jpanel.getGraphics();
//		g.setColor(new Color(255, 0, 0, 10));
		maxmin();
		for(Dot i: dots) {
				Double bp_rp = i.bp_rp + 1;
				Double mg = i.mg + 5;
				int pixelX = (int)(bp_rp*72);
				int pixelY = (int)(mg * 35);
				pixel[pixelX][pixelY]++;
				
		}
		redraw();
	}
	
	public static void redraw() {
		Graphics2D g = (Graphics2D)jpanel.getGraphics();
		for(int i=0;i<500;i++) {
			for(int j=0;j<800;j++) {
				if(pixel[i][j]==0)
					continue;
				g.setColor(new Color(Math.max(Math.min(pixel[i][j]*10, 255), 0), Math.max(Math.min(pixel[i][j], 255), 0), 0));
				g.fillRect(i, j, 1, 1);
			}
		}
		g.setColor(new Color(0, 0, 255));
		g.drawLine((int)(0*72), 0, (int)(0*72), jframe.getHeight());
		g.drawLine((int)(1*72), 0, (int)(1*72), jframe.getHeight());
		g.drawLine((int)(2*72), 0, (int)(2*72), jframe.getHeight());
		g.drawLine((int)(3*72), 0, (int)(3*72), jframe.getHeight());
		
		g.drawLine(0, (int)(5*35), jframe.getWidth(), (int)(5*35));
		g.drawLine(0, (int)(10*35), jframe.getWidth(), (int)(10*35));
		g.drawLine(0, (int)(15*35), jframe.getWidth(), (int)(15*35));
		g.drawLine(0, (int)(20*35), jframe.getWidth(), (int)(20*35));
	}
	
	public static void maxmin() {
		Double maxX = 0.0;
		Double minX = 1000.0;
		Double maxY = 0.0;
		Double minY = 1000.0;
		
		for(Dot i: dots) {
			maxX = Math.max(maxX, i.bp_rp);
			minX = Math.min(minX, i.bp_rp);
			maxY = Math.max(maxY, i.mg);
			minY = Math.min(minY, i.mg);
		}
		System.out.println("MaxX: " + maxX + ", MinX: " + minX + ", MaxY: " + maxY + ", MinY: " + minY);
	}
	
	public static void input(File file, boolean parallax) {
		try {
			Scanner scanner = new Scanner(file);
			int counter = 0;
			int counter2 = 0;
			while(scanner.hasNextDouble()&&counter2<5) {
				Double mg = scanner.nextDouble();
				Double bp_rp = scanner.nextDouble();
				Double parallaxD = Double.MAX_VALUE;
				if(parallax)
					parallaxD = scanner.nextDouble();
				dots.add(new Dot(mg, bp_rp, parallaxD));
				counter++;
				if(counter==100000) {
					counter2++;
					System.out.println(counter2 + "00000");
					counter = 0;
				}
				scanner.nextLine();
			}
			scanner.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
	}
	
	public static void main(String[] args) {
//		int sunTemp = Maths.temperatureAprox(0.63);
//		System.out.println(sunTemp);
//		double sunLum = Maths.luminosityAprox(4.83);
//		System.out.println(sunLum);
//		double sunRadius = Maths.radiusAprox(sunTemp, sunLum);
//		System.out.println(sunRadius);
		
//		File file = new File("dataSetGaia3OBPDesc.json");
//		input(file, false);
		File file = new File("src\\universim\\files\\datasetGaia3NSOBPDESC.json");
		input(file, true);
		analysis();
		draw();
		update();
		while(true) {
			redraw();
		}
	}
	
	public static void analysis() {
		float count = 0;
		int O = 0;
		int B = 0;
		int A = 0;
		int F = 0;
		int G = 0;
		int K = 0;
		int M = 0;
		int D = 0;
		int I = 0;
		int II = 0;
		int III = 0;
		int IV = 0;
		int V = 0;
		int VI = 0;
		int VII = 0;
		for(Dot i: dots) {
			count++;
			int temp = Maths.temperatureAprox(i.bp_rp);
			if(Maths.isWhiteDwarf(i))
				D++;
			else if(temp>=30000)
				O++;
			else if(temp>=10000)
				B++;
			else if(temp>=7500)
				A++;
			else if(temp>=6000)
				F++;
			else if(temp>=5200)
				G++;
			else if(temp>=3700)
				K++;
			else if(temp>=2400)
				M++;
		}
		System.out.println("Count: " + count);
		System.out.println("O: " + O + ", B: " + B + ", A: " + A + ", F: " + F + ", G: " + G + ", K: " + K + ", M: " + M + ", D: " + D);
		System.out.println(O/count + ", " + B/count + ", " + A/count + ", " + F/count + ", " + G/count + ", " + K/count + ", " + M/count + ", " + D/count);
	}
}
