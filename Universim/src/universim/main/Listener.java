package universim.main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import universim.abstractClasses.*;
import universim.ui.GameDisplay;
import universim.ui.Renderer;

import java.awt.event.*;


public class Listener implements KeyListener{
	private Main main;
	private Data data;
	private Renderer renderer;
	private GameDisplay gameDisplay;
	private ArrayList<CelestialBody> targets;
	private int targetCounter;						//To iterate through list of all celestial bodies
	
	public Listener(Main main) {
		this.main = main;
		data = main.getData();
		renderer = main.getRenderer();
		gameDisplay = renderer.getGameDisplay();
		
		//Fill list of all
		targets = new ArrayList();
		for(Star i: data.getStars()) {
			targets.add(i);
			for(Trabant j: i.getTrabants()) {
				targets.add(j);
				for(SubTrabant k: j.getSubTrabants()) {
					targets.add(k);
				}
			}
		}
		targetCounter = 0;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Released: " + e.getKeyCode());
		if(e.getKeyCode()==37) {//right
			if(gameDisplay.getCentre()==null)
				gameDisplay.move(-1, 0);
		}
		if(e.getKeyCode()==38) {//down
			if(gameDisplay.getCentre()==null)
				gameDisplay.move(0, -1);
		}
		if(e.getKeyCode()==39) {//left
			if(gameDisplay.getCentre()==null)
				gameDisplay.move(1, 0);
		}
		if(e.getKeyCode()==40) {//up
			if(gameDisplay.getCentre()==null)
				gameDisplay.move(0, 1);
		}
		if(e.getKeyCode()==79) {//o - zoom in
//			gameDisplay.setScale(gameDisplay.getScale() + (float)0.03);
			gameDisplay.setScale(1.25);
//			System.out.println(gameDisplay.getScale());
		}
		if(e.getKeyCode()==80) {//p - zoom out
//			gameDisplay.setScale(Math.max(gameDisplay.getScale() - (float)0.03, (float)0.03));
			gameDisplay.setScale(0.80);
//			System.out.println(gameDisplay.getScale());
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("Released: " + e.getKeyCode());
		if(e.getKeyCode()==0) {//Paragraph
			main.getRenderer().getGameDisplay().setCentre(targets.get(targetCounter));
			if(targetCounter<targets.size()-1)
				targetCounter++;
			else
				targetCounter = 0;
		}
		if(e.getKeyCode()==27) {//Escape
			main.getRenderer().getGameDisplay().setCentre(null);
		}
		if(e.getKeyCode()==32) {//Space
			main.setPaused(!main.getPaused());
//			System.out.println(main.getPaused());
		}
		if(e.getKeyCode()==33) {//pg up
			main.setGameSpeed(Math.min(main.getGameSpeed()+1, 5));
//			System.out.println(main.getGameSpeed());
		}
		if(e.getKeyCode()==34) {//pg down
			main.setGameSpeed(Math.max(main.getGameSpeed()-1, 1));
//			System.out.println(main.getGameSpeed());
		}
		if(e.getKeyCode()==18) {//Left alt
			main.tick();
		}
		
	}
}
