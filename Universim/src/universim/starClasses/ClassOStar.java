package universim.starClasses;

import universim.abstractClasses.Star;

public class ClassOStar extends Star{
//	O-Class: 30'000 K - 40'000 K
//	Main-sequence:
//		Mass: >= 16 SM
//		Radius: >= 6.6 SR
//		Luminosity: >= 30'000 SL
//	Sub-dwarfs(rare):
//		Luminosity: 1.5 - 2 Magnitudes dimmer (5 Magnitudes = * 100 - 1 Magnitude = * 2.5) -> smaller radius but higher temperature
	
	public ClassOStar(int x, int y, int size, int temperature, int mass) {
		super(x, y, size, temperature, mass);
	}

	public int temperature() {
		return 0;
	}

	public double size(int temperature) {
		return 0;
	}

}
