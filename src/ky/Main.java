package ky;

public class Main {
	
	static long time = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Screen screen = new Screen(720, 664, true, 60);
		
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		Asset nya = new Asset(group, 200, 300, 100, 100, 0.5);
		Asset test = new Asset("missing.png", 300, 300, 100, 100);
		Asset background = new Asset("background.png", 0, 0);
		background.rescale(664 / background.getHeight());
		screen.add(nya, 2);
		screen.add(background, 1);
		screen.add(test, 1);
		
		while(screen.isVisible()) {
			
			screen.update();
		}
		
	}
}
