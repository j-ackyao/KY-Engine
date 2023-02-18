package f;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ky.AnimationAsset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Player extends CollisionEntity {

	final private double gravity = 1000;
	final private double jump = -300;
	public boolean alive = true;
	public boolean start = false;
	private boolean jumped = false;
	
	final private static Vector2D position = new Vector2D(150, 400);
	final private static int width = 80;
	final private static int height = 60;
	final private static int layer = 2;
	
	int score = 0;
	boolean scored = false;
	
	AnimationAsset bird;
	
	public Player() {
		super(position.clone(), width, height, layer, "player");
		setCollision(true);
		
		bird = new AnimationAsset(new String[]{"Assets/bird1.png", "Assets/bird.png"}, new Vector2D(-7, 10), 1, 0);
		bird.setVisible(true);
		add(bird);
		
		setVisible(true);
	}
	
	public void start() {
		alive = true;
		start = true;
	}
	
	public void stop() {
		alive = false;
	}
	
	public void reset() {
		alive = true;
		start = false;
		setPos(position.clone());
		bird.setRotation(0);
		score = 0;
	}
	
	@Override
	public void onCollision(CollisionEntity ce) {
		if (ce.getName().equals("ground") || ce.getName().equals("wall")) {
			stop();
		}
		if (ce.getName().equals("score") && !scored) {
			score++;
			scored = true;
		}
		if (ce.getName().equals("reseter")) {
			scored = false;
		}
	}

	@Override
	public void update(double deltaT, ArrayList<Integer> keyCodes) {
		if(alive && start) {
			addVel(0, gravity * deltaT);
			
			
			if(keyCodes.contains(KeyEvent.VK_SPACE) && !jumped) {
				bird.setImageIndex(1);
				setVel(0, jump);
				jumped = true;
			}
			else if(!keyCodes.contains(KeyEvent.VK_SPACE)) {
				bird.setImageIndex(0);
				jumped = false;
			}
			bird.setRotation(getVel().getY()/50000);
		}
		else if(!alive && start){
			setVel(0, 0);
		}
		else if(!start){
			bird.animate();
		}
	}

}
