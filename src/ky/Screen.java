package ky;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Screen extends JFrame {
	private static final long serialVersionUID = 1897229948652321731L;

	
	private Image offscreen;
	private Graphics offg;
	
	private double mspf = 0; // milliseconds per frame
	private double referenceTime = 0;
	public double deltaT = 0; // this should be elsewhere, but will be here temporarily (probably)
	
	private KeyEvent keyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_UNDEFINED, KeyEvent.CHAR_UNDEFINED);
	private ArrayList<Character> activeKeys = new ArrayList<Character>();
	
	
	private ArrayList<ArrayList<Asset>> assetLayers = new ArrayList<ArrayList<Asset>>(); // this is a collection of arraylists, which are layers
																				// assets within layers do not have render priorities
																			   // between them
	public Screen(int width, int height, boolean resizable) {
		this.setSize(width, height);
		this.addKeyListener(keyListener);
		this.addMouseListener(mouseListener);
		this.setResizable(resizable);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		referenceTime = System.currentTimeMillis();
	}
	
	public Screen(int width, int height, boolean resizable, int FPScap) {
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
	}
	
	// called from main to update the screen, this func will only call refresh when time between frames matches the fps
	public void update() {
		if(System.currentTimeMillis() - referenceTime < mspf) {
			//System.out.println(mspf + ", " + (System.currentTimeMillis() - referenceTime));
		}
		else {
			deltaT = System.currentTimeMillis() - referenceTime;
			referenceTime = System.currentTimeMillis();
			render(getGraphics());
		}
	}
	
	// called from update to actually update the screen
	private void render(Graphics g) {
		offscreen = createImage(getWidth(), getHeight());
		offg = offscreen.getGraphics();
		
		for (ArrayList<Asset> layer : assetLayers) {
			if (!layer.isEmpty()) {
				for (Asset asset : layer) {
					if (asset != null) {
						offg.drawImage(asset.getImage(), asset.getX(), asset.getY(), asset.getWidth(), asset.getHeight(), null);
					}
				}
			}
		}
		g.drawImage(offscreen, 0, 0, this);
		offg.dispose();
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
	
	public KeyEvent getKeyEvent() {
		return keyEvent;
	}
	
	public Character[] getKeys() {
		Character[] current = activeKeys.toArray(new Character[activeKeys.size()]);
		return current;
	}
	
	KeyListener keyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(!activeKeys.contains(e.getKeyChar())) {
				activeKeys.add(e.getKeyChar());
			}
			keyEvent = e;
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(activeKeys.contains(e.getKeyChar())) {
				activeKeys.remove(activeKeys.indexOf(e.getKeyChar()));
			}
			keyEvent = e;
		}
		@Override
		public void keyTyped(KeyEvent e) {
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
