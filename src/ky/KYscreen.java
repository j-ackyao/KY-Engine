package ky;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public abstract class KYscreen extends JFrame {
	private static final long serialVersionUID = 1897229948652321731L;


	protected ArrayList<ArrayList<Asset>> assetLayers = new ArrayList<ArrayList<Asset>>(); // this is a collection of arraylists, which are layers
																								// assets within layers do not have render priorities
	   																							// between them
	protected ArrayList<ArrayList<Entity>> entityLayers = new ArrayList<ArrayList<Entity>>(); // holds all the entities to be rendered
	
	protected ArrayList<CollisionEntity> collisionEntities = new ArrayList<CollisionEntity>(); // holds all collision entities to handle collisions easier
	
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
	
	public void setDebugMode(boolean debug) { // debug mode makes assets and collision entities' borders visible
		this.debug = debug;
	}
	public boolean getDebugMode() {
		return this.debug;
	}
	
	private boolean debug = false;
	private boolean isFullScreen = false;
	private boolean cursorVisible = true;
	
	private Image offscreen;
	private Graphics offg;
	
	private double mspf = 0; // milliseconds per frame
	private double referenceTime = 0;
	protected double deltaT = 0; // this should be elsewhere, but will be here temporarily (probably)
	
	private Vector2D cameraPos = new Vector2D(0, 0);
	
	protected ArrayList<Integer> activeKeyCodes = new ArrayList<Integer>();
	
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
	
	public abstract void update();
	
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

				update();
				
				
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
							for(Asset[] assetLayer : e.getAssetLayers()) {
								for(Asset a : assetLayer) {
									if(a.isVisible()) {
										int renderXPos = (int) Math.round(a.getX() - (double) a.getWidth()/2 + e.getX() - getCameraPos().getX());
										int renderYPos = (int) Math.round(a.getY() - (double) a.getHeight()/2 + e.getY() - getCameraPos().getY());
										offg.drawImage(a.getImage(), renderXPos, renderYPos, a.getWidth(), a.getHeight(), null);
										if(a.getDebugVisibility() || this.debug) {
											offg.setColor(Color.red);
											offg.drawRect(renderXPos, renderYPos, a.getWidth(), a.getHeight());
										}
									}
								}
							}
							if(e instanceof CollisionEntity) {
								if(((CollisionEntity) e).getCollisionBoxVisibility() || this.debug) {
									CollisionBox cb = ((CollisionEntity) e).getCollisionBox();
									CollisionBox xb = ((CollisionEntity) e).getXCollisionBox();
									CollisionBox yb = ((CollisionEntity) e).getYCollisionBox();
									offg.setColor(Color.black);
									offg.drawRect(cb.x - (int) Math.round(getCameraPos().getX()), cb.y - (int) Math.round(getCameraPos().getY()), cb.width, cb.height);
									offg.setColor(Color.green);
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
							if(a instanceof Text) {
								((Text) a).updateText();
							}

							int renderXPos = (int) Math.round(a.getX() - (double) a.getWidth()/2 - getCameraPos().getX());
							int renderYPos = (int) Math.round(a.getY() - (double) a.getHeight()/2 - getCameraPos().getY());
							offg.drawImage(a.getImage(), renderXPos, renderYPos, a.getWidth(), a.getHeight(), null);
							if(a.getDebugVisibility() || this.debug) {
								offg.setColor(Color.red);
								offg.drawRect(renderXPos, renderYPos, a.getWidth(), a.getHeight());
							}
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
	
	public void add(Asset asset) {
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
	
	public void add(Entity entity) {
		
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

	public void setFullScreen (boolean fullscreen) {
		if (fullscreen) {
			isFullScreen = true;
			GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gD = gE.getDefaultScreenDevice();
			setSize(gD.getDisplayMode().getWidth(), gD.getDisplayMode().getHeight());
			dispose();
			setUndecorated(true);
			setResizable(false);
			setVisible(true);
		} else {
			isFullScreen = false;
			dispose();
			setExtendedState(JFrame.NORMAL);
			setUndecorated(false);
			setVisible(true);
		}
	}

	public boolean getFullScreen () {
		return isFullScreen;
	}

	public void setCursorVisible (boolean visible) {
		if (!visible) {
			cursorVisible=false;
			setCursor(getToolkit().createCustomCursor(
				new BufferedImage(3,3, BufferedImage.TYPE_INT_ARGB), new Point(0,0), "null"));
		} else {
			cursorVisible=true;
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public boolean getCursorVisible () {
		return cursorVisible;
	}

	KeyListener keyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(!activeKeyCodes.contains(e.getKeyCode())) {
				activeKeyCodes.add(e.getKeyCode());
			}
			KYscreen.this.keyPressed(e.getKeyCode());
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(activeKeyCodes.contains(e.getKeyCode())) {
				activeKeyCodes.remove(activeKeyCodes.indexOf(e.getKeyCode()));
			}
			KYscreen.this.keyReleased(e.getKeyCode());
		}
		@Override
		public void keyTyped(KeyEvent e) {
			KYscreen.this.keyTyped(e.getKeyCode());
		}
	};
	
	public abstract void keyPressed(int keyCode);
	
	public abstract void keyReleased(int keyCode);
	
	public abstract void keyTyped(int keyCode);
	
	MouseListener mouseListener = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
	};
}
