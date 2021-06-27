package ky;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Screen extends JFrame {
	private static final long serialVersionUID = 1L;

	private double mspf = 0; // milliseconds per frame
	private double referenceTime = 0;
	public double deltaT = 0; // this should be elsewhere, but will be here temporarily (probably)
	
	
	ArrayList<ArrayList<Asset>> assetLayers = new ArrayList<ArrayList<Asset>>(); // this is a collection of arraylists, which are layers
																				// assets within layers do not have render priorities
																			   // between them
	public Screen(int width, int height, boolean resizable) {
		this.setSize(width, height);
		this.setResizable(resizable);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		referenceTime = System.currentTimeMillis();
	}
	
	public Screen(int width, int height, boolean resizable, int FPScap) {
		this.setSize(width, height);
		this.setResizable(resizable);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		mspf = 1000 / FPScap;
		referenceTime = System.currentTimeMillis();
	}
	
	// called from main to update the screen, this func will only call refresh when time between frames matches the fps
	public void update() {
		if(System.currentTimeMillis() - referenceTime < mspf) {
			System.out.println(mspf + ", " + (System.currentTimeMillis() - referenceTime));
		}
		else {
			deltaT = System.currentTimeMillis() - referenceTime;
			referenceTime = System.currentTimeMillis();
			refresh();
		}
	}
	
	// called from update to actually update the screen
	private void refresh() {
		for (ArrayList<Asset> layer : assetLayers) {
			if (!layer.isEmpty()) {
				for (Asset asset : layer) {
					if (asset != null) {
						if (asset.getImage() != null) {
							getGraphics().drawImage(asset.getImage(), asset.x, asset.y, asset.width, asset.height, null);
						}
						if (asset.getImages() != null) {
							getGraphics().drawImage(asset.getImage(asset.nextAnimation()), asset.x, asset.y, asset.width, asset.height, null);
						}
					}
				}
			}
		}
	}
	
	// adds the assets to layers according to index (zero based indexing, 0 being bottom)
	public void add(Asset asset, int layer) {
		int difference = layer + 1 - assetLayers.size();
		if(difference > 0) { // checks if the indicated index exists or not and adds layers in between
			for(int i = 0; i < difference; i++) {
				assetLayers.add(new ArrayList<Asset>());
			}
		}
		assetLayers.get(layer).add(asset);
	}
}
