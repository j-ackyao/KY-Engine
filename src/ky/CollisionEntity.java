package ky;

import java.util.ArrayList;

public class CollisionEntity extends Entity {

	public static int collisionThreshold = 5;
	
	
	private CollisionBox collisionBox, xCollisionBox, yCollisionBox; // these positions are relative to the world and need to be updated
	
	private boolean collision = false; // if entity collides and is physically stopped (when disabled can be used for detection)
	private boolean staticCollision = false; // if entity is not affected by collisions
	
	private boolean visibleCollisionBox = false;
	
	private ArrayList<CollisionEntity> collidingEntities = new ArrayList<CollisionEntity>();
	
	public CollisionEntity(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer) { // static collision determines if the entity is influenced when collided
		super(x, y, layer);
		this.collisionBox = new CollisionBox(x, y, collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(x, y + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(x + collisionThreshold, y, collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);
	}
	
	public CollisionEntity(Vector2D position, int collisionBoxWidth, int collisionBoxHeight, int layer) {
		super(position, layer);
		this.collisionBox = new CollisionBox(position.getX(), position.getY(), collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(position.getX(), position.getY() + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(position.getX() + collisionThreshold, position.getY(), collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);
	}
	
	public CollisionEntity(double x, double y, int collisionBoxWidth, int collisionBoxHeight, int layer, String name) {
		super(x, y, layer, name);
		this.collisionBox = new CollisionBox(x, y, collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(x, y + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(x + collisionThreshold, y, collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);	
	}
	
	public CollisionEntity(Vector2D position, int collisionBoxWidth, int collisionBoxHeight, int layer, String name) {
		super(position, layer, name);
		this.collisionBox = new CollisionBox(position.getX(), position.getY(), collisionBoxWidth, collisionBoxHeight);
		this.xCollisionBox = new CollisionBox(position.getX(), position.getY() + collisionThreshold, collisionBoxWidth, collisionBoxHeight - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(position.getX() + collisionThreshold, position.getY(), collisionBoxWidth - 2 * collisionThreshold, collisionBoxHeight);
	}
	
	public boolean getCollision() {
		return this.collision;
	}
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	
	public boolean getStaticCollision() {
		return this.staticCollision;
	}
	public void setStaticCollision(boolean staticCollision) {
		this.staticCollision = staticCollision;
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
	
	public void setCollisionBoxDimensions(int width, int height) {
		this.collisionBox = new CollisionBox(getPos().getX(), getPos().getY(), width, height);
		this.xCollisionBox = new CollisionBox(getPos().getX(), getPos().getY() + collisionThreshold, width, height - 2 * collisionThreshold);
		this.yCollisionBox = new CollisionBox(getPos().getX() + collisionThreshold, getPos().getY(), width - 2 * collisionThreshold, height);
	}
	
	public void updateCollision(CollisionEntity[] collisionEntities, double deltaT) {
		collidingEntities.clear();

		this.collisionBox.setPos(getPos()); // current position before moved
		this.xCollisionBox.setPos(Vector2D.add(getPos(), Vector2D.multiply(new Vector2D(getVel().getX(), 0), deltaT))); // x collision box if moved
		this.yCollisionBox.setPos(Vector2D.add(getPos(), Vector2D.multiply(new Vector2D(0, getVel().getY()), deltaT))); // y collision box if moved
		
		for(CollisionEntity collidingEntity : collisionEntities) {
			if(collidingEntity != this && collidingEntity.isVisible()) {

				CollisionBox collidingEntityXCollisionBox = collidingEntity.getXCollisionBox();
				CollisionBox collidingEntityYCollisionBox = collidingEntity.getYCollisionBox();
				//updatedCECollisionBox.setPos(Vector2D.add(collidingEntity.getPos(), Vector2D.multiply(collidingEntity.getVel(), deltaT)));
				
				if(!this.staticCollision && collidingEntity.getCollision()) {

					if (collidingEntityXCollisionBox.intersects(this.xCollisionBox)) { // horizontal collision
						this.collidingEntities.add(collidingEntity);
						if(getCollision()) {
							if (getX() < collidingEntity.getX()) { // this entity is to the left of collidingEntity
								setPos(collidingEntityXCollisionBox.getMinX() - this.collisionBox.getWidth() / 2, getY());
							} else {
								setPos(collidingEntityXCollisionBox.getMaxX() + this.collisionBox.getWidth() / 2, getY());
							}
							//setVel(collidingEntity.getVel().getX(), getVel().getY());
							setVel(0, getVel().getY());
						}
						onCollision(collidingEntity);
					}

					else if (collidingEntityYCollisionBox.intersects(this.yCollisionBox)) { // vertical collision
						this.collidingEntities.add(collidingEntity);
						if(getCollision()) {
							if (getY() < collidingEntity.getY()) { // this entity is above collidingEntity
								setPos(getX(), collidingEntityYCollisionBox.getMinY() - this.collisionBox.getHeight() / 2);
							} else {
								setPos(getX(), collidingEntityYCollisionBox.getMaxY() + this.collisionBox.getHeight() / 2);
							}
							//setVel(getVel().getX(), collidingEntity.getVel().getY());
							setVel(getVel().getX(), 0);
						}
						onCollision(collidingEntity);
					}
				}
				
				else {

					if (collidingEntityXCollisionBox.intersects(this.xCollisionBox)) {
						this.collidingEntities.add(collidingEntity); // horizontal collision
						onCollision(collidingEntity);
					}
					
					else if (collidingEntityYCollisionBox.intersects(this.yCollisionBox)) {
						this.collidingEntities.add(collidingEntity); // vertical collision
						onCollision(collidingEntity);
					}
				}
			}
		}
		
	}
	
	public void setCollisionBoxVisibility(boolean visible) {
		visibleCollisionBox = visible;
	}
	
	public CollisionEntity clone() {
		CollisionEntity clone = new CollisionEntity(getPos().clone(), (int) getCollisionBox().getWidth(), (int) getCollisionBox().getHeight(), getLayer(), getName());
		for(Asset[] layers : getAssetLayers()) {
			for(Asset a : layers) {
				clone.add(a.clone());
			}
		}
		clone.setVel(getVel());
		clone.setVisible(isVisible());
		clone.setCollision(getCollision());
		clone.setStaticCollision(getStaticCollision());
		clone.setCollisionBoxVisibility(getCollisionBoxVisibility());
		return clone;
	}
	
	
	public void onCollision(CollisionEntity collidingEntity) {
		
	}
	
	@Override
	public void update(double deltaT, ArrayList<Integer> keyCodes) {

	}
	
}
