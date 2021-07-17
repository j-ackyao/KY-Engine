package ky;

import java.util.ArrayList;

public class Entity {
	
	
	// everything above this comment is for the entity class (static)
	// below this comment is for the respective entity
	
	
	private double x;
	private double y;
	private boolean visible;
	private int layer;
	private ArrayList<ArrayList<Asset>> entityAssetLayers = new ArrayList<ArrayList<Asset>>(); // assets of entity (no render order)
	
	
	public Entity(double x, double y, int layer) {
		this.x = x;
		this.y = y;
		this.layer = layer;
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

	public double[] getPos() {
		return new double[]{this.x, this.y};
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setPos(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void addPos(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void addPos(Vector2D displacementVector) {
		this.x += displacementVector.getX();
		this.y += displacementVector.getY();
	}
}
