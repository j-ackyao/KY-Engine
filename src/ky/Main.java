package ky;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Main extends Screen {
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
	
	@Override
	public void start() {

		velocity = new Vector2D(0, 0);
		speed = 0.5;
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		nya = new Asset(group, 0, 0, 1, 0, "nya");
		nya.rescale(0.2);
		nya.setVisible(true);
		
		nyaentity = new Entity(100, 100, 1);
		nyaentity.addAsset(nya);
		nyaentity.setVisible(true);
		addEntity(nyaentity);
		
		background = new Asset("background.png", 0, 0, 0);
		background.rescale(664 / background.getHeight());
		background.setVisible(true);
		addAsset(background);
	}
	
	
	@Override
	public void update() {
		
		velocity.set(0, 0);
		
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
		nyaentity.addPos(Vector2D.multiply(velocity, deltaT()));
		System.out.println(Arrays.toString(nyaentity.getPos()));
	}
}
