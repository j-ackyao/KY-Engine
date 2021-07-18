package ky;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public abstract class KYscreen extends JFrame {
	private static final long serialVersionUID = 1897229948652321731L;


	private static ArrayList<ArrayList<Asset>> assetLayers = new ArrayList<ArrayList<Asset>>(); // this is a collection of arraylists, which are layers
																								// assets within layers do not have render priorities
	   																							// between them
	private static ArrayList<ArrayList<Entity>> entityLayers = new ArrayList<ArrayList<Entity>>(); // holds all the entities to be rendered
	
	public static Entity[][] getEntities(){
		Entity[][] converted = new Entity[entityLayers.size()][];
		for(int i = 0; i < entityLayers.size(); i++) {
			converted[i] = entityLayers.get(i).toArray(new Entity[entityLayers.get(i).size()]);
		}
		return converted;
	}
	
	public static Asset[][] getAssets(){ // gets all assets whilst keeping the layers
		Asset[][] converted = new Asset[assetLayers.size()][];
		for(int i = 0; i < assetLayers.size(); i++) {
			converted[i] = assetLayers.get(i).toArray(new Asset[assetLayers.get(i).size()]);
		}
		return converted;
	}
	
	
	private Image offscreen;
	private Graphics offg;
	
	private double mspf = 0; // milliseconds per frame
	private double referenceTime = 0;
	private double deltaT = 0; // this should be elsewhere, but will be here temporarily (probably)
	
	private Vector2D cameraPos = new Vector2D(0, 0);
	
	private KeyEvent keyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_UNDEFINED, KeyEvent.CHAR_UNDEFINED);
	private ArrayList<Integer> activeKeyCodes = new ArrayList<Integer>();
	
	public KYscreen(int width, int height, boolean resizable) {
		this.setSize(width, height);
		this.addKeyListener(keyListener);
		this.addMouseListener(mouseListener);
		this.setResizable(resizable);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		referenceTime = System.currentTimeMillis();
		
		start();
		run();
	}
	
	public KYscreen(int width, int height, boolean resizable, int FPScap) {
		this.setSize(width, height);
		this.addKeyListener(keyListener);
		this.addMouseListener(mouseListener);
		this.setResizable(resizable);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		try {
			mspf = 1000 / FPScap;
		}
		catch(ArithmeticException | IllegalArgumentException e) {
			System.out.println("Invalid FPS cap, no FPS cap will be set.");
		}
		referenceTime = System.currentTimeMillis();
		
		start();
		run();
	}
	
	public abstract void start();
	
	public abstract void update();
	
	private void run() {
		while(isVisible()) {
			if(System.currentTimeMillis() - referenceTime > mspf) {
				deltaT = System.currentTimeMillis() - referenceTime;
				referenceTime = System.currentTimeMillis();
				update();
				render(getGraphics());
			}
		}
		
	}
	
	// called from update to actually update the screen
	private void render(Graphics g) {
		offscreen = createImage(getWidth(), getHeight());
		offg = offscreen.getGraphics();

		Entity[][] allEntities = getEntities();	// retrieve all our entities
		Asset[][] allAssets = getAssets();		// retrieve all our assets
		boolean renderAssets = true;
		boolean renderEntities = true;
		int i = 0;										// this acts as the index for the layers, goes through all the layers to render
		
		while(renderAssets && renderEntities) {			// acts like a "for loop," stops when either entities or assets have been rendered 
			if(i < allEntities.length) {
				if(allEntities[i].length != 0) {		// if entity layer is not empty
					for(Entity e : allEntities[i]) {
						if(e.isVisible()) {
							for(Asset[] assetLayer : e.getAssets()) {
								for(Asset a : assetLayer) {
									if(a.isVisible()) {
										offg.drawImage(a.getImage(), (int) Math.round(a.getX() + e.getX() - cameraPos.getX()), (int) Math.round(a.getY() + e.getY() - cameraPos.getY()), a.getWidth(), a.getHeight(), null);
									}
								}
							}
						}
					}
				}
			}
			else {
				renderEntities = false;
			}
			
			if(i < allAssets.length) {
				if(allAssets[i].length != 0) {			// if asset layer is not empty
					for(Asset a : allAssets[i]) {
						if(a.isVisible()) {
							offg.drawImage(a.getImage(), (int) Math.round(a.getX() - cameraPos.getX()), (int) Math.round(a.getY() - cameraPos.getY()), a.getWidth(), a.getHeight(), null);
						}
					}
				}
			}
			else {
				renderAssets = false;
			}
			i++;
		}
		g.drawImage(offscreen, 0, 0, this);
		offg.dispose();
	}
	
	public void setCameraPos(Vector2D position) {
		this.cameraPos = position;
	}
	
	public void setCameraPos(double x, double y) {
		this.cameraPos.set(x, y);
	}
	
	public Vector2D getCameraPos() {
		return this.cameraPos;
	}
	
	public void addAsset(Asset asset) {
		int difference = asset.getLayer() + 1 - assetLayers.size();// check if the indicated layer exists or not
		if(difference > 0) { 							// if difference is greater than 0,
			for(int i = 0; i < difference; i++) {		// there needs to be filler layers to reach the indicated layer
				assetLayers.add(new ArrayList<Asset>());
			}
		}
		if(!assetLayers.get(asset.getLayer()).contains(asset)) { // add to layer
			assetLayers.get(asset.getLayer()).add(asset);
		}
	}
	
	public void addEntity(Entity entity) {
		int difference = entity.getLayer() + 1 - entityLayers.size();// check if the indicated layer exists or not
		if(difference > 0) { 							// if difference is greater than 0,
			for(int i = 0; i < difference; i++) {		// there needs to be filler layers to reach the indicated layer
				entityLayers.add(new ArrayList<Entity>());
			}
		}
		if(!entityLayers.get(entity.getLayer()).contains(entity)) { // add to layer
			entityLayers.get(entity.getLayer()).add(entity);
		}
	}
	
	public double deltaT() {
		return this.deltaT / 1000;
	}
	
	public KeyEvent getKeyEvent() {
		return this.keyEvent;
	}
	
	public boolean getKeyStatus(int key) {
		return this.activeKeyCodes.contains(key);
	}

	KeyListener keyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(!activeKeyCodes.contains(e.getKeyCode())) {
				activeKeyCodes.add(e.getKeyCode());
			}
			keyEvent = e;
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(activeKeyCodes.contains(e.getKeyCode())) {
				activeKeyCodes.remove(activeKeyCodes.indexOf(e.getKeyCode()));
			}
			keyEvent = e;
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	MouseListener mouseListener = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	};
}
