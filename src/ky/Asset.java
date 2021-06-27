package ky;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asset {
	
	int x;
	int y;
	int width;
	int height;
	BufferedImage image = null;
	BufferedImage[] images = null;
	String identifier = null;
	boolean animated = false;
	int animationCycle = -1;
	
	public Asset(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public Asset(BufferedImage image, int x, int y, int width, int height) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(BufferedImage image, int x, int y, String name) {
		this.identifier = name;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public Asset(BufferedImage image, int x, int y, int width, int height, String name) {
		this.identifier = name;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(BufferedImage[] images, int x, int y) {
		this.animated = true;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
	}
	
	public Asset(BufferedImage[] images, int x, int y, int width, int height) {
		this.animated = true;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(BufferedImage[] images, int x, int y, String name) {
		this.animated = true;
		this.identifier = name;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
	}
	
	public Asset(BufferedImage[] images, int x, int y, int width, int height, String name) {
		this.animated = true;
		this.identifier = name;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String filename, int x, int y) {
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public Asset(String filename, int x, int y, int width, int height) {
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String filename, int x, int y, String name) {
		this.identifier = name;
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public Asset(String filename, int x, int y, int width, int height, String name) {
		this.identifier = name;
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String[] filenames, int x, int y) {
		this.animated = true;
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
	}
	
	public Asset(String[] filenames, int x, int y, int width, int height) {
		this.animated = true;
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String[] filenames, int x, int y, String name) {
		this.animated = true;
		this.identifier = name;
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
	}
	
	public Asset(String[] filenames, int x, int y, int width, int height, String name) {
		this.animated = true;
		this.identifier = name;
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void rescale(double factor) {
		this.width *= factor;
		this.height *= factor;
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
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public BufferedImage getImage(int index) {
		try {
			return this.images[index];
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			aioobe.printStackTrace();
		}
		return null;
	}

	public BufferedImage[] getImages() {
		return this.images;
	}
	
	
	private BufferedImage readImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
			try {
				image = ImageIO.read(new File("missing.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(-1);
			}
		}
		return image;
	}
	
	private BufferedImage[] readImage(String[] filenames) {
		BufferedImage[] images = new BufferedImage[filenames.length];
		for(int i = 0; i < filenames.length; i++) {
			try {
				images[i] = ImageIO.read(new File(filenames[i]));
			} catch (IOException e) {
				e.printStackTrace();
				try {
					images[i] = ImageIO.read(new File("missing.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(-1);
				}
			}
		}
		
		return images;
	}
}
