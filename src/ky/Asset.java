package ky;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asset {

	public static BufferedImage readImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException missingTexture) {
			missingTexture.printStackTrace();
			image = getMissing();
		}
		return image;
	}
	
	public static BufferedImage[] readImage(String[] filenames) {
		BufferedImage[] images = new BufferedImage[filenames.length];
		for(int i = 0; i < filenames.length; i++) {
			try {
				images[i] = ImageIO.read(new File(filenames[i]));
			} catch (IOException missingTexture) {
				missingTexture.printStackTrace();
				images[i] = getMissing();
			}
		}
		return images;
	}
	
	public static BufferedImage getMissing() {
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
	
	private Vector2D position;
	private int width, height;
	protected BufferedImage[] images = null; // sprite(s) of asset
	private String name = ""; // asset's name
	private boolean visible = false; // if asset will be rendered
	private int imageIndex = 0;
	private int layer = 0;
	
	public Asset(BufferedImage image, Vector2D position, int layer) {
		this.images = new BufferedImage[] {image};
		this.position = position;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, Vector2D position, int width, int height, int layer) {
		this.images = new BufferedImage[] {image};
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {image};
		this.position = position;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {image};
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int layer) {
		this.images = images;
		this.position = position;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int width, int height, int layer) {
		this.images = images;
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = images;
		this.position = position;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = images;
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int layer) {
		this.images = new BufferedImage[] {readImage(filename)};
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int width, int height, int layer) {
		this.images = new BufferedImage[] {readImage(filename)};
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {readImage(filename)};
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {readImage(filename)};
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int layer) {
		this.images = readImage(filenames);
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int width, int height, int layer) {
		this.images = readImage(filenames);
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = readImage(filenames);
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = readImage(filenames);
		this.position = position;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}

	// visual related methods
	
	public void rescale(double factor) {
		this.width *= factor;
		this.height *= factor;
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

	// position related methods
	
	public void setPos(double x, double y) {
		this.position.set(x, y);
	}
	
	public void setPos(Vector2D position) {
		this.position = position;
	}
	
	public Vector2D getPos() {
		return this.position.clone();
	}
	
	public double getX() {
		return this.position.clone().getX();
	}
	
	public double getY() {
		return this.position.clone().getY();
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
	
	// other methods
	
	public String getName() {
		return this.name;
	}
	
	public Asset clone() {
		Asset clone = new Asset(this.images, getPos(), getWidth(), getHeight(), this.layer, this.name);
		clone.setVisible(this.visible);
		clone.setImageIndex(this.imageIndex);
		return clone; 
	}
	
	public void setImageIndex(int index) {
		imageIndex = index;
	}
	
	public int getImageIndex() {
		return this.imageIndex;
	}
	
	public BufferedImage getImage() {
		return getImage(this.imageIndex);
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
}
