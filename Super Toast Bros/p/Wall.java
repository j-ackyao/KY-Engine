package p;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Wall extends CollisionEntity {

	public Wall(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer) {
		super(x, y, collisionBoxWidth, collisionBoxHeight, layer, "wall");
		
		Asset wall = new Asset("Assets/wall.png", new Vector2D(0, 0), 0, "wall");
		wall.setVisible(true);
		
		
		for(int i = 0; i < Math.ceil((double)collisionBoxWidth/256); i++) {

			for(int u = 0; u < Math.ceil((double)collisionBoxHeight/256); u++) {
				Asset clone = wall.clone();
				clone.setPos(i * 256 - collisionBoxWidth/2 + 128, -u * 256 + collisionBoxHeight/2 - 128);
				add(clone);
			}
		}

		setStaticCollision(true);
		setCollision(true);
		setVisible(true);
		
	}

}
