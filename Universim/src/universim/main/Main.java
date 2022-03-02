package universim.main;

import universim.abstractClasses.*;
import universim.generalClasses.Date;
import universim.generalClasses.Maths;
import universim.ui.Renderer;

public class Main implements Runnable{
	protected int framerate;
	protected int gameSpeed;
	protected Date date;
	protected boolean running;
	protected boolean paused;
	protected Thread thread;
	protected Renderer renderer;
	protected Listener listener;
	protected int width;
	protected int height;
	protected int hour;
	protected Data data;
	
//	TODO Fix jittering
	
	public Main() {
		framerate = 480;
		setGameSpeed(1);
		date = new Date(1, 1, 2000);
		data = new Data(this);
		renderer = new Renderer(this, 1000000, 1000000, getData());
		listener = new Listener(this);
		hour = 0;
		getRenderer().addKeyListener(listener);
	}
	
	public Date getDate() {
		return date;
	}
	
	public void start() {
		running = true;
		paused = true;
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		running = false;
	}
	
	public void run(){
	    long lastLoopTime = System.nanoTime();
	    final long OptimalTime = 1000000000 / framerate;
	    long lastFpsTime = 0;
	    long lastGameUpdate = 0;
	    while(running){
	        long now = System.nanoTime();
	        long updateLength = now - lastLoopTime;
	        lastLoopTime = now;
	        double delta = updateLength / ((double)OptimalTime);

	        lastFpsTime += updateLength;
	        if(lastFpsTime >= 1000000000){
	            lastFpsTime = 0;
	        }
	        
	        //GameSpeed options
	        if(!paused) {
	        	switch(getGameSpeed()) {
	        	case 1:
	        		if(lastGameUpdate+(1000000000)/24/Maths.secondsPerTick<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		
	        		break;
	        	case 2:
	        		if(lastGameUpdate+(500000000)/24/Maths.secondsPerTick<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		break;
	        	case 3:
	        		if(lastGameUpdate+(250000000)/24/Maths.secondsPerTick<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		break;
	        	case 4:
	        		if(lastGameUpdate+(125000000)/24/Maths.secondsPerTick<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		break;
	        	case 5:
		        	updateGame(delta);
		        	break;
	        	}
	        }
	        updateKeyTimeouts();
	        width = getRenderer().getWidth();
	        height = getRenderer().getHeight();
	        getRenderer().repaint();
//	        System.out.println("Width: " + width + ", Height" + height);
//	        System.out.println("Again");

	        try{
	        	long gameTime = (lastLoopTime - System.nanoTime() + OptimalTime) / 1000000;
//	            System.out.println("Game time: " + gameTime + ", expected game time: " + (1000 / framerate));
	            Thread.sleep(gameTime);
	        }
	        catch(Exception e){
	        }
	    }
	}
	
	public void updateGame(double delta) {
		if(hour==24*60) {
			date.nextDay();
			hour = 0;
		}
		else {
			hour++;
		}
		for(Star i: getData().getStars()) {
			for(Trabant j: i.getTrabants()) {
				j.update(renderer.getGameDisplay());
				for(SubTrabant k: j.getSubTrabants()) {
					k.update(renderer.getGameDisplay());
				}
			}
		}
	}
	
	public void tick() {
		if(hour==24*60) {
			date.nextDay();
			hour = 0;
		}
		else {
			hour++;
		}
		for(Star i: getData().getStars()) {
			for(Trabant j: i.getTrabants()) {
				j.update(renderer.getGameDisplay());
				for(SubTrabant k: j.getSubTrabants()) {
					k.update(renderer.getGameDisplay());
				}
			}
		}
	}
	
	public void updateKeyTimeouts() {
		
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public boolean setPaused(boolean newValue) {
		paused = newValue;
		return paused;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public static void main(String[] args) {
		Main main = new Main();
		
		main.start();
	}

	public int getGameSpeed() {
		return gameSpeed;
	}

	public void setGameSpeed(int gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Data getData() {
		return data;
	}	
}


