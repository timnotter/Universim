package universim.threads;

import universim.abstractClasses.Star;
import universim.abstractClasses.Trabant;
import universim.abstractClasses.SubTrabant;

public class UpdateThread implements Runnable{
	protected Star star;
	protected int ticks;
	
	public UpdateThread(Star star, int ticks) {
		this.star = star;
		this.ticks = ticks;
	}
	
	@Override
	public void run() {
		for(int i=0;i<ticks;i++) {
			for(Trabant j: star.getTrabants()) {
//				System.out.println("Updated Trabant");
				j.update();
				for(SubTrabant k: j.getSubTrabants()) {
//					System.out.println("Updated SubTrabant");
					k.update();
				}
			}
		}
	}

}
