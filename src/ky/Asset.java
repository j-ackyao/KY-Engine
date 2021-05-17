package ky;

import java.awt.Image;

public class Asset {
	
	public int x;
	public int y;
	public Image image;
	public String identifier = "";
	
	public Asset(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public Asset(Image image, int x, int y, String identifier) {
		this.identifier = identifier;
		this.image = image;
		this.x = x;
		this.y = y;
	}
}
