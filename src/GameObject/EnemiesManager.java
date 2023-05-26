package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Handler.Resource;
import Handler.ScoringSystem;

public class EnemiesManager extends GameObj{

	private BufferedImage cactus1;
	private BufferedImage cactus2;
	private BufferedImage pterosaurs;
	private Random rand;

	private List<GameObj> gameObj;
	private ScoringSystem scoringSystem;

	public EnemiesManager(Dino dino, ScoringSystem scoringSystem) {
		setRand(new Random());
		setCactus1(Resource.getResourceImage("data/cactus1.png"));
		setCactus2(Resource.getResourceImage("data/cactus2.png"));
		setPterosaurs(Resource.getResourceImage("data/pterosaur.png"));
		setGameObj(new ArrayList<>());
		setDino(dino);
		setScoringSystem(scoringSystem);
		getGameObj().add(createEnemy());
	}

	public void update() {
		for (GameObj e : getGameObj()) {
			e.update();
		}
		GameObj enemy = getGameObj().get(0);
		if (enemy.isOutOfScreen()) {
			getScoringSystem().increaseScore(10);
			getGameObj().clear();
			getGameObj().add(createEnemy());
		}
	}

	public void draw(Graphics g) {
		for (GameObj e : getGameObj()) {
			e.draw(g);
		}
	}

	private GameObj createEnemy() {
		int type = getRand().nextInt(4); // Updated to include pterosaurs
		int xPos = getRand().nextInt(300) + 800;
		int yPos;
		boolean isSpawnHigh = getRand().nextBoolean();
		if (isSpawnHigh) {
			yPos = 95;
		} else
			yPos = 120;

		if (type == 0) {
			return new Cactus(getDino(), xPos, getCactus1().getWidth() - 10, getCactus1().getHeight() - 10, getCactus1());
		} else if (type == 1) {
			return new Cactus(getDino(), xPos, getCactus2().getWidth() - 10, getCactus2().getHeight() - 10, getCactus2());
		} else {
			return new Pterosaurs(getDino(), xPos, yPos, getPterosaurs().getWidth() - 10, getPterosaurs().getHeight() - 10,
					getPterosaurs());
		}
	}

	public boolean isCollide() {
		for (GameObj e : getGameObj()) {
			if (getDino().getDinoCollisionShape().intersects(e.getCollision())) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		getGameObj().clear();
		getGameObj().add(createEnemy());
	}

	public Rectangle getCollision() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getCollision'");
	}

	public boolean isOutOfScreen() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isOutOfScreen'");
	}

	// Getters and setters
	
	private BufferedImage getCactus1() {
		return cactus1;
	}

	private void setCactus1(BufferedImage cactus1) {
		this.cactus1 = cactus1;
	}

	private BufferedImage getCactus2() {
		return cactus2;
	}

	private void setCactus2(BufferedImage cactus2) {
		this.cactus2 = cactus2;
	}

	private BufferedImage getPterosaurs() {
		return pterosaurs;
	}

	private void setPterosaurs(BufferedImage pterosaurs) {
		this.pterosaurs = pterosaurs;
	}

	private Random getRand() {
		return rand;
	}

	private void setRand(Random rand) {
		this.rand = rand;
	}

	private List<GameObj> getGameObj() {
		return gameObj;
	}

	private void setGameObj(List<GameObj> gameObj) {
		this.gameObj = gameObj;
	}

	private ScoringSystem getScoringSystem() {
		return scoringSystem;
	}

	private void setScoringSystem(ScoringSystem scoringSystem) {
		this.scoringSystem = scoringSystem;
	}
}