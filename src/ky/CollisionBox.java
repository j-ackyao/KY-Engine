package ky;

import java.awt.Rectangle;

public class CollisionBox extends Rectangle {
	private static final long serialVersionUID = 1L;


	CollisionBox(double x, double y, int width, int height){
		super((int) Math.round(x - (double) width/2), (int) Math.round(y - (double) height/2), width, height);
	}
	
	
	public void setPos(double x, double y) {
		this.x = (int) Math.round(x - (double) this.width/2);
		this.y = (int) Math.round(y - (double) this.height/2);
	}
	
	public void setPos(Vector2D position) {
		this.x = (int) Math.round(position.getX() - (double) this.width/2);
		this.y = (int) Math.round(position.getY() - (double) this.height/2);
	}
}
