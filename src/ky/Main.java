package ky;

import java.awt.event.KeyEvent;

public class Main extends KYscreen {
	private static final long serialVersionUID = 1L;


	
	public Main(int width, int height, boolean resizable, int FPScap) {
		super(width, height, resizable, FPScap);
	}

	public static void main(String[] args) {
		new Main(1000, 1000, false, 20);
	}

	CollisionEntity nyaentity;
	CollisionEntity test;
	CollisionEntity blank;
	double speed;
	Asset background;
	Asset nya;
	
	boolean jumped = false;
	
	@Override
	public void start() {
		
		speed = 1000;
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		nya = new Asset(group, new Vector2D(0, 0), 1, 0, "nya");
		nya.rescale(0.2);
		nya.setVisible(true);
		
		
		nyaentity = new CollisionEntity(200, 0, 200, 200, 2, false, "nya");
		nyaentity.addAsset(nya);
		nyaentity.setVisible(true);
		addEntity(nyaentity);
		
		test = new _TestCollisionEntity();
		test.addAsset(test.getAsset("test"));
		addEntity(test);
		
		
		// collision debugging
		test.setCollisionBoxVisibility(true);
		nyaentity.setCollisionBoxVisibility(true);
		
		// ===============
		
		background = new Asset("background.png", new Vector2D(0, 100), 0);
		background.setPos(getWidth()/2, getHeight()/2);
		//background.rescale(664 / background.getHeight());
		background.setVisible(true);
		addAsset(background);
	}
	
	boolean rescaled = false;
	
	@Override
	public void update(double deltaT) {
		
		nyaentity.setVel(0, 0);
		test.setVel(0, 0);
		
		if(getKeyStatus(KeyEvent.VK_W)) {
			nyaentity.addVel(0, -1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_S)) {
			nyaentity.addVel(0, 1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_A)) {
			nyaentity.addVel(-1 * speed, 0);
		}
		if(getKeyStatus(KeyEvent.VK_D)) {
			nyaentity.addVel(1 * speed, 0);
		}
		if(getKeyStatus(KeyEvent.VK_F)) {
			nyaentity.getAsset("nya").animate();
		}
		
		if(getKeyStatus(KeyEvent.VK_UP)) {
			test.addVel(0, -1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_DOWN)) {
			test.addVel(0, 1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_LEFT)) {
			test.addVel(-1 * speed, 0);
		}
		if(getKeyStatus(KeyEvent.VK_RIGHT)) {
			test.addVel(1 * speed, 0);
		}
		
		
		if(getKeyStatus(KeyEvent.VK_Q) && rescaled == false) {
			rescaled = true;
			background.rescale(2);
			test.getAsset("test").rescale(2);
			nyaentity.getAsset("nya").rescale(2);
		}
		if(!getKeyStatus(KeyEvent.VK_Q) && rescaled != false) {
			rescaled = false;
			background.rescale(0.5);
			test.getAsset("test").rescale(0.5);
			nyaentity.getAsset("nya").rescale(0.5);
		}
	}
}
