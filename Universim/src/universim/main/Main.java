package universim.main;

import universim.abstractClasses.Star;
import universim.abstractClasses.SubTrabant;
import universim.abstractClasses.Trabant;
import universim.generalClasses.*;
import universim.threads.*;
import universim.ui.*;

public class Main implements Runnable{
	protected int refreshrate;
//	protected int[] refreshratePerSpeed;							//Deprecated
	protected int ticksPerUpdate;
	protected int[] ticksArray;
	protected int framerate;
	protected long optimalRefreshTime;
	protected long optimalFrameTime;
	protected int secondsPerRefresh;
	protected int gameSpeed;
	protected Date date;
	private boolean running;
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
//	TODO Improve orbit calculation
//	TODO Introduce 3. dimension
//	TODO Method for determinig stars
//	TODO Starclass analysis
//	TODO Improve Accuracy
	
//	TODO Indroduce update(int times), for multiple executions
//	TODO Improve update method for faster execution
	
	public Main() {
		refreshrate = 100;											//Ticks - physics updates - per second			
//		framerate = 60;												//Deprecated - currently only refreshrate is used
	    optimalRefreshTime = 1000000000 / refreshrate;				//Optimal time in nanoseconds
//	    optimalFrameTime = 1000000000 / framerate;					//Optimal time of frame in nanoseconds		
		ticksArray = new int[11];									//Array with stored "physics updates per tick" to cycle through
		ticksArray[1] = (int)(1);
		ticksArray[2] = (int)(30);
		ticksArray[3] = (int)(60);
		ticksArray[4] = (int)(60*8);
		ticksArray[5] = (int)(60*16);
		ticksArray[6] = (int)(60*24);
		ticksArray[7] = (int)(60*24*3);
		ticksArray[8] = (int)(60*24*5);
		ticksArray[9] = (int)(60*24*10);
		ticksArray[10] = (int)(60*24*20);
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
	    long gameTime;
	    
	    
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
	        
	        //Draw canvas
	        setWidth(renderer.getWidth());
	        setHeight(renderer.getHeight());
			renderer.repaint();
	        
        	gameTime = System.nanoTime() - now;
        	if(gameTime < optimalRefreshTime) {
        		int sleepTime = (int)(optimalRefreshTime - gameTime);
        		int nanoSleepTime = sleepTime % 1000000;
        		int milliSleepTime = Math.max(sleepTime / 1000000 - 3, 0);	//-3 because the scheduler wakes up every 3 ms (so max precision is 3ms)
//	        	if(Math.random()>=0.999)
//	        		System.out.printf("gameTime: %d, optimalTime: %d, sleepTime: %d\n", gameTime, optimalRefreshTime, sleepTime);
	            try {
	        		Thread.sleep(milliSleepTime, nanoSleepTime);	
	            }
	            catch(Exception e) {
		        	System.out.printf("Exception with sleep, mST: %d, nST: %d\n", milliSleepTime, nanoSleepTime);
	            }
        	}
//		    System.out.printf("GameTime: %d, expectedGameTime: %f, now: %d\n", gameTime, 1000000000.0/refreshrate, System.nanoTime()-now);
//	        System.out.printf("LastLoopTime: %d, optimalTime: %d\n", lastLoopTime, optimalRefreshTime);
//	        System.out.printf("gameTime: %d, refreshrate: %d\n", gameTime, refreshrate);
	        //For remaining time use spin-lock - more precision
        	gameTime = System.nanoTime() - now;
        	while(gameTime<optimalRefreshTime) {
        		gameTime = System.nanoTime() - now;
        	}
//        	System.out.printf("GameTime: %d, expectedGameTime: %f, now: %d\n", gameTime, 1000000000.0/refreshrate, System.nanoTime()-now);
//	        System.out.printf("GameTime: %d, expectedGameTime: %f, now: %d, nownow: %d\n", gameTime, 1000000000.0/refreshrate, System.nanoTime(), now);
	    }
	}
	
	@SuppressWarnings("unused")
	public void updateGame(double delta) {
		//Increment date
//		date.nextCentiSecond(ticksArray[gameSpeed]);
		if(Maths.secondsPerTick==0.1)
			date.nextDeciSecond(ticksPerUpdate);
		else if(Maths.secondsPerTick==0.01)
			date.nextCentiSecond(ticksPerUpdate);
		//Update position of stellar objects
		//Multithreading attempt - Quite a bit slower when only "simulating" once in each thread
//		threads = new Thread[getData().getStars().size()];
//		for(int i=0;i<threads.length;i++) {
//			threads[i] = new Thread(new UpdateThread(getData().getStars().get(i), ticksPerUpdate));
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
			for(int l=0;l<ticksPerUpdate;l++) {
				for(Trabant j: i.getTrabants()) {
//					System.out.println("Updated Trabant");
					j.update();
					for(SubTrabant k: j.getSubTrabants()) {
//						System.out.println("Updated SubTrabant");
						k.update();
					}
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
		gameSpeed = Math.max(1, Math.min(10, gameSpeed + interval));
		ticksPerUpdate = ticksArray[gameSpeed];
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Data getData() {
		return data;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isRunning() {
		return running;
	}

	public int getFramerate() {
		return framerate;
	}

	public long getOptimalFrameTime() {
		return optimalFrameTime;
	}	

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
}
