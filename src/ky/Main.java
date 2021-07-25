package ky;

import java.awt.event.KeyEvent;

public class Main extends KYscreen {
	private static final long serialVersionUID = 1L;


	
	public Main(int width, int height, boolean resizable, int FPScap) {
		super(width, height, resizable, FPScap);
	}

	public static void main(String[] args) {
		new Main(1000,1000, false, 60);
	}

	Entity nyaentity;
	Entity test;
	double speed;
	Asset background;
	Asset nya;
	
	boolean jumped = false;
	
	@Override
	public void start() {

		speed = 500;
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		nya = new Asset(group, new Vector2D(0, 0), 1, 0, "nya");
		nya.rescale(0.2);
		nya.setVisible(true);
		
		nyaentity = new Entity(500, 500, 2, "nya");
		nyaentity.addAsset(nya);
		nyaentity.setVisible(true);
		addEntity(nyaentity);
		
		test = new Entity(0, 0, 1, "nya");
		Asset testAsset = new Asset("test.png", new Vector2D(0, 0), 0, "test");
		testAsset.rescale(0.25);
		testAsset.setVisible(true);
		test.addAsset(testAsset);
		test.setVisible(true);
		addEntity(test);
		
		background = new Asset("background.png", new Vector2D(0, 100), 0);
		background.setPos(getWidth()/2, getHeight()/2);
		//background.rescale(664 / background.getHeight());
		background.setVisible(true);
		addAsset(background);
		
	}
	
	boolean rescaled = false;
	
	@Override
	public void update() {
		
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
		}
		if(!getKeyStatus(KeyEvent.VK_Q) && rescaled != false) {
			rescaled = false;
			background.rescale(0.5);
			test.getAsset("test").rescale(0.5);
		}
		
		/*
		if(getKeyStatus(KeyEvent.VK_LEFT)) {
			setCameraPos(getCameraPos().getX() - 10, 0);
		}
		
		if(getKeyStatus(KeyEvent.VK_RIGHT)) {
			setCameraPos(getCameraPos().getX() + 10, 0);
		}
		*/
		
		//setCameraPos(Vector2D.subtract(nyaentity.getPos(), new Vector2D(getWidth()/2, getHeight()/2)));
		
		//System.out.println(nyaentity.velocity.toString());
		//System.out.println(velocity.getX() + ", " + velocity.getY());
	}
}
