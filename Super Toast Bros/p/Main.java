package p;

import java.awt.event.KeyEvent;

import ky.Asset;
import ky.CollisionEntity;
import ky.KYscreen;
import ky.Vector2D;

public class Main extends KYscreen {
	private static final long serialVersionUID = 1L;


	
	public Main(int width, int height, boolean resizable, int FPScap) {
		super(width, height, resizable, FPScap);
	}

	public static void main(String[] args) {
		new Main(1000, 1000, false, 120);
	}

	CollisionEntity nyaentity;
	CollisionEntity player;
	CollisionEntity ground;
	double speed;
	Asset background;
	Asset nya;
	
	@Override
	public void start() {
		
		speed = 500;
		
		String[] group = {"nya.png","ichi.png","ni.png","san.png", "nya.png", "arigatou.png"};
		nya = new Asset(group, new Vector2D(0, 0), 1, 0, "nya");
		nya.rescale(0.2);
		nya.setVisible(true);
		
		
		nyaentity = new CollisionEntity(200, 0, 200, 200, 2, true, "nya");
		nyaentity.addAsset(nya);
		nyaentity.setVisible(true);
		addEntity(nyaentity);
		
		
		ground = new Ground(500, 1000, 10000, 256, 2, true, "ground");
		addEntity(ground);
		
		
		player = new Player();
		addEntity(player);
		
		
		// collision debugging
		player.setCollisionBoxVisibility(true);
		nyaentity.setCollisionBoxVisibility(true);
		ground.setCollisionBoxVisibility(true);
		
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
		
		if(getKeyStatus(KeyEvent.VK_UP)) {
			nyaentity.addVel(0, -1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_DOWN)) {
			nyaentity.addVel(0, 1 * speed);
		}
		if(getKeyStatus(KeyEvent.VK_LEFT)) {
			nyaentity.addVel(-1 * speed, 0);
		}
		if(getKeyStatus(KeyEvent.VK_RIGHT)) {
			nyaentity.addVel(1 * speed, 0);
		}
		if(getKeyStatus(KeyEvent.VK_NUMPAD0)) {
			nyaentity.getAsset("nya").animate();
		}
		
		if(getKeyStatus(KeyEvent.VK_Q) && rescaled == false) {
			rescaled = true;
			background.rescale(2);
			player.getAsset("bread").rescale(2);
			nyaentity.getAsset("nya").rescale(2);
		}
		if(!getKeyStatus(KeyEvent.VK_Q) && rescaled != false) {
			rescaled = false;
			background.rescale(0.5);
			player.getAsset("bread").rescale(0.5);
			nyaentity.getAsset("nya").rescale(0.5);
		}
		
		
		
		
		setCameraPos(player.getX() - (double) getWidth()/2, player.getY() - (double) getHeight()/2);
		
		
		
	}
}
