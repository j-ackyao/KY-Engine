package ky;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	static long time = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Screen screen = new Screen(720, 664, true, 2);
		
		try {
			Image nya = ImageIO.read(new File("nya.png"));
			Image ichi = ImageIO.read(new File("ichi.png"));
			Image ni = ImageIO.read(new File("ni.png"));
			Image san = ImageIO.read(new File("san.png"));
			Image arigatou = ImageIO.read(new File("arigatou.png"));
			Image[] group = {nya, ichi, ni, san, nya, arigatou};
			Asset asset = new Asset(group, 0, 0);
			screen.add(asset, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			screen.update();
		}
		
	}
}
