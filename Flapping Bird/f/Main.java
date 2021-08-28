package f;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import ky.Asset;
import ky.CollisionEntity;
import ky.KYscreen;
import ky.Text;
import ky.Vector2D;

public class Main extends KYscreen {
	private static final long serialVersionUID = 7029069423874039807L;

	public Main(int width, int height, boolean resizable, int FPScap) {
		super(width, height, resizable, FPScap);
	}
	
	public static void main(String[] args) {
		new Main(800, 600, false, 60);
	}

	public final static int speed = 200;
	public final static int walls = 4;
	public final static int space = 200;
	public final static int hole = 200;
	public final static int wallWidth = 100;
	public final static int upperWallMax = 300;
	public final static int upperWallMin = 100;
	
	CollisionEntity[] upperWalls;
	CollisionEntity[] lowerWalls;
	
	boolean start = false;
	Player player;
	CollisionEntity sky;
	CollisionEntity[] scoreDetectors;
	CollisionEntity[] scoreDetectorReseters;
	Ground ground;
	Background background;
	Text titleText;
	Text startText;
	Text deathText;
	Text finalScoreText;
	Text restartText;
	Text scoreText;
	
	
	@Override
	public void start() {
		setDebugMode(false);
		
		initializeObstacles();
		
		player = new Player();
		add(player);
		
		sky = new CollisionEntity(getWidth()/2, -25, getWidth(), 100, 1, "sky");
		sky.setVisible(true);
		sky.setStaticCollision(true);
		sky.setCollision(true);
		add(sky);
		
		ground = new Ground();
		add(ground);
		
		background = new Background();
		add(background);
		
		titleText = new Text("Flapping Bird", new Font("Comic Sans MS", Font.BOLD, 50), Color.black, new Vector2D(400, 200), 600, 100, 2);
		titleText.setVisible(true);
		add(titleText);
		
		startText = new Text("Press spacebar to start", new Font("Comic Sans MS", Font.BOLD, 25), Color.green, new Vector2D(400, 300), 600, 100, 2);
		startText.setVisible(true);
		add(startText);
		
		deathText = new Text("Game Over!", new Font("Comic Sans MS", Font.BOLD, 50), Color.red, new Vector2D(400, 300), 400, 100, 2);
		add(deathText);

		finalScoreText = new Text("", new Font("Comic Sans MS", Font.BOLD, 25), Color.black, new Vector2D(400, 370), 400, 100, 2);
		add(finalScoreText);
		
		restartText = new Text("Press spacebar to reset", new Font("Comic Sans MS", Font.BOLD, 25), Color.green, new Vector2D(400, 400), 400, 100, 2);
		add(restartText);
		
		scoreText = new Text("Score: 0", new Font("Comic Sans MS", Font.BOLD, 18), Color.black, new Vector2D(110, 100), 200, 100, 2);
		add(scoreText);
	}

	@Override
	public void update() {
		
		
		if(!start && getKeyStatus(KeyEvent.VK_SPACE)) { // start game
			start = true;
			player.start();
			titleText.setVisible(false);
			startText.setVisible(false);
			scoreText.setVisible(true);
		}
		
		if(!player.alive) { // game over
			deathText.setVisible(true);
			scoreText.setVisible(false);
			finalScoreText.setText("Your score: " + player.score);
			finalScoreText.setVisible(true);
			restartText.setVisible(true);
		}
		else {
			ground.move(deltaT);
			background.move(deltaT);
			if(start) {
				updateObstacles();
			}
		}
		
		scoreText.setText("Score: " + player.score);
		
	}


	@Override
	public void keyPressed(int keyCode) {
		
	}

	@Override
	public void keyReleased(int keyCode) {
		if(keyCode == KeyEvent.VK_SPACE && !player.alive) {
			start = false;
			player.reset();
			titleText.setVisible(true);
			startText.setVisible(true);
			deathText.setVisible(false);
			finalScoreText.setVisible(false);
			restartText.setVisible(false);
			scoreText.setText("Score: 0");
			resetObstacles();
		}
	}

	@Override
	public void keyTyped(int keyCode) {
	}
	
	
	void initializeObstacles() {
		upperWalls = new CollisionEntity[walls];
		lowerWalls = new CollisionEntity[walls];
		scoreDetectors = new CollisionEntity[walls];
		scoreDetectorReseters = new CollisionEntity[walls];
		
		CollisionEntity wallCopy = new CollisionEntity(-wallWidth,0,1,1,1,"wall");
		wallCopy.setVisible(true);
		
		CollisionEntity scoreDetector = new CollisionEntity(-wallWidth, getHeight()/2, 10, getHeight(), 2, "score");
		scoreDetector.setVisible(true);
		
		CollisionEntity scoreDetectorReseter = new CollisionEntity(-wallWidth, getHeight()/2, 10, getHeight(), 2, "reseter");
		scoreDetectorReseter.setVisible(true);
		
		Asset wall = new Asset("FlappingBirdAssets/wall.png", new Vector2D(0, 0), 0);
		wall.setVisible(true);
		wall.rescale(wallWidth/256d);
		for(int i = 0; i < walls; i++) {
			upperWalls[i] = wallCopy.clone();
			lowerWalls[i] = wallCopy.clone();
			scoreDetectors[i] = scoreDetector.clone();
			scoreDetectorReseters[i] = scoreDetectorReseter.clone();
			upperWalls[i].add(wall.clone());
			upperWalls[i].add(wall.clone());
			upperWalls[i].add(wall.clone());
			lowerWalls[i].add(wall.clone());
			lowerWalls[i].add(wall.clone());
			lowerWalls[i].add(wall.clone());
			add(upperWalls[i]);
			add(lowerWalls[i]);
			add(scoreDetectors[i]);
			add(scoreDetectorReseters[i]);
		}
	}
	
	void resetObstacles() {
		for(int i = 0; i < walls; i++) {
			upperWalls[i].setPos(-wallWidth, 0);
			lowerWalls[i].setPos(-wallWidth, 0);
			scoreDetectors[i].setPos(-wallWidth, getHeight()/2);
			scoreDetectorReseters[i].setPos(-wallWidth, getHeight()/2);
		}
	}
	
	void updateObstacles() {
		for(int i = 0; i < walls; i++) {
			upperWalls[i].setPos(upperWalls[i].getX() - deltaT * speed, upperWalls[i].getY());
			lowerWalls[i].setPos(lowerWalls[i].getX() - deltaT * speed, lowerWalls[i].getY());
			scoreDetectors[i].setPos(scoreDetectors[i].getX() - deltaT * speed, getHeight()/2);
			scoreDetectorReseters[i].setPos(scoreDetectorReseters[i].getX() - deltaT * speed, getHeight()/2);
			
			if(upperWalls[i].getX() < -upperWalls[i].getCollisionBox().getWidth()/2) {
				int randomHeight = upperWallMin + (int) Math.round(Math.random() * (upperWallMax - upperWallMin + 1));
				if(i == 0 && upperWalls[walls-1].getPos().getX() == -wallWidth) { // very first init
					upperWalls[i].setCollisionBoxDimensions(wallWidth, randomHeight);
					lowerWalls[i].setCollisionBoxDimensions(wallWidth, getHeight() - randomHeight - hole);
					upperWalls[i].setPos(getWidth() + wallWidth, randomHeight/2);
					lowerWalls[i].setPos(getWidth() + wallWidth, getHeight() - lowerWalls[i].getCollisionBox().getHeight()/2);
					scoreDetectors[i].setPos(getWidth() + wallWidth * 1.5, getHeight()/2);
					scoreDetectorReseters[i].setPos(getWidth(), getHeight()/2);
					
				}
				else if(i == 0) {
					upperWalls[i].setCollisionBoxDimensions(wallWidth, randomHeight);
					lowerWalls[i].setCollisionBoxDimensions(wallWidth, getHeight() - randomHeight - hole);
					upperWalls[i].setPos(upperWalls[walls-1].getPos().getX() + space + wallWidth, randomHeight/2);
					lowerWalls[i].setPos(lowerWalls[walls-1].getPos().getX() + space + wallWidth, getHeight() - lowerWalls[i].getCollisionBox().getHeight()/2);
					scoreDetectors[i].setPos(scoreDetectors[walls-1].getPos().getX() + space + wallWidth, getHeight()/2);
					scoreDetectorReseters[i].setPos(scoreDetectorReseters[walls-1].getPos().getX() + space + wallWidth, getHeight()/2);
				}
				else {
					upperWalls[i].setCollisionBoxDimensions(wallWidth, randomHeight);
					lowerWalls[i].setCollisionBoxDimensions(wallWidth, getHeight() - randomHeight - hole);
					upperWalls[i].setPos(upperWalls[i-1].getPos().getX() + space + wallWidth, randomHeight/2);
					lowerWalls[i].setPos(lowerWalls[i-1].getPos().getX() + space + wallWidth, getHeight() - lowerWalls[i].getCollisionBox().getHeight()/2);
					scoreDetectors[i].setPos(scoreDetectors[i-1].getPos().getX() + space + wallWidth, getHeight()/2);
					scoreDetectorReseters[i].setPos(scoreDetectorReseters[i-1].getPos().getX() + space + wallWidth, getHeight()/2);
				}
				

				Asset[] upperWallAssets = upperWalls[i].getAssetLayers()[0];
				Asset[] lowerWallAssets = lowerWalls[i].getAssetLayers()[0];
				double upperWallInitialPos = upperWalls[i].getCollisionBox().getHeight()/2d - wallWidth/2d;
				double lowerWallInitialPos = -lowerWalls[i].getCollisionBox().getHeight()/2d + wallWidth/2d;
				for(int u = 0; u < upperWallAssets.length; u++) {
					upperWallAssets[u].setPos(0, upperWallInitialPos - u * wallWidth);
					lowerWallAssets[u].setPos(0, lowerWallInitialPos + u * wallWidth);
				}
			}
		}
	}

}
