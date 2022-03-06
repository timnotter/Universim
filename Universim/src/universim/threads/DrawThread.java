package universim.threads;

import universim.ui.Renderer;

public class DrawThread implements Runnable{
	Renderer renderer;
	
	public DrawThread(Renderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public void run() {
		renderer.repaint();
	}

}
