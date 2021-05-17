package ky;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) {
		Screen screen = new Screen(800, 600, true, true);
		
		try {
			Image image = ImageIO.read(new File("test.png"));
			Asset test = new Asset(image, 0, 0);
			Asset test2 = new Asset(image, 50, 0);
			Asset test3 = new Asset(image, 100, 0);
			Asset test4 = new Asset(image, 150, 0);
			Asset test5 = new Asset(image, 200, 0);
			Asset test6 = new Asset(image, 250, 0);
			Asset test7 = new Asset(image, 0, 100);
			Asset test8 = new Asset(image, 50, 100);
			Asset test9 = new Asset(image, 100, 100);
			Asset test10 = new Asset(image, 150, 100);
			Asset test11 = new Asset(image, 200, 100);
			Asset test12 = new Asset(image, 250, 100);
			screen.add(test, 0);
			screen.add(test2, 0);
			screen.add(test3, 0);
			screen.add(test4, 0);
			screen.add(test5, 0);
			screen.add(test6, 0);

			screen.update();
			
			screen.add(test7, 0);
			screen.add(test8, 0);
			screen.add(test9, 0);
			screen.add(test10, 0);
			screen.add(test11, 0);
			screen.add(test12, 0);
			screen.update();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
