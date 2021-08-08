package ky;

public class _TestCollisionEntity extends CollisionEntity {
	

	double speed = 500;
	
	_TestCollisionEntity(){
		super(0, 0, 200, 200, 1, true, "testEntity");
		Asset testAsset = new Asset("test.png", new Vector2D(0, 0), 0, "test");
		testAsset.rescale(0.25);
		testAsset.setVisible(true);
		addAsset(testAsset);
		setVisible(true);
	}
	
	
	
	

	@Override
	public void onCollision(CollisionEntity[] collidingEntities) {
		System.out.println("Collision!");
	}
	
	
	@Override
	public void update(double deltaT) {

	}
}
