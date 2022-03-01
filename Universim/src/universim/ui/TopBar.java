package universim.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import universim.main.Main;

public class TopBar extends JPanel{
	private Main main;
	private JLabel dateLabel;
	private Renderer renderer;
	
	public TopBar(Main main, Renderer renderer) {
		this.main = main;
		this.renderer = renderer;
		setLayout(null);
		setBackground(new Color(150, 150, 150));
		setBounds(0, 0, renderer.getWidth(), 50);
		
		Border border = BorderFactory.createLineBorder(Color.black);
		setBorder(border);
		createDateLabel();
	}
	
	public void createDateLabel() {
		dateLabel = new JLabel(main.getDate().toString());
		dateLabel.setBounds(main.getWidth()-100, 0, 100, 50);
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		dateLabel.setBackground(new Color(100, 100, 100));
		Border border = BorderFactory.createLineBorder(Color.black);
		dateLabel.setBorder(border);
		dateLabel.setOpaque(true);
		add(dateLabel);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(0));
		g.drawLine(main.getWidth()-100, 0, main.getWidth()-100, 50);
		setBounds(0, 0, renderer.getWidth(), 50);
		
		dateLabel.setBounds(main.getWidth()-100, 0, 100, 50);
		dateLabel.setText(main.getDate().toString());
	}
}
