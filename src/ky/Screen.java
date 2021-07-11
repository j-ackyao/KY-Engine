package ky;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public abstract class Screen extends JFrame {
	private static final long serialVersionUID = 1897229948652321731L;

	
	private Image offscreen;
	private Graphics offg;
	
	private double mspf = 0; // milliseconds per frame
	private double referenceTime = 0;
	public double deltaT = 0; // this should be elsewhere, but will be here temporarily (probably)
	
	private KeyEvent keyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_UNDEFINED, KeyEvent.CHAR_UNDEFINED);
	private ArrayList<Integer> activeKeyCodes = new ArrayList<Integer>();
	
	public Screen(int width, int height, boolean resizable) {
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

		Entity[][] allEntities = Entity.getEntities();	// retrieve all our entities
		Asset[][] allAssets = Asset.getAssets();		// retrieve all our assets
		boolean renderAssets = true;
		boolean renderEntities = true;
		int i = 0;										// this acts as the index for the layers, goes through all the layers to render
		
		while(renderAssets || renderEntities) {			// acts like a "for loop," stops when either entities or assets have been rendered 
			if(i < allEntities.length) {
				if(allEntities[i].length != 0) {		// if entity layer is not empty
					for(Entity e : allEntities[i]) {
						for(Asset a : e.getAssets()) {
							offg.drawImage(a.getImage(), (int) Math.round(a.getX() + e.getX()), (int) Math.round(a.getY() + e.getY()), a.getWidth(), a.getHeight(), null);
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
						offg.drawImage(a.getImage(), (int) Math.round(a.getX()), (int) Math.round(a.getY()), a.getWidth(), a.getHeight(), null);					
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
	
	
	public KeyEvent getKeyEvent() {
		return keyEvent;
	}
	
	public boolean getKeyStatus(int key) {
		return activeKeyCodes.contains(key);
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
