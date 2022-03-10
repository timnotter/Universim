package universim.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import universim.generalClasses.Maths;
import universim.main.Main;

public class TopBar extends JPanel{
	private Main main;
	private JLabel dateLabel;
	private JLabel gameSpeedLabel;
	private JLabel scaleLabel;
	private Renderer renderer;
	
	public TopBar(Main main, Renderer renderer) {
		this.main = main;
		this.renderer = renderer;
		setLayout(null);
		setBackground(new Color(150, 150, 150));
		setBounds(0, 0, renderer.getWidth(), 50);
		setMinimumSize(new Dimension(1201, 50));
		
		Border border = BorderFactory.createLineBorder(Color.black);
		setBorder(border);
		dateLabel = new JLabel(main.getDate().toString());
		createLabel(dateLabel, main.getWidth()-200, 0, 126, 31);
		gameSpeedLabel = new JLabel("" + main.getGameSpeed());
		createLabel(gameSpeedLabel, main.getWidth()-75, 75, 50, 31);
		scaleLabel = new JLabel("100 pixels: " + 0 + "m");
		createLabel(scaleLabel, main.getWidth()-200, 30, 200, 20);
//		System.out.println(scaleLabel.getFont().getName());
//		System.out.println(scaleLabel.getFont());
		scaleLabel.setFont(new Font("Dialog", Font.BOLD, 9));
	}
	
	public void createLabel(JLabel label, int x, int y, int width, int height) {
		label.setBounds(x, y, width, height);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(new Color(100, 100, 100));
		Border border = BorderFactory.createLineBorder(Color.black);
		label.setBorder(border);
		label.setOpaque(true);
		add(label);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawLine(main.getWidth()-100, 0, main.getWidth()-100, 50);
		setBounds(0, 0, renderer.getWidth(), 50);
		
		dateLabel.setBounds(main.getWidth()-200, 0, 126, 31);
		dateLabel.setText(main.getDate().toString());
		gameSpeedLabel.setBounds(main.getWidth()-75, 0, 75, 31);
		gameSpeedLabel.setText("" + main.getGameSpeed());
		scaleLabel.setBounds(main.getWidth()-200, 30, 200, 20);
		if(renderer.getGameDisplay().pixelPerLY<=200)
			scaleLabel.setText("100 pixels: " + (((double)((long)(100*(100/renderer.getGameDisplay().pixelPerLY))))/100) + " LY");
		else if(renderer.getGameDisplay().pixelPerAU<=1000) {
			scaleLabel.setText("100 pixels: " + (((double)((long)(100*(100/renderer.getGameDisplay().pixelPerAU))))/100) + " AU");
		}
		else if(renderer.getGameDisplay().pixelPerEM<=200) {
			scaleLabel.setText("100 pixels: " + (((double)((long)(100*(100/renderer.getGameDisplay().pixelPerSR))))/100) + " SR");
		}
		else if(renderer.getGameDisplay().pixelPerER<=200) {
			scaleLabel.setText("100 pixels: " + (((double)((long)(100*(100/renderer.getGameDisplay().pixelPerER))))/100) + " ER");
		}
		else {
			scaleLabel.setText("100 pixels: " + ((long)(100/renderer.getGameDisplay().pixelPerLY*Maths.lightyear/1000)) + " km");
		}
	}
}
