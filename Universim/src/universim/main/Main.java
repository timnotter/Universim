package universim.main;

import universim.abstractClasses.*;
import universim.generalClasses.Date;
import universim.generalClasses.Maths;
import universim.ui.Renderer;
import universim.threads.*;

public class Main implements Runnable{
	protected int refreshrate;
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
	protected Data data;
	
//	TODO Fix jittering
//	TODO Find optimal gameSpeeds
	
	public Main() {
		refreshrate = 10 * 60 * 60 * 24 * 5;	//Max of 5 days per second with 0.1 seconds per tick
		framerate = 60;
		setGameSpeed(1);
		date = new Date(1, 1, 2000);
		data = new Data(this);
		renderer = new Renderer(this, 1000000, 1000000, getData());
		listener = new Listener(this);
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
	    final long optimalTime = 1000000000 / refreshrate;					//Optimal time in nanoseconds
	    final long optimalFrameTime = 1000000000 / framerate;				//Optimal time of frame in nanoseconds
	    long lastFrameUpdate = 0;
	    long lastGameUpdate = 0;
	    while(running){
	        long now = System.nanoTime();
	        long updateLength = now - lastLoopTime;			//In nano seconds
	        lastLoopTime = now;
	        double delta = updateLength / (double)optimalTime;

//			if(Math.random()>=0.9999999)
//				System.out.printf("uL: %d, oT: %d, delta: %f\n", updateLength, optimalTime, delta);
	        
	        //GameSpeed options
	        if(!paused) {
	        	switch(getGameSpeed()) {
	        	case 1:
	        		if(lastGameUpdate+(2000000000)/Maths.ticksPerDay<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		
	        		break;
	        	case 2:
	        		if(lastGameUpdate+(1500000000)/Maths.ticksPerDay<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		break;
	        	case 3:
	        		if(lastGameUpdate+(1000000000)/Maths.ticksPerDay<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		break;
	        	case 4:
	        		if(lastGameUpdate+(500000000)/Maths.ticksPerDay<=now) {
	        			updateGame(delta);
	        			lastGameUpdate = now;
	        		}
	        		break;
	        	case 5:
		        	updateGame(delta);
        			lastGameUpdate = now;
		        	break;
	        	}
	        }
	        //Draw
	        if(now-lastFrameUpdate>=optimalFrameTime) {
		        width = getRenderer().getWidth();
		        height = getRenderer().getHeight();
		        Thread drawThread = new Thread(new DrawThread(renderer));
		        drawThread.start();
		        try {
					drawThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		        getRenderer().repaint();
		        lastFrameUpdate = now;
	        }
	        //Use sleep for "longer" pauses between ticks - less cpu usage
	        if(refreshrate<300) {
		        try{
		        	long gameTime = System.nanoTime() - now;
		        	if(gameTime < optimalTime) {
		        		int sleepTime = (int)(optimalTime - gameTime);
		        		int nanoSleepTime = sleepTime % 1000000;
		        		int milliSleepTime = sleepTime / 1000000;
//	        			if(Math.random()>=0.999)
//	        				System.out.printf("gameTime: %d, optimalTime: %d, sleepTime: %d\n", gameTime, optimalTime, sleepTime);
			            Thread.sleep(milliSleepTime, nanoSleepTime);
		        	}
//	        		System.out.printf("LastLoopTime: %d, optimalTime: %f\n", lastLoopTime, optimalTime);
//	            	System.out.printf("gameTime: %d, refreshrate: %d\n", gameTime, refreshrate);
		        }
		        catch(Exception e){
		        	System.out.println("Exception with sleep");
//		        	throw new IllegalStateException();
		        }
	        }
	        //Else use spin-lock - more precision
	        else {
	        	double gameTime = (double)System.nanoTime() - now;
	        	while(gameTime<optimalTime) {
	        		gameTime = (double)System.nanoTime() - now;
	        	}
//	        	System.out.printf("GameTime: %f, expectedGameTime: %f, now: %d, nownow: %d\n", gameTime, 1000000000.0/refreshrate, System.nanoTime(), now);
	        }
	    }
	}
	
	@SuppressWarnings("unused")
	public void updateGame(double delta) {
		//Increment date
		if(Maths.secondsPerTick==1) {
			date.nextSecond();
		}
		else {
			date.nextDeciSecond();
		}
		//Update position of stellar objects
		for(Star i: getData().getStars()) {
			for(Trabant j: i.getTrabants()) {
				j.update(renderer.getGameDisplay());
				for(SubTrabant k: j.getSubTrabants()) {
					k.update(renderer.getGameDisplay());
				}
			}
		}
//		renderer.getGameDisplay().calc();
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

	public void setGameSpeed(int interval) {
		gameSpeed = Math.max(1, Math.min(5, gameSpeed + interval));
//		System.out.printf("Gamespeed: %d\n", gameSpeed);
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Data getData() {
		return data;
	}	
}


