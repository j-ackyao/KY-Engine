package ky;

import java.awt.event.KeyEvent;

public class Main extends KYscreen {
	private static final long serialVersionUID = 1L;


	
	public Main(int width, int height, boolean resizable, int FPScap) {
		super(width, height, resizable, FPScap);
	}

	public static void main(String[] args) {
		
		Vector2D vector = new Vector2D(1, 1);
		vector.add(new Vector2D(0,-2));
		System.out.println(vector.toString());
		System.out.println(vector.getMagnitude());
		System.out.println(vector.getAngle());
		
		
		new Main(720, 664, true, 60);
	}

	public Entity nyaentity;
	public Vector2D velocity;
	public double speed;
	Asset background;
	Asset nya;
	
	boolean jumped = false;
	
	@Override
	public void start() {

		velocity = new Vector2D(0, 0);
		speed = 200;
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		nya = new Asset(group, new Vector2D(0, 0), 1, 0, "nya");
		nya.rescale(0.2);
		nya.setPos((double) -nya.getWidth()/2, (double) -nya.getHeight()/2);
		nya.setVisible(true);
		
		nyaentity = new Entity(100, 100, 1);
		nyaentity.addAsset(nya);
		nyaentity.setVisible(true);
		addEntity(nyaentity);
		
		background = new Asset("background.png", new Vector2D(0, 0), 0);
		background.rescale(664 / background.getHeight());
		background.setVisible(true);
		addAsset(background);
	}
	
	
	@Override
	public void update() {

		
		
		velocity.set(0, velocity.getY());
		
		velocity.add(0, 20);
		
		if(nyaentity.getY() > 300) {
			velocity.set(0, 0);
		}
		
		if(getKeyStatus(KeyEvent.VK_W)) {
			velocity.add(0, -1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_S)) {
			velocity.add(0, 1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_A)) {
			velocity.add(-1 * speed, 0);
		}
		if(getKeyStatus(KeyEvent.VK_D)) {
			velocity.add(1 * speed, 0);
		}
		if(getKeyStatus(KeyEvent.VK_F)) {
			nyaentity.getAsset("nya").animate();
		}
		if(getKeyStatus(KeyEvent.VK_Q)) {
			nyaentity.setVisible(!nyaentity.isVisible());
		}

		if(getKeyStatus(KeyEvent.VK_SPACE) && !jumped) {
			System.out.println("jumped");
			jumped = true;
			velocity.set(velocity.getX(), -500);
		}
		else if(!getKeyStatus(KeyEvent.VK_SPACE) && jumped) {
			System.out.println("no jump");
			jumped = false;
			
		}
		
		/*
		if(getKeyStatus(KeyEvent.VK_LEFT)) {
			setCameraPos(getCameraPos().getX() - 10, 0);
		}
		
		if(getKeyStatus(KeyEvent.VK_RIGHT)) {
			setCameraPos(getCameraPos().getX() + 10, 0);
		}
		*/
		
		if(getKeyStatus(KeyEvent.VK_Q)) {
			nyaentity.setVisible(!nyaentity.isVisible());
		}
		
		nyaentity.addPos(Vector2D.multiply(velocity, deltaT()));
		System.out.println(1/deltaT());
		
		setCameraPos(Vector2D.subtract(nyaentity.getPos(), new Vector2D(getWidth()/2, getHeight()/2)));
		
		//System.out.println(nyaentity.getPos().toString());
		//System.out.println(velocity.getX() + ", " + velocity.getY());
	}
}
