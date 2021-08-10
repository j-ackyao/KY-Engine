package ky;

import java.awt.image.BufferedImage;

public class AnimationAsset extends Asset {

	private boolean animating = false; // if asset is currently in animation
	private int animationCycle = 0; // the index of sprites which the animation is currently on
	private double animationTime = 0; // milliseconds between animation sprites, this should not be less than screen's mspf
	private double animationReferenceTime = 0; // current time at which animation sprite is changed
	
	public AnimationAsset(BufferedImage[] images, Vector2D position, double animationTime, int layer) {
		super(images, position, layer);
		this.animationTime = animationTime * (double) 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	public AnimationAsset(BufferedImage[] images, Vector2D position, int width, int height, double animationTime, int layer) {
		super(images, position, width, height, layer);
		this.animationTime = animationTime * (double) 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	public AnimationAsset(BufferedImage[] images, Vector2D position, double animationTime, int layer, String name) {
		super(images, position, layer, name);
		this.animationTime = animationTime * (double) 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	public AnimationAsset(BufferedImage[] images, Vector2D position, int width, int height, double animationTime, int layer, String name) {
		super(images, position, width, height, layer, name);
		this.animationTime = animationTime * (double) 1000 / images.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	public AnimationAsset(String[] filenames, Vector2D position, double animationTime, int layer) {
		super(filenames, position, layer);
		this.animationTime = animationTime * (double) 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	public AnimationAsset(String[] filenames, Vector2D position, int width, int height, double animationTime, int layer) {
		super(filenames, position, width, height, layer);
		this.animationTime = animationTime * (double) 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	public AnimationAsset(String[] filenames, Vector2D position, double animationTime, int layer, String name) {
		super(filenames, position, layer, name);
		this.animationTime = animationTime * (double) 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	public AnimationAsset(String[] filenames, Vector2D position, int width, int height, double animationTime, int layer, String name) {
		super(filenames, position, width, height, layer, name);
		this.animationTime = animationTime * (double) 1000 / filenames.length;
		this.animationReferenceTime = System.currentTimeMillis();
	}
	
	@Override
	public BufferedImage getImage() {
		if (animating) {
			if(System.currentTimeMillis() - animationReferenceTime > animationTime) {
				animationReferenceTime = System.currentTimeMillis();
				animationCycle++;
				if(animationCycle > getImages().length - 1) {
					animationCycle = 0;
					animating = false;
				}
				return getImage(animationCycle);
			}
			return getImage(animationCycle);
		}
		else {
			return getImage(getImageIndex());
		}
	}
	
	public void animate() {
		this.animating = true;
	}

	public boolean isAnimating() {
		return this.animating;
	}
	
	
}
