package ky;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asset {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage image = null;
	private BufferedImage[] images = null;
	private String identifier = null;
	private boolean animated = false;
	private int animationCycle = 0;
	private double animationTime = 0; // milliseconds between animation sprites, this should not be less than screen's mspf
	private double animationReferenceTime = 0; // current time at which animation sprite is changed
	
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
	
	public Asset(BufferedImage[] images, int x, int y, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
	}
	
	public Asset(BufferedImage[] images, int x, int y, int width, int height, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(BufferedImage[] images, int x, int y, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.identifier = name;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
	}
	
	public Asset(BufferedImage[] images, int x, int y, int width, int height, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
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
	
	public Asset(String[] filenames, int x, int y, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
	}
	
	public Asset(String[] filenames, int x, int y, int width, int height, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String[] filenames, int x, int y, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.identifier = name;
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
	}
	
	public Asset(String[] filenames, int x, int y, int width, int height, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
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
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public BufferedImage getImage() {
		if (animated) {
			if(System.currentTimeMillis() - animationReferenceTime > animationTime) {
				animationReferenceTime = System.currentTimeMillis();
				animationCycle++;
				if(animationCycle > images.length - 1) {
					animationCycle = 0;
				}
				return getImage(animationCycle);
			}
			return getImage(animationCycle);
		}
		else {
			return this.image;
		}
	}
	
	public BufferedImage getImage(int index) {
		try {
			return this.images[index];
		} catch (ArrayIndexOutOfBoundsException noImageAtIndex) {
			noImageAtIndex.printStackTrace();
			return null;
		}
	}

	public BufferedImage[] getImages() {
		return this.images;
	}
	
	
	private BufferedImage readImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException missingTexture) {
			missingTexture.printStackTrace();
			try {
				image = ImageIO.read(new File("missing.png"));
			} catch (IOException missingFallbackTexture) {
				missingFallbackTexture.printStackTrace();
				System.exit(0);
			}
		}
		return image;
	}
	
	private BufferedImage[] readImage(String[] filenames) {
		BufferedImage[] images = new BufferedImage[filenames.length];
		for(int i = 0; i < filenames.length; i++) {
			try {
				images[i] = ImageIO.read(new File(filenames[i]));
			} catch (IOException missingTexture) {
				missingTexture.printStackTrace();
				try {
					images[i] = ImageIO.read(new File("missing.png"));
				} catch (IOException missingFallbackTexture) {
					missingFallbackTexture.printStackTrace();
					System.exit(0);
				}
			}
		}
		return images;
	}
}
