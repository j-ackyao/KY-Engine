package ky;

import java.awt.Image;

public class Asset {
	
	int x;
	int y;
	Image image = null;
	Image[] images = null;
	String identifier = null;
	boolean animated = false;
	int animationCycle = -1;
	
	public Asset(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public Asset(Image image, int x, int y, String name) {
		this.identifier = name;
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public Asset(Image[] images, int x, int y) {
		this.animated = true;
		this.images = images;
		this.x = x;
		this.y = y;
	}
	
	public Asset(Image[] images, int x, int y, String identifier) {
		this.animated = true;
		this.identifier = identifier;
		this.images = images;
		this.x = x;
		this.y = y;
	}
	
	public String getName() {
		return this.identifier;
	}
	
	public boolean isAnimated() {
		return this.animated;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int nextAnimation() {
		if(animated) {
			animationCycle++;
			if(animationCycle > images.length - 1) {
				animationCycle = 0;
			}
			return animationCycle;
		}
		return 0;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public Image getImage(int index) {
		try {
			return this.images[index];
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			aioobe.printStackTrace();
		}
		return null;
	}

	public Image[] getImages() {
		return this.images;
	}
}
