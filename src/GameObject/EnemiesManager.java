package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Handler.Resource;
import Handler.ScoringSystem;

public class EnemiesManager {

	private BufferedImage cactus1;
	private BufferedImage cactus2;
	private BufferedImage pterosaurs;
	private Random rand;

	private List<Enemy> enemies;
	private Dino dino;
	private ScoringSystem scoringSystem;

	public EnemiesManager(Dino dino, ScoringSystem scoringSystem) {
		rand = new Random();
		cactus1 = Resource.getResourceImage("data/cactus1.png");
		cactus2 = Resource.getResourceImage("data/cactus2.png");
		pterosaurs = Resource.getResourceImage("data/pterosaur1.png");
		enemies = new ArrayList<>();
		this.dino = dino;
		this.scoringSystem = scoringSystem;
		enemies.add(createEnemy());
	}

	public void update() {
		for (Enemy e : getEnemies()) {
			e.update();
		}
		Enemy enemy = enemies.get(0);
		if (enemy.isOutOfScreen()) {
			scoringSystem.increaseScore(10);
			enemies.clear();
			enemies.add(createEnemy());
		}
	}

	public void draw(Graphics g) {
		for (Enemy e : getEnemies()) {
			e.draw(g);
		}
	}

	private Enemy createEnemy() {
		int type = rand.nextInt(4); // Updated to include pterosaurs
		int xPos = rand.nextInt(300) + 800;
		int yPos;
		boolean isSpawnHigh = rand.nextBoolean();
		if (isSpawnHigh) {
			yPos = 95;
		} else
			yPos = 120;

		if (type == 0) {
			return new Cactus(getDino(), xPos, cactus1.getWidth() - 10, cactus1.getHeight() - 10, getCactus1());
		} else if (type == 1) {
			return new Cactus(getDino(), xPos, cactus2.getWidth() - 10, cactus2.getHeight() - 10, getCactus2());
		} else {
			return new Pterosaurs(getDino(), xPos, yPos, pterosaurs.getWidth() - 10, pterosaurs.getHeight() - 10,
					getPterosaurs());
		}
	}

	public boolean isCollide() {
		for (Enemy e : getEnemies()) {
			if (dino.getDinoCollisionShape().intersects(e.getCollision())) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		enemies.clear();
		enemies.add(createEnemy());
	}

	public BufferedImage getCactus1() {
		return cactus1;
	}

	public BufferedImage getCactus2() {
		return cactus2;
	}

	public BufferedImage getPterosaurs() {
		return pterosaurs;
	}

	public Dino getDino() {
		return dino;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
}
