package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Handler.Resource;
import UserInterface.GameScreen;

public class EnemyManager {
    private List<Enemy> enemy;
    private Random random;

    private BufferedImage imageCactus1, imageCactus2;
    private Dino dino;
    private GameScreen gameScreen;

    public EnemyManager(Dino dino, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.dino = dino;
        enemy = new ArrayList<Enemy>();
        imageCactus1 = Resource.getResourceImage("data/cactus1.png");
        imageCactus2 = Resource.getResourceImage("data/cactus2.png");
        random = new Random();
        enemy.add(getRandomCactus());
    }

    public void update() {
        for (Enemy enemy : enemy) {
            enemy.update();
            if (enemy.isOver() && !enemy.isScored()) {
                gameScreen.plusScore(20);
                enemy.setIsScored(true);
            }
            if (enemy.getCollisionShape().intersects(dino.getCollisionShape())) {
                dino.setAlive(false);
            }
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

    public void reset() {
        enemy.clear();
        enemy.add(getRandomCactus());
    }

    private Cactus getRandomCactus() {
        Cactus cactus;
        cactus = new Cactus(dino);

        cactus.setXPosition(600);
        if (random.nextBoolean()) {
            cactus.setImage(imageCactus1);
        } else {
            cactus.setImage(imageCactus2);
            cactus.setYPosition(75);
        }
        return cactus;
    }
}
