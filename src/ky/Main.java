package ky;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Main {
	
	static long time = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Screen screen = new Screen(720, 664, true, 60);
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		Asset nya = new Asset(group, 0, 0, 100, 100, 0.5);
		Entity nyaentity = new Entity(0, 100, new Asset[]{nya});
		
		Asset background = new Asset("background.png", 0, 0);
		background.rescale(664 / background.getHeight());
		
		
		background.setVisible(true, 0);
		nyaentity.setVisible(true, 1);
		
		while(screen.isVisible()) {
			if(screen.getKeyStatus(KeyEvent.VK_W)) {
				
			}
			nyaentity.setPos(nyaentity.getX() + 1, nyaentity.getY());
			System.out.println(Arrays.toString(nyaentity.getPos()));
			screen.update();
		}
		
	}
}
