package ky;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Main extends Screen {
	private static final long serialVersionUID = 1L;

	public Main(int width, int height, boolean resizable, int FPScap) {
		super(width, height, resizable, FPScap);
	}

	public static void main(String[] args) {
		new Main(720, 664, true, 60);
	}
	
	Entity nyaentity = null;

	@Override
	public void start() {
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		Asset nya = new Asset(group, 0, 0, 100, 100, 0.5);
		nyaentity = new Entity(0, 100, new Asset[]{nya});
		
		Asset background = new Asset("background.png", 0, 0);
		background.rescale(664 / background.getHeight());
		
		
		background.setVisible(true, 0);
		nyaentity.setVisible(true, 1);
	}
	

	@Override
	public void update() {
		double speed = 5d;
		
		
		if(getKeyStatus(KeyEvent.VK_W)) {
			nyaentity.setPos(nyaentity.getX(), nyaentity.getY() - speed);
		}
		if(getKeyStatus(KeyEvent.VK_S)) {
			nyaentity.setPos(nyaentity.getX(), nyaentity.getY() + speed);
		}
		if(getKeyStatus(KeyEvent.VK_A)) {
			nyaentity.setPos(nyaentity.getX() - speed, nyaentity.getY());
		}
		if(getKeyStatus(KeyEvent.VK_D)) {
			nyaentity.setPos(nyaentity.getX() + speed, nyaentity.getY());
		}
		System.out.println(Arrays.toString(nyaentity.getPos()));
	}
}
