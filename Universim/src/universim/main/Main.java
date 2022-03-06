package universim.main;

import universim.abstractClasses.*;
import universim.generalClasses.Date;
import universim.generalClasses.Maths;
import universim.ui.Renderer;

public class Main implements Runnable{
	protected int refreshrate;
	protected int[] refreshratePerSpeed;
	protected int framerate;
	long optimalRefreshTime;
	long optimalFrameTime;
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
	protected Thread[] threads;
	
//	TODO Rethink scale panel
//	TODO Add stars and planets
//	TODO Meethod for determinig stars
//	TODO Starclass analysis
	
	public Main() {
//		refreshrate = 10 * 60 * 60 * 24 * 5;	//Max of 5 days per second with 0.1 seconds per tick
		refreshrate = 60;
		refreshratePerSpeed = new int[6];
		refreshratePerSpeed[1] = 10 * 60 * 60 * 12;
		refreshratePerSpeed[2] = 10 * 60 * 60 * 24;
		refreshratePerSpeed[3] = 10 * 60 * 60 * 24 * 2;
		refreshratePerSpeed[4] = 10 * 60 * 60 * 24 * 3;
		refreshratePerSpeed[5] = 10 * 60 * 60 * 24 * 10;
		framerate = 60;
	    optimalRefreshTime = 1000000000 / refreshrate;				//Optimal time in nanoseconds
	    optimalFrameTime = 1000000000 / framerate;					//Optimal time of frame in nanoseconds
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
	    long lastFrameUpdate = 0;
	    while(running){
	        long now = System.nanoTime();
	        long updateLength = now - lastLoopTime;			//In nano seconds
	        lastLoopTime = now;
	        double delta = updateLength / (double)optimalRefreshTime;

//			if(Math.random()>=0.9999999)
//				System.out.printf("uL: %d, oT: %d, delta: %f\n", updateLength, optimalTime, delta);
	        
	        //GameSpeed options
	        if(!paused) {
	        	updateGame(delta);
	        }
	        //Draw
	        if(now-lastFrameUpdate>=optimalFrameTime) {
	        	//Multithreading attempt - doesn't not help in any way
//		        width = getRenderer().getWidth();
//		        height = getRenderer().getHeight();
//		        Thread drawThread = new Thread(new DrawThread(renderer));
//		        drawThread.start();
//		        try {
//					drawThread.join();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//		        lastFrameUpdate = now;

		        width = getRenderer().getWidth();
		        height = getRenderer().getHeight();
		        getRenderer().repaint();
		        lastFrameUpdate = now;
	        	
	        }
	        //Use sleep if there are longer pauses between ticks (more than around 3.33 milliseconds) - less cpu usage
	        if(refreshrate<300) {
		        try{
		        	long gameTime = System.nanoTime() - now;
		        	if(gameTime < optimalRefreshTime) {
		        		int sleepTime = (int)(optimalRefreshTime - gameTime);
		        		int nanoSleepTime = sleepTime % 1000000;
		        		int milliSleepTime = sleepTime / 1000000;
//	        			if(Math.random()>=0.999)
//	        				System.out.printf("gameTime: %d, optimalTime: %d, sleepTime: %d\n", gameTime, optimalRefreshTime, sleepTime);
			            Thread.sleep(milliSleepTime, nanoSleepTime);
		        	}
//		        	System.out.printf("GameTime: %d, expectedGameTime: %f, now: %d, nownow: %d\n", gameTime, 1000000000.0/refreshrate, System.nanoTime(), now);
//	        		System.out.printf("LastLoopTime: %d, optimalTime: %f\n", lastLoopTime, optimalRefreshTime);
//	            	System.out.printf("gameTime: %d, refreshrate: %d\n", gameTime, refreshrate);
		        }
		        catch(Exception e){
		        	System.out.println("Exception with sleep");
//		        	throw new IllegalStateException();
		        }
	        }
	        //Else use spin-lock - more precision
	        else {
	        	long gameTime = System.nanoTime() - now;
	        	while(gameTime<optimalRefreshTime) {
	        		gameTime = System.nanoTime() - now;
	        	}
//	        	System.out.printf("GameTime: %d, expectedGameTime: %f, now: %d, nownow: %d\n", gameTime, 1000000000.0/refreshrate, System.nanoTime(), now);
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
//			System.out.println("Increment DeciSecond");
			date.nextDeciSecond();
		}
		//Update position of stellar objects
		//Multithreading attempt - Over 1000x slower
//		threads = new Thread[getData().getStars().size()];
//		for(int i=0;i<threads.length;i++) {
////			System.out.println(i);
//			threads[i] = new Thread(new UpdateThread(getData().getStars().get(i)));
//			threads[i].start();
//		}
//		
//		for(int i=threads.length-1;i>0;i--) {
//			try {
//				threads[i].join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		for(Star i: data.getStars()) {
			for(Trabant j: i.getTrabants()) {
				j.update();
				for(SubTrabant k: j.getSubTrabants()) {
					k.update();
				}
			}
		}
		
//		renderer.getGameDisplay().calc();
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public void setPaused(boolean newValue) {
		paused = newValue;
		if(!paused) {
			refreshrate = refreshratePerSpeed[gameSpeed];
		}
		else {
			refreshrate = framerate;
		}
		optimalRefreshTime = 1000000000 / refreshrate;
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

	public int getGameSpeed() {
		return gameSpeed;
	}

	public void setGameSpeed(int interval) {
		gameSpeed = Math.max(1, Math.min(5, gameSpeed + interval));
		if(!paused) {
			refreshrate = refreshratePerSpeed[gameSpeed];
			optimalRefreshTime = 1000000000 / refreshrate;
		}
//		System.out.printf("Gamespeed: %d\n", gameSpeed);
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Data getData() {
		return data;
	}	

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
}


