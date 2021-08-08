package p;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Player extends CollisionEntity {
	

	double accel = 10000;
	double deccel = 5000;
	double maxSpeed = 1000;
	double deadZone = 50;
	double gravity = 2500;
	double jump = 1000;
	boolean jumped = false;
	
	Player(){
		super(0, 0, 160, 215, 1, false, "player");
		Asset bread = new Asset("bread.png", new Vector2D(0, -20), 0, "bread");
		bread.rescale(1);
		bread.setVisible(true);
		addAsset(bread);
		setVisible(true);
	}
	
	
	
	

	@Override
	public void onCollision(CollisionEntity[] collidingEntities) {
		for(CollisionEntity collidingEntity : collidingEntities) {
			if (collidingEntity.getName() == "ground") {
				jumped = false;
			}
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
			addVel(-1 * accel * deltaT, 0);
		}
		else if(keyCodes.contains(KeyEvent.VK_D)) {
			addVel(1 * accel * deltaT, 0);
		}
		else { //Math cos does not work properly, so resorting to more ifs
			if(getVel().getX() < 0) {
				addVel(deccel * deltaT, 0);
			}
			else {
				addVel(-deccel * deltaT, 0);
			}
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
	}
}
