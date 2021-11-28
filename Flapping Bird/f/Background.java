package f;

import ky.Asset;
import ky.Entity;
import ky.Vector2D;

public class Background extends Entity {

	Asset background;
	Asset background2;
	
	public Background() {
		super(0, 0, 0);
		background = new Asset("Flapping Bird/Assets/background.png", new Vector2D(400, 300), 0);
		background.setVisible(true);
		add(background);
		background2 = background.clone();
		background2.setPos(background.getWidth() + background.getX(), background.getY());
		add(background2);
		
		setVisible(true);
	}
	

	public void move(double deltaT) {
		background.setPos(background.getX() - Main.speed * deltaT / 2, background.getY());
		background2.setPos(background2.getX() - Main.speed * deltaT / 2, background2.getY());
		
		if(background.getX() < -background.getWidth()/2) {
			background.setPos(background2.getX() + background2.getWidth() - 10, background2.getY());
		}
		if(background2.getX() < -background2.getWidth()/2) {
			background2.setPos(background.getX() + background.getWidth() - 10, background.getY());
		}
		
		
	}

}
