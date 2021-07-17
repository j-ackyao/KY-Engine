package ky;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asset {
	
	
	private static BufferedImage getMissing() {
		try {
			return ImageIO.read(new File("missing.png"));
		} catch (IOException missingFallbackTexture) {
			missingFallbackTexture.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	// everything above this comment is for the asset class (static)
	// below this comment is for the respective asset
	
	private double x;
	private double y;
	private int width;
	private int height;
	private BufferedImage image = null; // sprite of asset
	private BufferedImage[] images = null; // sprites of asset if animated
	private String name = ""; // asset's name
	private boolean visible = false; // if asset will be rendered
	private int layer = 0;
	private boolean animated = false; // if asset has animation or not
	private boolean animating = false; // if asset is currently in animation
	private int animationCycle = 0; // the index of sprites which the animation is currently on
	private double animationTime = 0; // milliseconds between animation sprites, this should not be less than screen's mspf
	private double animationReferenceTime = 0; // current time at which animation sprite is changed
	
	public Asset(BufferedImage image, double x, double y, int layer) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, double x, double y, int width, int height, int layer) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, double x, double y, int layer, String name) {
		this.name = name;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, double x, double y, int width, int height, int layer, String name) {
		this.name = name;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, double x, double y, double animationTime, int layer) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.image = images[0];
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, double x, double y, int width, int height, double animationTime, int layer) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.image = images[0];
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, double x, double y, double animationTime, int layer, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.image = images[0];
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, double x, double y, int width, int height, double animationTime, int layer, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.image = images[0];
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String filename, double x, double y, int layer) {
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		this.layer = layer;
	}
	
	public Asset(String filename, double x, double y, int width, int height, int layer) {
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String filename, double x, double y, int layer, String name) {
		this.name = name;
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		this.layer = layer;
	}
	
	public Asset(String filename, double x, double y, int width, int height, int layer, String name) {
		this.name = name;
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, double x, double y, double animationTime, int layer) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = readImage(filenames);
		this.image = this.images[0];
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(String[] filenames, double x, double y, int width, int height, double animationTime, int layer) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = readImage(filenames);
		this.image = this.images[0];
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, double x, double y, double animationTime, int layer, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.images = readImage(filenames);
		this.image = this.images[0];
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(String[] filenames, double x, double y, int width, int height, double animationTime, int layer, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.images = readImage(filenames);
		this.image = this.images[0];
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public int getLayer() {
		return this.layer;
	}
	
	public void rescale(double factor) {
		this.width *= factor;
		this.height *= factor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isAnimated() {
		return this.animated;
	}
	
	public void setPos(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double[] getPos() {
		return new double[]{this.x, this.y};
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public int[] getDimensions() {
		return new int[] {this.width, this.height};
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void animate() {
		this.animating = true;
	}
	
	public BufferedImage getImage() {
		if (animated && animating) {
			if(System.currentTimeMillis() - animationReferenceTime > animationTime) {
				animationReferenceTime = System.currentTimeMillis();
				animationCycle++;
				if(animationCycle > images.length - 1) {
					animationCycle = 0;
					animating = false;
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
			return getMissing();
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
			getMissing();
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
				getMissing();
			}
		}
		return images;
	}
}
