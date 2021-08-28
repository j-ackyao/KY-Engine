package ky;

public class Ray2D extends Vector2D {

	private Vector2D position;
	
	public Ray2D(double ox, double oy, double x, double y) {
		super(x, y);
		this.position = new Vector2D(ox, oy);
	}
	
	public Ray2D(Vector2D position, double x, double y) {
		super(x, y);
		this.position = position;
	}
	
	public void setPos(double x, double y) {
		this.position.set(x, y);
	}
	
	public void setPos(Vector2D position) {
		this.position = position;
	}
	
	public Vector2D getPos() {
		return this.position.clone();
	}
	
	public double getXPos() {
		return this.position.clone().getX();
	}
	
	public double getYPos() {
		return this.position.clone().getY();
	}
	

}
