package p;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Win extends CollisionEntity {
	public Win(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision) {
		super(x, y, collisionBoxWidth, collisionBoxHeight, layer, staticCollision, "win");
		
		Asset crown = new Asset("crown.png", new Vector2D(0, 0), 0, "crown");
		crown.setVisible(true);
		addAsset(crown);
		
		
		setCollision(false);
		setVisible(true);
		
	}
}
