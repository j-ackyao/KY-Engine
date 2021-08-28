package p;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import ky.AnimationAsset;
import ky.Asset;
import ky.CollisionEntity;
import ky.Entity;
import ky.KYscreen;
import ky.Text;
import ky.Vector2D;

public class Main extends KYscreen {
	private static final long serialVersionUID = 1L;


	
	public Main(int width, int height, boolean resizable, int FPScap) {
		super(width, height, resizable, FPScap);
	}

	public static void main(String[] args) {
		new Main(1600, 900, false, 120);
	}

	CollisionEntity nyaentity;
	Player player;
	CollisionEntity ground;
	
	Asset background;
	Asset background2;
	Asset background3;
	Asset background4;
	AnimationAsset nya;
	
	double startTime;
	Text timer;
	boolean time;
	Text winText;
	Text title;
	
	
	@Override
	public void start() {
		
		String[] group = {"SuperToastBrosAssets/nya.png","SuperToastBrosAssets/ichi.png","SuperToastBrosAssets/ni.png","SuperToastBrosAssets/san.png", "SuperToastBrosAssets/nya.png", "SuperToastBrosAssets/arigatou.png"};
		nya = new AnimationAsset(group, new Vector2D(0, 0), 1, 0, "nya");
		nya.rescale(0.28);
		nya.setVisible(true);
		
		
		nyaentity = new CollisionEntity(200, 0, 200, 200, 2, "nya");
		nyaentity.setStaticCollision(true);
		nyaentity.add(nya);
		nyaentity.setVisible(true);
		add(nyaentity);
		
		
		ground = new Ground(3500, 1000, 10000, 256, 1, "ground");
		add(ground);
		

		CollisionEntity wall1 = new Wall(-770, 1000-256-128, 256, 512, 1);
		add(wall1);
		
		CollisionEntity wall2 = new Wall(1000, 1000-256, 256*4, 256, 1);
		add(wall2);
		
		CollisionEntity wall3 = new Wall(2400, 1000-512, 256, 256, 1);
		add(wall3);
		
		CollisionEntity wall4 = new Wall(3700, 1000-512, 256, 256, 1);
		add(wall4);
		
		CollisionEntity wall5 = new Wall(4800-256-128, 1000-256, 256, 256, 1);
		add(wall5);
		
		CollisionEntity wall6 = new Wall(4800+256+128, 1000-256-128, 256, 512, 1);
		add(wall6);
		
		CollisionEntity wall7 = new Wall(4800-256-128-80, 1000-512-256, 256, 256, 1);
		add(wall7);
		
		CollisionEntity spike1 = new Spike(1350, 552, 256, 128, 1);
		add(spike1);
		
		CollisionEntity spike2 = new Spike(3050, 552+256, 512, 128, 1);
		add(spike2);
		
		CollisionEntity spike3 = new Spike(4800, 552+256, 512, 128, 1);
		add(spike3);
		
		CollisionEntity win = new Win(4800-256-128-80, 1000-512-256-256, 128, 128, 1);
		add(win);
		
		startTime = System.currentTimeMillis();
		time = true;
		
		timer = new Text("", new Font("TimesRoman", Font.BOLD, 18), Color.RED, new Vector2D(0, 200), 100, 100, 4);
		timer.setVisible(true);
		add(timer);
		
		winText = new Text("You win!!!", new Font("Comic Sans MS", Font.BOLD, 50), Color.GREEN, new Vector2D(0, 0), 250, 100, 4);
		add(winText);
		
		title = new Text("Super Toast Bros", new Font("Comic Sans MS", Font.BOLD, 100), Color.ORANGE, new Vector2D(0, 600), 900, 200, 4);
		title.setVisible(true);
		add(title);
		
		player = new Player();
		add(player);
		
		
		
		background = new Asset("SuperToastBrosAssets/background.png", new Vector2D(0, 100), 0);
		background.setPos(400, 500);
		background.rescale(2);
		background.setVisible(true);
		add(background);
		background2 = background.clone();
		background2.setPos(background.getPos().getX() + (double) background.getWidth()/2, background.getPos().getY());
		add(background2);
		background3 = background.clone();
		background3.setPos(background.getPos().getX() + (double) background.getWidth(), background.getPos().getY());
		add(background3);
		background4 = background.clone();
		background4.setPos(background.getPos().getX() + (double) background.getWidth() * (double) 3/2, background.getPos().getY());
		add(background4);
		
	}
	
	boolean rescaled = false;
	boolean box = false;
	
	@Override
	public void update() {

		
		/*
		nyaentity.setVel(0, 0);
		
		if(getKeyStatus(KeyEvent.VK_UP)) {
			nyaentity.addVel(0, -1 * 1000);
		}
		if(getKeyStatus(KeyEvent.VK_DOWN)) {
			nyaentity.addVel(0, 1 * 1000);
		}
		if(getKeyStatus(KeyEvent.VK_LEFT)) {
			nyaentity.addVel(-1 * 1000, 0);
		}
		if(getKeyStatus(KeyEvent.VK_RIGHT)) {
			nyaentity.addVel(1 * 1000, 0);
		}*/
		if(getKeyStatus(KeyEvent.VK_NUMPAD0)) {
			nya.animate();
		}
		
		if(getKeyStatus(KeyEvent.VK_Q) && rescaled == false) {
			rescaled = true;
			Entity[][] a = getEntityLayers();
			for(Entity[] b : a) {
				for(Entity c : b) {
					for(Asset[] d : c.getAssetLayers()) {
						for(Asset e : d) {
							e.rescale(2);
						}
					}
				}
			}
		}
		if(!getKeyStatus(KeyEvent.VK_Q) && rescaled != false) {
			rescaled = false;
			Entity[][] a = getEntityLayers();
			for(Entity[] b : a) {
				for(Entity c : b) {
					for(Asset[] d : c.getAssetLayers()) {
						for(Asset e : d) {
							e.rescale(0.5);
						}
					}
				}
			}
		}

		if(getKeyStatus(KeyEvent.VK_F) && box == false) {
			box = true;
			setDebugMode(!getDebugMode());
		}
		if(!getKeyStatus(KeyEvent.VK_F) && box != false) {
			box = false;
		}
		
		
		//System.out.println(nyaentity.getCollisionBox().getCenterX() + ", " + nyaentity.getCollisionBox().getCenterY());
		
		setCameraPos(player.getX() - (double) getWidth()/2, player.getY() - (double) getHeight()/2 - 100d);


		if(player.win) {
			winText.setVisible(true);
			winText.setPos(getCameraPos().getX() + (double) getWidth()/2, getCameraPos().getY() + (double) getHeight()/2);
			time = false;
		}
		if(time) {
			timer.setText(Double.toString((System.currentTimeMillis() - startTime)/1000d));
		}
		timer.setPos(getCameraPos().getX() + (double) timer.getWidth(), getCameraPos().getY() + (double) timer.getHeight());
		
		
		
	}

	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(int keyCode) {
		// TODO Auto-generated method stub
		
	}
}
