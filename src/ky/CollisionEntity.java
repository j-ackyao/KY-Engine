package ky;

import java.util.ArrayList;

public class CollisionEntity extends Entity {

	public static int collisionThreshold = 5;
	
	
	private CollisionBox collisionBox, xCollisionBox, yCollisionBox; // these positions are relative to the world and need to be updated
	
	private boolean collision = true; // if entity collides and is physically stopped (when disabled can be used for detection)
	private boolean staticCollision = false; // if entity is not affected by
	
	private boolean visibleCollisionBox = false;
	
	private ArrayList<CollisionEntity> collidingEntities = new ArrayList<CollisionEntity>();
	
	public CollisionEntity(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision) { // static collision determines if the entity is influenced when collided
		super(x, y, layer);
		this.staticCollision = staticCollision;
		this.collisionBox = new CollisionBox(x, y, collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(x, y + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(x + collisionThreshold, y, collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);
	}
	
	public CollisionEntity(Vector2D position, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision) {
		super(position, layer);
		this.staticCollision = staticCollision;
		this.collisionBox = new CollisionBox(position.getX(), position.getY(), collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(position.getX(), position.getY() + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(position.getX() + collisionThreshold, position.getY(), collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);
	}
	
	public CollisionEntity(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision, String name) {
		super(x, y, layer, name);
		this.staticCollision = staticCollision;
		this.collisionBox = new CollisionBox(x, y, collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(x, y + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(x + collisionThreshold, y, collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);	
	}
	
	public CollisionEntity(Vector2D position, int collisionBoxWidth, int collisionBoxHeight, int layer, boolean staticCollision, String name) {
		super(position, layer, name);
		this.staticCollision = staticCollision;
		this.collisionBox = new CollisionBox(position.getX(), position.getY(), collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(position.getX(), position.getY() + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(position.getX() + collisionThreshold, position.getY(), collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);
	}
	
	public boolean getCollision() {
		return this.collision;
	}
	
	public boolean getCollisionType() {
		return this.staticCollision;
	}
	
	public boolean getCollisionBoxVisibility() {
		return this.visibleCollisionBox;
	}
	
	public CollisionBox getCollisionBox() {
		return (CollisionBox) this.collisionBox.clone();
	}
	
	public CollisionBox getXCollisionBox() {
		return (CollisionBox) this.xCollisionBox.clone();
	}
	
	public CollisionBox getYCollisionBox() {
		return (CollisionBox) this.yCollisionBox.clone();
	}
	
	public void updateCollision(CollisionEntity[] collisionEntities, double deltaT) {
		collidingEntities.clear();

		this.collisionBox.setPos(getPos()); // current position before moved
		this.xCollisionBox.setPos(Vector2D.add(getPos(), Vector2D.multiply(new Vector2D(getVel().getX(), 0), deltaT))); // x collision box if moved
		this.yCollisionBox.setPos(Vector2D.add(getPos(), Vector2D.multiply(new Vector2D(0, getVel().getY()), deltaT))); // y collision box if moved
		
		for(CollisionEntity collidingEntity : collisionEntities) {
			if(collidingEntity != this) {

				CollisionBox collidingEntityXCollisionBox = collidingEntity.getXCollisionBox();
				CollisionBox collidingEntityYCollisionBox = collidingEntity.getYCollisionBox();
				//updatedCECollisionBox.setPos(Vector2D.add(collidingEntity.getPos(), Vector2D.multiply(collidingEntity.getVel(), deltaT)));
				
				if(!this.staticCollision) {

					if (collidingEntityXCollisionBox.intersects(this.xCollisionBox)) { // horizontal collision
						if (getX() < collidingEntity.getX()) { // this entity is to the left of collidingEntity
							setPos(collidingEntityXCollisionBox.getMinX() - this.collisionBox.getWidth() / 2, getY());
						} else {
							setPos(collidingEntityXCollisionBox.getMaxX() + this.collisionBox.getWidth() / 2, getY());
						}
						setVel(collidingEntity.getVel().getX(), getVel().getY());
						this.collidingEntities.add(collidingEntity);
					}

					else if (collidingEntityYCollisionBox.intersects(this.yCollisionBox)) { // vertical collision
						if (getY() < collidingEntity.getY()) { // this entity is above collidingEntity
							setPos(getX(), collidingEntityYCollisionBox.getMinY() - this.collisionBox.getHeight() / 2);
						} else {
							setPos(getX(), collidingEntityYCollisionBox.getMaxY() + this.collisionBox.getHeight() / 2);
						}
						setVel(getVel().getX(), collidingEntity.getVel().getY());
						this.collidingEntities.add(collidingEntity);
					}
				}
				
				else {

					if (collidingEntityXCollisionBox.intersects(this.xCollisionBox)) this.collidingEntities.add(collidingEntity); // horizontal collision
					
					else if (collidingEntityYCollisionBox.intersects(this.yCollisionBox)) this.collidingEntities.add(collidingEntity); // vertical collision
				}
			}
		}
		
		if(collidingEntities.size() > 0) {
			onCollision(collidingEntities.toArray(new CollisionEntity[collidingEntities.size()]));
		}
	}
	
	public void setCollisionBoxVisibility(boolean visible) {
		visibleCollisionBox = visible;
	}
	
	public void setCollision(boolean collision) {
		this.collision = false;
	}
	
	
	
	
	
	public void onCollision(CollisionEntity[] collidingEntities) {
		
	}
	
	@Override
	public void update(double deltaT, ArrayList<Integer> keyCodes) {

	}
	
}
