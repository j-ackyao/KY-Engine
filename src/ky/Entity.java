package ky;

import java.util.ArrayList;
import java.util.Arrays;

public class Entity {
	
	private static ArrayList<ArrayList<Entity>> entityLayers = new ArrayList<ArrayList<Entity>>(); // holds all the entities to be rendered
	
	public static Entity[][] getEntities(){
		Entity[][] converted = new Entity[entityLayers.size()][];
		for(int i = 0; i < entityLayers.size(); i++) {
			converted[i] = entityLayers.get(i).toArray(new Entity[entityLayers.get(i).size()]);
		}
		return converted;
	}
	
	// everything above this comment is for the entity class (static)
	// below this comment is for the respective entity
	
	
	private int x;
	private int y;
	private ArrayList<Asset> assets = new ArrayList<Asset>(); // assets of entity (no render order)
	
	
	public Entity(int x, int y, Asset[] assets) {
		this.x = x;
		this.y = y;
		this.assets.addAll(Arrays.asList(assets));
	}
	
	// adds the assets to layers according to index (zero based indexing, 0 being bottom)
	public void setVisible(boolean visible, int layer) {
		int difference = layer + 1 - entityLayers.size();
		if(difference > 0) { // checks if the indicated index doesn't exist
			if(visible) { // if does not exist yet and is adding, add filler layers
				for(int i = 0; i < difference; i++) {
					entityLayers.add(new ArrayList<Entity>());
				}
			}
		}
		else if(!visible && entityLayers.get(layer).contains(this)) { // if layer does exist and is remove
			entityLayers.get(layer).remove(this);
		}
		if(visible && !entityLayers.get(layer).contains(this)) { // add to layer
			entityLayers.get(layer).add(this);
		}
	}
	
	public void addAsset(Asset asset) {
		assets.add(asset);
	}
	
	public Asset getAsset(String name) {
		for(Asset asset : assets) {
			if(asset.getName().equals(name)) {
				return asset;
			}
		}
		return null;
	}
	
	public Asset[] getAssets() {
		return assets.toArray(new Asset[assets.size()]);
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
