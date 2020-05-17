package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.util.Random;

import javafx.util.Duration;

import java.util.ArrayList;

/**
 * EnemyFactory spawns different types of enemies in different places.
 */
public class EnemyFactory {
    static Image basicImage = new Image("Grimace.png");
    static Image silentImageClosed = new Image("hamburglar_closed.png");
    static Image silentImageOpen = new Image("hamburglar_open.png");
    static Image fatImage = new Image("fatEnemy.png");

    public int lives;
    public double ySpeed;
    public double xSpeed;
    private Timeline timeline;
    Random rand = new Random();
    static ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyFactory(Group root, HamburgerHelper handy, ScoreManager scoreManager) {
        spawnInit(root, handy, scoreManager);
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        for (int i = 0; i < enemies.size(); i++) {
                            if (enemies.get(i).isDead) {
                                spawnNew(root, handy, scoreManager);
                                enemies.remove(i);
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * spawns a new enemy at a random location between 0-200 and 400-600
     *
     * @param root  to add to group
     * @param handy to chase handy
     */
    public void spawnNew(Group root, HamburgerHelper handy, ScoreManager scoreManager) {
        int randomup = 0;
        int randomright = 0;
        if (rand.nextInt(2) == 1) {
            randomup = 500;
        }
        if (rand.nextInt(2) == 1) {
            randomright = 500;
        }
        int xrand = rand.nextInt(100) + randomup;
        int yrand = rand.nextInt(100) + randomright;
        switch (rand.nextInt(5)) {
            case 0:
            case 1:
                enemies.add(new BasicEnemy(root, handy, xrand, yrand, scoreManager));
                break;
            case 2:
            case 3:
                enemies.add(new SilentEnemy(root, handy, xrand, yrand, scoreManager));
                break;
            case 4:
                enemies.add(new FatEnemy(root, handy, xrand, yrand, scoreManager));
                break;
        }
    }

    /**
     * initially spawns 4 enemies at the start of the game
     *
     * @param root  to add to group
     * @param handy to chase handy
     */
    private void spawnInit(Group root, HamburgerHelper handy, ScoreManager scoreManager) {
        for (int i = 0; i < 5; i++) {
            spawnNew(root, handy, scoreManager);
        }
    }
}
