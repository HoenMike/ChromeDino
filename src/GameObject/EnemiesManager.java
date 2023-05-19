package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Handler.Resource;

public class EnemiesManager {

	private BufferedImage cactus1;
	private BufferedImage cactus2;
	private Random rand;

	private List<Enemy> enemies;
	private Dino dino;

	public EnemiesManager(Dino dino) {
		rand = new Random();
		cactus1 = Resource.getResourceImage("data/cactus1.png");
		cactus2 = Resource.getResourceImage("data/cactus2.png");
		enemies = new ArrayList<Enemy>();
		this.dino = dino;
		enemies.add(createCactus());
	}

	public void update() {
		for (Enemy e : getEnemies()) {
			e.update();
		}
		Enemy enemy = enemies.get(0);
		if (enemy.isOutOfScreen()) {
			dino.upScore();
			enemies.clear();
			enemies.add(createCactus());
		}
	}

	public void draw(Graphics g) {
		for (Enemy e : getEnemies()) {
			e.draw(g);
		}
	}

	private Enemy createCactus() {
		int type = rand.nextInt(2);
		if (type == 0) {
			return new Cactus(getDino(), 800, cactus1.getWidth() - 10, cactus1.getHeight() - 10, getCactus1());
		} else {
			return new Cactus(getDino(), 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, getCactus2());
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
		enemies.add(createCactus());
	}

	public BufferedImage getCactus1() {
		return cactus1;
	}

	public BufferedImage getCactus2() {
		return cactus2;
	}

	public Dino getDino() {
		return dino;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

}
