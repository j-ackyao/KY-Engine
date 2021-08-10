package ky;

import java.awt.Color;
import java.awt.Dimension;
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


	private ArrayList<ArrayList<Asset>> assetLayers = new ArrayList<ArrayList<Asset>>(); // this is a collection of arraylists, which are layers
																								// assets within layers do not have render priorities
	   																							// between them
	private ArrayList<ArrayList<Entity>> entityLayers = new ArrayList<ArrayList<Entity>>(); // holds all the entities to be rendered
	
	private ArrayList<CollisionEntity> collisionEntities = new ArrayList<CollisionEntity>(); // holds all collision entities to handle collisions easier
	
	public Entity[][] getEntityLayers(){
		Entity[][] converted = new Entity[entityLayers.size()][];
		for(int i = 0; i < entityLayers.size(); i++) {
			converted[i] = entityLayers.get(i).toArray(new Entity[entityLayers.get(i).size()]);
		}
		return converted;
	}
	
	public Asset[][] getAssetLayers(){ // gets all assets whilst keeping the layers
		Asset[][] converted = new Asset[assetLayers.size()][];
		for(int i = 0; i < assetLayers.size(); i++) {
			converted[i] = assetLayers.get(i).toArray(new Asset[assetLayers.get(i).size()]);
		}
		return converted;
	}
	
	public CollisionEntity[] getCollisionEntities() {
		return collisionEntities.toArray(new CollisionEntity[collisionEntities.size()]);
	}
	
	
	private Image offscreen;
	private Graphics offg;
	
	private double mspf = 0; // milliseconds per frame
	private double referenceTime = 0;
	private double deltaT = 0; // this should be elsewhere, but will be here temporarily (probably)
	
	private Vector2D cameraPos = new Vector2D(0, 0);
	
	private ArrayList<Integer> activeKeyCodes = new ArrayList<Integer>();
	
	public KYscreen(int width, int height, boolean resizable) {
		this.getContentPane().setPreferredSize(new Dimension(width, height));
		this.pack();
		
		//this.windowOffset = new Dimension(this.getWidth() - this.getContentPane().getWidth(), this.getHeight() - this.getContentPane().getHeight());
		
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
		this.getContentPane().setPreferredSize(new Dimension(width, height));
		this.pack();

		//this.windowOffset = new Dimension(this.getWidth() - this.getContentPane().getWidth(), this.getHeight() - this.getContentPane().getHeight());
		//System.out.println(windowOffset.height);
		
		this.addKeyListener(keyListener);
		this.addMouseListener(mouseListener);
		this.setResizable(resizable);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (FPScap > 0) {
			mspf = (double) 1000 / FPScap;
		}
		else {
			System.out.println("Invalid FPS cap, no FPS cap will be set.");
		}
		referenceTime = System.currentTimeMillis();
		
		
		start();
		run();
	}
	
	public abstract void start();
	
	public abstract void update(double deltaT);
	
	public void updateEntities() {
		for(Entity[] entityLayer : getEntityLayers()) {
			ArrayList<Integer> clonedKeyCodes = activeKeyCodes;
			for(Entity e : entityLayer) {
				e.update(deltaT, clonedKeyCodes);
				if(e instanceof CollisionEntity) {
					((CollisionEntity) e).updateCollision(getCollisionEntities(), deltaT);
				}
				e.addPos(Vector2D.multiply(e.getVel(), deltaT));
			}
		}
	}
	
	private void run() {
		while(isVisible()) {
			if(System.currentTimeMillis() - referenceTime > mspf) {
				deltaT = (double) (System.currentTimeMillis() - referenceTime) / 1000;
				referenceTime = System.currentTimeMillis();
				
				updateEntities();

				update(deltaT);
				
				
				render(getGraphics());
			}
		}
		
	}
	
	// called from update to actually update the screen
	private void render(Graphics g) {
		offscreen = createImage(getWidth(), getHeight());
		offg = offscreen.getGraphics();

		Entity[][] allEntities = getEntityLayers();	// retrieve all our entities
		Asset[][] allAssets = getAssetLayers();		// retrieve all our assets
		boolean renderAssets = true;
		boolean renderEntities = true;
		int i = 0;										// this acts as the index for the layers, goes through all the layers to render
		
		while(renderAssets || renderEntities) {			// acts like a "for loop," stops when either entities or assets have been rendered
			
			if(i < allEntities.length) {
				if(allEntities[i].length != 0) {		// if entity layer is not empty
					for(Entity e : allEntities[i]) {
						if(e.isVisible()) {
							for(Asset[] assetLayer : e.getAssets()) {
								for(Asset a : assetLayer) {
									if(a.isVisible()) {
										int renderXPos = (int) Math.round(a.getX() - (double) a.getWidth()/2 + e.getX() - cameraPos.getX());
										int renderYPos = (int) Math.round(a.getY() - (double) a.getHeight()/2 + e.getY() - cameraPos.getY());
										offg.drawImage(a.getImage(), renderXPos, renderYPos, a.getWidth(), a.getHeight(), null);
									}
								}
							}
							if(e instanceof CollisionEntity) {
								if(((CollisionEntity) e).getCollisionBoxVisibility()) {
									CollisionBox cb = ((CollisionEntity) e).getCollisionBox();
									CollisionBox xb = ((CollisionEntity) e).getXCollisionBox();
									CollisionBox yb = ((CollisionEntity) e).getYCollisionBox();
									offg.setColor(Color.black);
									offg.drawRect(cb.x - (int) Math.round(getCameraPos().getX()), cb.y - (int) Math.round(getCameraPos().getY()), cb.width, cb.height);
									offg.setColor(Color.red);
									offg.drawRect(xb.x - (int) Math.round(getCameraPos().getX()), xb.y - (int) Math.round(getCameraPos().getY()), xb.width, xb.height);
									offg.setColor(Color.blue);
									offg.drawRect(yb.x - (int) Math.round(getCameraPos().getX()), yb.y - (int) Math.round(getCameraPos().getY()), yb.width, yb.height);
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
							double renderXPos = a.getX() - (double) a.getWidth()/2 - cameraPos.getX();
							double renderYPos = a.getY() - (double) a.getHeight()/2 - cameraPos.getY();
							offg.drawImage(a.getImage(), (int) Math.round(renderXPos), (int) Math.round(renderYPos), a.getWidth(), a.getHeight(), null);
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
		
		// this keeps track of all collision entities
		if(entity instanceof CollisionEntity && !collisionEntities.contains(entity)) {
			collisionEntities.add((CollisionEntity) entity);
			//((CollisionEntity) entity).onCollision();
		}
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
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(activeKeyCodes.contains(e.getKeyCode())) {
				activeKeyCodes.remove(activeKeyCodes.indexOf(e.getKeyCode()));
			}
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
