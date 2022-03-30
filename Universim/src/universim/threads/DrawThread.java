package universim.threads;

import universim.main.Main;
import universim.ui.Renderer;

public class DrawThread implements Runnable{				//Deprecated for performance
	protected Renderer renderer;
	protected Main main;
	protected int framerate;
	protected long optimalFrameTime;
	
	
	public DrawThread(Main main, Renderer renderer) {
		this.main = main;
		this.renderer = renderer;
		framerate = main.getFramerate();
		optimalFrameTime = main.getOptimalFrameTime();
	}
	
	@Override
	public void run() {
		long now;
		while(main.isRunning()) {
			now = System.nanoTime();
	        main.setWidth(renderer.getWidth());
	        main.setHeight(renderer.getHeight());
			renderer.repaint();
			
	        try{
	        	long frameTime = (System.nanoTime() - now);
//	        	System.out.printf("Last now: %d, Now now: %d, frameTime: %d\n", now, System.nanoTime(), frameTime);
	        	if(frameTime < optimalFrameTime) {
	        		int sleepTime = (int)((optimalFrameTime - frameTime)/1000000);
//	        		System.out.printf("SleepTime: %d, frameTime: %d, optimaleFrameTime: %d\n", sleepTime, frameTime, optimalFrameTime);
		            Thread.sleep(sleepTime);
	        	}
	        }
	        catch(Exception e){
	        	System.out.println("Exception with sleep in drawThreads");
//		        	throw new IllegalStateException();
	        }
		}
	}

}
