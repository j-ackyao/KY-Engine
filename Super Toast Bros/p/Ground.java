package p;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Ground extends CollisionEntity {

	public Ground(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision, String name) {
		super(x, y, collisionBoxWidth, collisionBoxHeight, layer, staticCollision, name);
		
		Asset wall = new Asset("wall.png", new Vector2D(0, 0), 0, "wall");
		wall.setVisible(true);
		
		for(int i = -10; i < 50; i++) {
			Asset clone = wall.clone();
			clone.setPos(i * wall.getWidth(), wall.getY());
			addAsset(clone);
		}

		setVisible(true);
		
	}

}
