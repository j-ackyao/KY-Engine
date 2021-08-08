package ky;

public class Vector2D {
	
	public static Vector2D add(Vector2D a, Vector2D b) {
		return new Vector2D(a.getX() + b.getX(), a.getY() + b.getY());
	}
	
	public static Vector2D subtract(Vector2D a, Vector2D b) {
		return new Vector2D(a.getX() - b.getX(), a.getY() - b.getY());
	}
	
	public static Vector2D multiply(Vector2D v, double factor) {
		Vector2D newVector = new Vector2D(v.getX(), v.getY());
		newVector.multiply(factor);
		return newVector;
	}
	
	public static double multiply(Vector2D a, Vector2D b) { // dot multiplication
		return a.getX() * b.getX() + a.getY() * b.getY();
	}
	
	public static Vector2D invert(Vector2D v) {
		return new Vector2D(v.getX() * -1, v.getY() * -1);
	}
	
	private double x;
	private double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D clone() {
		return new Vector2D(this.x, this.y);
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}
	
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public double getAngle() {
		double angle = Math.abs(Math.atan(this.y/this.x)) * 180d / Math.PI;
		
		if(this.x > 0 && this.y <= 0) { // quad 1
			return angle;
		}
		else if(this.x < 0 && this.y <= 0) { // quad 2
			return 180d - angle;
		}
		else if(this.x <= 0 && this.y >= 0) { // quad 3
			return 180d + angle;
		}
		else if(this.x > 0 && this.y > 0) { // quad 4
			return 360d - angle;
		}
		
		return angle;
	}
	
	public void add(Vector2D addend) {
		this.x += addend.getX();
		this.y += addend.getY();
	}
	
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void subtract(Vector2D addend) {
		this.x -= addend.getX();
		this.y -= addend.getY();
	}
	
	public void subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
	}
	
	public void multiply(double factor) {
		this.x *= factor;
		this.y *= factor;
	}
	
	public void invert() {
		this.x = this.x * -1;
		this.y = this.y * -1;
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
