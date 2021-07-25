package ky;

public class BoxCollider extends Entity {

	public BoxCollider(double x, double y, int layer) {
		super(x, y, layer);
	}
	
	@Override
	public void update(double deltaTime) {
		System.out.println("Wow we are doing collision stuff");
	}
	
}
