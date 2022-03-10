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
	
	public Listener(Main main) {
		this.main = main;
		data = main.getData();
		renderer = main.getRenderer();
		gameDisplay = renderer.getGameDisplay();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("Released: " + e.getKeyCode());
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
			gameDisplay.setScale(20.0/19);
		}
		if(e.getKeyCode()==80) {//p - zoom out
			gameDisplay.setScale(0.95);
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("Released: " + e.getKeyCode());
		if(e.getKeyCode()==0) {//Paragraph
			gameDisplay.setScale(1/gameDisplay.getScale());
		}
		if(e.getKeyCode()==49) {//1
			gameDisplay.nextSun(-1);
		}
		if(e.getKeyCode()==50) {//2
			gameDisplay.nextSun(1);
		}
		if(e.getKeyCode()==51) {//3
			gameDisplay.nextTrabant(-1);
		}
		if(e.getKeyCode()==52) {//4
			gameDisplay.nextTrabant(1);
		}
		if(e.getKeyCode()==53) {//5
			gameDisplay.nextSubTrabant(-1);
		}
		if(e.getKeyCode()==54) {//6
			gameDisplay.nextSubTrabant(1);
		}
		if(e.getKeyCode()==27) {//Escape
			gameDisplay.resetCentre();
		}
		if(e.getKeyCode()==32) {//Space
			main.setPaused(!main.getPaused());
//			System.out.println(main.getPaused());
		}
		if(e.getKeyCode()==33) {//Pg up
			main.setGameSpeed(1);
		}
		if(e.getKeyCode()==34) {//Pg down
			main.setGameSpeed(-1);
		}
		if(e.getKeyCode()==78) {//n
			for(int i=0;i<10*60*60;i++)
				main.updateGame(1);
		}
		
	}
}
