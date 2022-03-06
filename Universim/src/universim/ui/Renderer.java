package universim.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

import universim.main.Data;
import universim.main.Main;

@SuppressWarnings({"serial", "unused"})
public class Renderer extends JFrame{
	private Main main;
	private Data data;
	private int worldWidth;
	private int worldHeight;
	private TopBar topBar;
	private GameDisplay gameDisplay;
	
	public Renderer(Main main, int worldWidth, int worldHeight, Data data) {
		this.main = main;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.data = data;
		
		setTitle("Universim");
		setSize(1201, 801 + 30);
		setMinimumSize(new Dimension(801, 601));
//		setMinimumSize(new Dimension(1201, 801));
		main.setWidth(1201);
		main.setHeight(801 + 30);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		
		topBar = new TopBar(main, this);
		gameDisplay = new GameDisplay(main, this, data);
		
		add(topBar);
		add(getGameDisplay());
	}

	public GameDisplay getGameDisplay() {
		return gameDisplay;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}	
}
