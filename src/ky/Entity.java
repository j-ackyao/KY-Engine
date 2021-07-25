package ky;

import java.util.ArrayList;

public class Entity {
	
	
	// everything above this comment is for the entity class (static)
	// below this comment is for the respective entity
	
	
	public Vector2D position;
	public Vector2D velocity;
	private String name;
	private boolean visible;
	private int layer;
	private ArrayList<ArrayList<Asset>> entityAssetLayers = new ArrayList<ArrayList<Asset>>(); // assets of entity (no render order)
	
	
	public Entity(double x, double y, int layer) {
		this.position = new Vector2D(x, y);
		this.velocity = new Vector2D(0, 0);
		this.layer = layer;
	}
	
	public Entity(Vector2D position, int layer) {
		this.position = position;
		this.velocity = new Vector2D(0, 0);
		this.layer = layer;
	}
	
	public Entity(double x, double y, int layer, String name) {
		this.position = new Vector2D(x, y);
		this.velocity = new Vector2D(0, 0);
		this.layer = layer;
		this.name = name;
	}
	
	public Entity(Vector2D position, int layer, String name) {
		this.position = position;
		this.velocity = new Vector2D(0, 0);
		this.layer = layer;
		this.name = name;
	}
	
	// adds the assets to layers according to index (zero based indexing, 0 being bottom)
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public int getLayer() {
		return this.layer;
	}
	
	public void addAsset(Asset asset) {
		int difference = asset.getLayer() + 1 - entityAssetLayers.size();// check if the indicated layer exists or not
		if(difference > 0) { 							// if difference is greater than 0,
			for(int i = 0; i < difference; i++) {		// there needs to be filler layers to reach the indicated layer
				entityAssetLayers.add(new ArrayList<Asset>());
			}
		}
		if(!entityAssetLayers.get(asset.getLayer()).contains(asset)) { // add to layer
			entityAssetLayers.get(asset.getLayer()).add(asset);
		}
	}
	
	public Asset getAsset(String name) {
		for(ArrayList<Asset> assetLayer : entityAssetLayers) {
			for(Asset asset : assetLayer) {
				if(asset.getName().equals(name)) {
					return asset;
				}
			}
		}
		return null;
	}
	
	public Asset[][] getAssets() {
		Asset[][] converted = new Asset[entityAssetLayers.size()][];
		for(int i = 0; i < entityAssetLayers.size(); i++) {
			converted[i] = entityAssetLayers.get(i).toArray(new Asset[entityAssetLayers.get(i).size()]);
		}
		return converted;
	}

	public Vector2D getPos() {
		return this.position;
	}
	
	public double getX() {
		return this.position.getX();
	}
	
	public double getY() {
		return this.position.getY();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPos(double x, double y) {
		this.position.set(x, y);
	}
	
	public void setPos(Vector2D position) {
		this.position = position;
	}
	
	public void addPos(double x, double y) {
		this.position.add(x, y);
	}
	
	public void addPos(Vector2D displacement) {
		this.position.add(displacement);
	}
}
