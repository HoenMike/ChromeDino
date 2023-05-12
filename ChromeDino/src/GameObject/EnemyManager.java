package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Handler.Resource;

public class EnemyManager {
    private List<Enemy> enemy;
    private Random random;
    private BufferedImage imageCactus1, imageCactus2;

    public EnemyManager() {
        enemy = new ArrayList<Enemy>();
        imageCactus1 = Resource.getResourceImage("data/cactus1.png");
        imageCactus2 = Resource.getResourceImage("data/cactus2.png");
        random = new Random();
        enemy.add(getRandomCactus());
    }

    public void update() {
        for (Enemy enemy : enemy) {
            enemy.update();
        }
        Enemy firstEnemy = enemy.get(0);
        if (enemy.get(0).isOutOfScreen()) {
            enemy.remove(firstEnemy);
            enemy.add(getRandomCactus());
        }
    }

    public void draw(Graphics g) {
        for (Enemy dangerObject : enemy) {
            dangerObject.draw(g);
        }
    }

    private Cactus getRandomCactus() {
        Cactus cactus;
        cactus = new Cactus();
        cactus.setXPosition(600);
        if (random.nextBoolean()) {
            cactus.setImage(imageCactus1);
        } else {
            cactus.setImage(imageCactus2);
        }
        return cactus;
    }
}
