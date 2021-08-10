package p;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Ground extends CollisionEntity {

	public Ground(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision, String name) {
		super(x, y, collisionBoxWidth, collisionBoxHeight, layer, staticCollision, name);
		
		Asset wall = new Asset("grass.png", new Vector2D(0, 0), 0, "grass");
		wall.setVisible(true);
		
		for(int i = -20; i < 50; i++) {
			Asset clone = wall.clone();
			clone.setPos(i * wall.getWidth(), wall.getY());
			addAsset(clone);
		}

		setVisible(true);
		
	}

}
