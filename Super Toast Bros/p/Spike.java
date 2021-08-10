package p;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Spike extends CollisionEntity {

	public Spike(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision) {
		super(x, y, collisionBoxWidth, collisionBoxHeight, layer, staticCollision, "spike");
		
		Asset spike = new Asset("spike.png", new Vector2D(0, 0), 0, "spike");
		spike.setVisible(true);
		
		
		for(int i = 0; i < Math.ceil((double)collisionBoxWidth/256); i++) {

			for(int u = 0; u < Math.ceil((double)collisionBoxHeight/128); u++) {
				Asset clone = spike.clone();
				clone.setPos(i * 256 - collisionBoxWidth/2 + 128, -u * 256 + collisionBoxHeight/2 - 128);
				addAsset(clone);
			}
		}
		
		setCollision(false);
		setVisible(true);
		
	}
}
