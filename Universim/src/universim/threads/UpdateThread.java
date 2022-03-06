package universim.threads;

import universim.abstractClasses.Star;
import universim.abstractClasses.Trabant;
import universim.abstractClasses.SubTrabant;

public class UpdateThread implements Runnable{
	protected Star star;
	
	public UpdateThread(Star star) {
		this.star = star;
	}
	
	@Override
	public void run() {
		for(Trabant i: star.getTrabants()) {
//			System.out.println("Updated Trabant");
			i.update();
			for(SubTrabant j: i.getSubTrabants()) {
//				System.out.println("Updated SubTrabant");
				j.update();
			}
		}
	}

}
