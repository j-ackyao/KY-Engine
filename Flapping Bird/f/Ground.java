package f;

import ky.Asset;
import ky.CollisionEntity;
import ky.Vector2D;

public class Ground extends CollisionEntity {

	
	final private static Vector2D position = new Vector2D(400, 650);
	final private static int width = 800;
	final private static int height = 50;
	final private static int layer = 1;
	
	public Ground() {
		super(position, width, height, layer, "ground");
		
		Asset grass = new Asset("Assets/grass.png", new Vector2D(-400, -30), 0);
		grass.setVisible(true);
		grass.rescale(0.3);
		
		for(int i = 0; i < 40; i++) {
			Asset clone = grass.clone();
			clone.setPos(grass.getX() + i * (grass.getWidth() - 5), grass.getY());
			add(clone);
		}
		
		setVisible(true);
	}
	
	
	public void move(double deltaT) {
		for(Asset[] layer : getAssetLayers()) {
			for(Asset a : layer) {
				a.setPos(a.getX() - Main.speed * deltaT, a.getY());
				if(a.getX() < -450) {
					a.setPos(getCollisionBox().getWidth(), a.getY());
				}
			}
		}
	}
}
