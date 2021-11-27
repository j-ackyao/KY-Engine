package ky;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
	private int originalWidth, originalHeight;
	private double rotation = 0; // in degrees
	protected BufferedImage[] images = null; // sprite(s) of asset
	private BufferedImage[] originalImages = null;
	private String name = ""; // asset's name
	private boolean visible = false; // if asset will be rendered
	private int imageIndex = 0;
	private int layer = 0;
	private boolean debugVisual; // to render outline of width and height
	
	
	public Asset(BufferedImage image, Vector2D position, int layer) {
		this.images = new BufferedImage[] {image};
		this.originalImages = new BufferedImage[] {image};
		this.position = position;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, Vector2D position, int width, int height, int layer) {
		this.images = new BufferedImage[] {image};
		this.originalImages = new BufferedImage[] {image};
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {image};
		this.originalImages = new BufferedImage[] {image};
		this.position = position;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage image, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {image};
		this.originalImages = new BufferedImage[] {image};
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int layer) {
		this.images = images;
		this.originalImages = images;
		this.position = position;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int width, int height, int layer) {
		this.images = images;
		this.originalImages = images;
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = images;
		this.originalImages = images;
		this.position = position;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(BufferedImage[] images, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = images;
		this.originalImages = images;
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int layer) {
		this.images = new BufferedImage[] {readImage(filename)};
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int width, int height, int layer) {
		this.images = new BufferedImage[] {readImage(filename)};
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {readImage(filename)};
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String filename, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = new BufferedImage[] {readImage(filename)};
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int layer) {
		this.images = readImage(filenames);
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int width, int height, int layer) {
		this.images = readImage(filenames);
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int layer, String name) {
		this.name = name;
		this.images = readImage(filenames);
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}
	
	public Asset(String[] filenames, Vector2D position, int width, int height, int layer, String name) {
		this.name = name;
		this.images = readImage(filenames);
		this.originalImages = this.images.clone();
		this.position = position;
		this.width = width;
		this.height = height;
		this.originalWidth = this.width;
		this.originalHeight = this.height;
		this.layer = layer;
	}

	// visual related methods
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	

	// transformation related methods
	
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

	
	public void rescale(double factor) {
		this.width *= factor;
		this.height *= factor;
	}
	
	public void setRotation(double degrees) {
		this.rotation = 0;
		rotate(degrees);
	}
	
	public void flipHorizontal() {
		for(int i = 0; i < images.length; i++) {
			BufferedImage flippedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = flippedImage.createGraphics();
			// draw image with negative width, and translate it by its width
			// use original image to draw new image
			g.drawImage(images[i], getWidth(), 0, -getWidth(), getHeight(), null);
		    g.dispose();
			
			images[i] = flippedImage;
		}
	}
	
	public void flipVertical(){
		for(int i = 0; i < images.length; i++) {
			BufferedImage flippedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = flippedImage.createGraphics();
			// draw image with negative height, and translate it by its height
			// use original image to draw new image
			g.drawImage(images[i], 0, getHeight(), getWidth(), -getHeight(), null);
		    g.dispose();
			
			images[i] = flippedImage;
		}
	}
	
	public void rotate(double degrees) {
		double rad = (this.rotation + degrees) * 180d / Math.PI;
		this.rotation += degrees;
		// calculate new dimensions after rotation
		double cos = Math.abs(Math.cos(rad));
		double sin = Math.abs(Math.sin(rad));
		int rotatedWidth = (int) Math.round(getOriginalWidth() * cos + getOriginalHeight() * sin);
		int rotatedHeight = (int) Math.round(getOriginalHeight() * cos + getOriginalWidth() * sin);
		
		// create AT to draw with transformation
		AffineTransform at = new AffineTransform();
		at.translate((double) (rotatedWidth - getOriginalWidth()) / 2, (double) (rotatedHeight - getOriginalHeight()) / 2);
		at.rotate(rad, (double) getOriginalWidth()/2, (double) getOriginalHeight()/2);
		
		for(int i = 0; i < images.length; i++) {
			BufferedImage rotatedImage = new BufferedImage(rotatedWidth, rotatedHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = rotatedImage.createGraphics();
			g.setTransform(at);
			// use original image to draw, otherwise quality decreases
			g.drawImage(getOriginalImage(i), 0, 0, null);
		    g.dispose();
			
			images[i] = rotatedImage;
		}
		
		this.width = rotatedWidth;
		this.height = rotatedHeight;
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
	
	
	public int getOriginalWidth() {
		return this.originalWidth;
	}
	
	public int getOriginalHeight() {
		return this.originalHeight;
	}
	
	
	// other methods
	
	public String getName() {
		return this.name;
	}
	
	
	public int getLayer() {
		return this.layer;
	}
	
	
	public Asset clone() {
		Asset clone = new Asset(getImages().clone(), getPos().clone(), getWidth(), getHeight(), getLayer(), getName());
		clone.setVisible(isVisible());
		clone.setImageIndex(getImageIndex());
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
	
	
	public BufferedImage getOriginalImage() {
		return getOriginalImage(this.imageIndex);
	}
	
	public BufferedImage getOriginalImage(int index) {
		try {
			return this.originalImages[index];
		} catch (ArrayIndexOutOfBoundsException noImageAtIndex) {
			noImageAtIndex.printStackTrace();
			return getMissing();
		}
	}

	public BufferedImage[] getOriginalImages() {
		return this.originalImages;
	}
	
	public void setDebugVisibility(boolean visible) {
		this.debugVisual = visible;
	}
	
	public boolean getDebugVisibility() {
		return this.debugVisual;
	}
}
