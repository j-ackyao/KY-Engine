package ky;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Screen extends JFrame {
	private static final long serialVersionUID = 1L;

	Graphics g = this.getGraphics();
	
	ArrayList<ArrayList<Asset>> assetLayers = new ArrayList<ArrayList<Asset>>();
	
	
	public Screen(int width, int height, boolean resizable, boolean visible) {
		this.setSize(width, height);
		this.setResizable(resizable);
		this.setVisible(visible);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//assetLayers.add(new ArrayList<Asset>());
	}
	
	public void update() {
		System.out.println(assetLayers.toString());
		for (ArrayList<Asset> layer : assetLayers) {
			if (!layer.isEmpty()) {
				for (Asset asset : layer) {
					if (asset != null) {
						System.out.println(asset.identifier);
						getGraphics().drawImage(asset.image, asset.x, asset.y, null);
					}
				}
			}
		}
	}
	
	
	public void add(Asset asset, int layer) {
		int difference = layer + 1 - assetLayers.size();
		if(difference > 0) {
			for(int i = 0; i < difference; i++) {
				assetLayers.add(new ArrayList<Asset>());
			}
		}
		assetLayers.get(layer).add(asset);
	}
}
