package ky;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Asset {
	
	private static ArrayList<ArrayList<Asset>> assetLayers = new ArrayList<ArrayList<Asset>>(); // this is a collection of arraylists, which are layers
																								// assets within layers do not have render priorities
	   																							// between them
	public static Asset[][] getAssets(){ // gets all assets whilst keeping the layers
		Asset[][] converted = new Asset[assetLayers.size()][];
		for(int i = 0; i < assetLayers.size(); i++) {
			converted[i] = assetLayers.get(i).toArray(new Asset[assetLayers.get(i).size()]);
		}
		return converted;
	}
	
	
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
	private boolean animated = false; // if asset has animation or not
	private int animationCycle = 0; // the index of sprites which the animation is currently on
	private double animationTime = 0; // milliseconds between animation sprites, this should not be less than screen's mspf
	private double animationReferenceTime = 0; // current time at which animation sprite is changed
	
	public Asset(BufferedImage image, double x, double y) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public Asset(BufferedImage image, double x, double y, int width, int height) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(BufferedImage image, double x, double y, String name) {
		this.name = name;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public Asset(BufferedImage image, double x, double y, int width, int height, String name) {
		this.name = name;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(BufferedImage[] images, double x, double y, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
	}
	
	public Asset(BufferedImage[] images, double x, double y, int width, int height, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(BufferedImage[] images, double x, double y, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = images[0].getWidth();
		this.height = images[0].getHeight();
	}
	
	public Asset(BufferedImage[] images, double x, double y, int width, int height, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.images = images;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String filename, double x, double y) {
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public Asset(String filename, double x, double y, int width, int height) {
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String filename, double x, double y, String name) {
		this.name = name;
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public Asset(String filename, double x, double y, int width, int height, String name) {
		this.name = name;
		this.image = readImage(filename);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String[] filenames, double x, double y, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
	}
	
	public Asset(String[] filenames, double x, double y, int width, int height, double animationTime) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Asset(String[] filenames, double x, double y, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = this.images[0].getWidth();
		this.height = this.images[0].getHeight();
	}
	
	public Asset(String[] filenames, double x, double y, int width, int height, double animationTime, String name) {
		this.animated = true;
		this.animationTime = animationTime * 1000;
		this.animationReferenceTime = System.currentTimeMillis();
		this.name = name;
		this.images = readImage(filenames);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	// adds the assets to layers according to index (zero based indexing, 0 being bottom)
	// visible boolean is to either remove or add asset
	public void setVisible(boolean visible, int layer) {
		if(visible) { 										// if adding asset
			int difference = layer + 1 - assetLayers.size();// check if the indicated layer exists or not
			if(difference > 0) { 							// if difference is greater than 0,
				for(int i = 0; i < difference; i++) {		// there needs to be filler layers to reach the indicated layer
					assetLayers.add(new ArrayList<Asset>());
				}
			}
		}
		else if(!visible && assetLayers.get(layer).contains(this)) { // if layer exists and is removing asset
			assetLayers.get(layer).remove(this);
		}
		if(visible && !assetLayers.get(layer).contains(this)) { // add to layer
			assetLayers.get(layer).add(this);
		}
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
	
	public double[] getPos() {
		return new double[]{this.x, this.y};
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
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
