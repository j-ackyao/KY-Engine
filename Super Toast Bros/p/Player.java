package p;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Player extends CollisionEntity {
	
	Asset bread;
	
	double accel = 10000;
	double deccel = 50;
	double maxSpeed = 1000;
	double deadZone = 50;
	double gravity = 2500;
	double jump = 1250;
	boolean jumped = false;
	
	public boolean win = false;
	
	
	Player(){
		super(0, 0, 160, 215, 1, "player");
		bread = new Asset(new String[]{"SuperToastBrosAssets/bread.png", "SuperToastBrosAssets/bread1.png"}, new Vector2D(0, -20), 0, "bread");
		bread.rescale(1);
		bread.setVisible(true);
		add(bread);
		setCollision(true);
		setVisible(true);
	}
	
	
	
	

	@Override
	public void onCollision(CollisionEntity collidingEntity) {
		if (collidingEntity.getName() == "ground" || collidingEntity.getName() == "nya"
				|| collidingEntity.getName() == "wall") {
			if (getCollisionBox().getMaxY() <= collidingEntity.getCollisionBox().getMinY()) {
				jumped = false;
			}
		}
		if (collidingEntity.getName() == "spike") {
			System.exit(0);
		}
		if (collidingEntity.getName() == "win") {
			win = true;
		}
	}
	
	
	@Override
	public void update(double deltaT, ArrayList<Integer> keyCodes) {
		
		addVel(0, gravity * deltaT);
		
		
		if(keyCodes.contains(KeyEvent.VK_W) && !jumped) {
			addVel(0, -1 * jump);
			jumped = true;
		}
		
		if(keyCodes.contains(KeyEvent.VK_A)) {

			bread.setImageIndex(0);
			addVel(-1 * accel * deltaT, 0);
		}
		else if(keyCodes.contains(KeyEvent.VK_D)) {

			bread.setImageIndex(1);
			addVel(1 * accel * deltaT, 0);
		}
		else if(keyCodes.contains(KeyEvent.VK_S)) {
			
		}
		else {
			setVel(getVel().getX() * deccel * deltaT, getVel().getY());
		}
		
		if(Math.abs(getVel().getX()) <= deadZone) {
			setVel(0, getVel().getY());
		}
		else if(Math.abs(getVel().getX()) > maxSpeed) { // again, resorting to more ifs
			if(getVel().getX() < 0) {
				setVel(-maxSpeed, getVel().getY());
			}
			else {
				setVel(maxSpeed, getVel().getY());
			}
		}
		
		if(win) {
			bread.rotate(0.0005 * deltaT * getVel().getX());
		}
	}
}
