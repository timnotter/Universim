package universim.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import universim.main.Main;

public class TopBar extends JPanel{
	private Main main;
	private JLabel dateLabel;
	private JLabel gameSpeedLabel;
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
		createDateLabel();
		createGameSpeedLabel();
	}
	
	public void createDateLabel() {
		dateLabel = new JLabel(main.getDate().toString());
		dateLabel.setBounds(main.getWidth()-100, 0, 100, 25);
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		dateLabel.setBackground(new Color(100, 100, 100));
		Border border = BorderFactory.createLineBorder(Color.black);
		dateLabel.setBorder(border);
		dateLabel.setOpaque(true);
		add(dateLabel);
	}
	
	public void createGameSpeedLabel() {
		gameSpeedLabel = new JLabel("" + main.getGameSpeed());
		gameSpeedLabel.setBounds(main.getWidth()-100, 0, 100, 25);
		gameSpeedLabel.setHorizontalAlignment(JLabel.CENTER);
		gameSpeedLabel.setBackground(new Color(100, 100, 100));
		Border border = BorderFactory.createLineBorder(Color.black);
		gameSpeedLabel.setBorder(border);
		gameSpeedLabel.setOpaque(true);
		add(gameSpeedLabel);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawLine(main.getWidth()-100, 0, main.getWidth()-100, 50);
		setBounds(0, 0, renderer.getWidth(), 50);
		
		dateLabel.setBounds(main.getWidth()-100, 0, 100, 26);
		dateLabel.setText(main.getDate().toString());
		gameSpeedLabel.setBounds(main.getWidth()-100, 25, 100, 25);
		gameSpeedLabel.setText("" + main.getGameSpeed());
	}
}
