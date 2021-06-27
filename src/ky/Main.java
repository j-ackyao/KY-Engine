package ky;

public class Main {
	
	static long time = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Screen screen = new Screen(720, 664, true, 2);
		
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png","arigatou.png"};
		Asset nya = new Asset(group, 200, 300, 100, 100);
		Asset background = new Asset("background.png", 0, 0);
		background.rescale(664 / background.height);
		screen.add(nya, 1);
		screen.add(background, 0);
		
		while(true) {
			screen.update();
		}
		
	}
}
