package ky;

import java.util.ArrayList;

public class Entity {
	
	
	// everything above this comment is for the entity class (static)
	// below this comment is for the respective entity
	
	
	private Vector2D position, velocity = new Vector2D(0, 0);
	private double rotation = 0;
	private String name = "";
	private boolean visible = false;
	private int layer;
	private ArrayList<ArrayList<Asset>> entityAssetLayers = new ArrayList<ArrayList<Asset>>(); // assets of entity (no render order)
	
	
	public Entity(double x, double y, int layer) {
		this.position = new Vector2D(x, y);
		this.layer = layer;
	}
	
	public Entity(Vector2D position, int layer) {
		this.position = position;
		this.layer = layer;
	}
	
	public Entity(double x, double y, int layer, String name) {
		this.position = new Vector2D(x, y);
		this.layer = layer;
		this.name = name;
	}
	
	public Entity(Vector2D position, int layer, String name) {
		this.position = position;
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
	
	public void add(Asset asset) {
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
	
	/* doesn't seem necessary, user should have assets in track anyways
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
	*/
	
	public Asset[][] getAssetLayers() {
		Asset[][] converted = new Asset[entityAssetLayers.size()][];
		for(int i = 0; i < entityAssetLayers.size(); i++) {
			converted[i] = entityAssetLayers.get(i).toArray(new Asset[entityAssetLayers.get(i).size()]);
		}
		return converted;
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
	
	public Vector2D getVel() {
		return this.velocity.clone();
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
	
	public void setVel(double x, double y) {
		this.velocity.set(x, y);
	}
	
	public void setVel(Vector2D velocity) {
		this.velocity = velocity;
	}
	
	public void addVel(double x, double y) {
		this.velocity.add(x, y);
	}
	
	public void addVel(Vector2D deltaV) {
		this.velocity.add(deltaV);
	}
	
	public Entity clone() {
		Entity clone = new Entity(getPos().clone(), getLayer(), getName());
		for(Asset[] layers : getAssetLayers()) {
			for(Asset a : layers) {
				clone.add(a.clone());
			}
		}
		clone.setVel(getVel());
		clone.setVisible(isVisible());
		return clone;
	}
	
	public void update(double deltaT, ArrayList<Integer> keyCodes) {
		
	}
}
