package ky;

import java.awt.event.KeyEvent;

public class Main {
	
	static long time = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Screen screen = new Screen(720, 664, true, 60);
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		Asset nya = new Asset(group, 200, 300, 100, 100, 0.5);
		Entity entity = new Entity(100, 100, new Asset[]{nya});
		Asset test = new Asset("missing.png", 300, 300, 100, 100);
		Asset background = new Asset("background.png", 0, 0);
		background.rescale(664 / background.getHeight());
		background.setVisible(true, 0);
		test.setVisible(true, 1);
		entity.setVisible(true, 1);
		//entity.setPos(200, 100);
		while(screen.isVisible()) {
			if(screen.getKeyStatus(KeyEvent.VK_W)) {
				
			}
			screen.update();
		}
		
	}
}
