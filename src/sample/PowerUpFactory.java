package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

/**
 * Spawns powerups.
 * by Dominic Salas
 */
public class PowerUpFactory {
    private Timeline timeline;
    static Image bombImg = new Image("bomb.png");
    static Image coinImg = new Image("coin.png");
    static Image shieldImg = new Image("shield.png");
    static Image speedImg = new Image("speed.png");
    static Image heartImg = new Image("heart.png");
    static ArrayList<PowerUp> powerUps = new ArrayList<>();
    static Rectangle powerCheck = new Rectangle();
    static boolean powerCheckValid = false;
    static boolean secondLife = false;
    int coinCounter = 0;
    int shieldCounter = 0;
    int speedCounter = 0;
    int bombCounter = 0;
    int heartCounter = 0;
    Random rand = new Random();

    /**
     * PowerUpFactory class to initialize timeline and to handle all powerups
     *
     * @param root         to spawn powerups to group
     * @param handy        to pass in handy to all powerups
     * @param scoreManager for certain powerups (bomb)
     * @param enemyFactory for certain powerups (bomb)
     */
    public PowerUpFactory(Group root, HamburgerHelper handy, ScoreManager scoreManager, EnemyFactory enemyFactory) {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    /**
                     * timeline that loops and increases an integer counter for each powerup
                     * when the counter hits a certain number, and then counter resets
                     * used to simulate the "rarity" of certain power ups
                     */
                    public void handle(ActionEvent event) {
                        if (!HamburgerHelper.dead) {
                            coinCounter++;
                            speedCounter++;
                            shieldCounter++;
                            bombCounter++;
                            heartCounter++;
                            if (coinCounter >= 250) {
                                if (spawnNew(root, handy, "coin", scoreManager, enemyFactory)) {
                                    coinCounter = 0;
                                }
                            }
                            if (speedCounter >= 1250) {
                                if (spawnNew(root, handy, "speed", scoreManager, enemyFactory)) {
                                    speedCounter = 0;
                                }
                            }
                            if (shieldCounter >= 2250) {
                                if (spawnNew(root, handy, "shield", scoreManager, enemyFactory)) {
                                    shieldCounter = 0;
                                }
                            }
                            if (bombCounter >= 2625) {
                                if (spawnNew(root, handy, "bomb", scoreManager, enemyFactory)) {
                                    bombCounter = 0;
                                }
                            }
                            if (heartCounter >= 3000 && !secondLife) {
                                if (spawnNew(root, handy, "heart", scoreManager, enemyFactory)) {
                                    heartCounter = 0;
                                }
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * spawns a new powerup.
     *
     * @param root         to spawn powerup to group
     * @param handy        to make sure powerups are taken
     * @param type         for the type of powerup
     * @param scoreManager to pass into bomb
     * @param enemyFactory to pass into bomb
     * @return if the power up was successfully spawned.
     */
    public boolean spawnNew(Group root, HamburgerHelper handy, String type, ScoreManager scoreManager, EnemyFactory enemyFactory) {
        powerCheck.setWidth(30);
        powerCheck.setHeight(30);
        if (powerCheckValid) {
            switch (type) {
                case "coin":
                    powerUps.add(new Coin(root, handy, MapMaker.possibleX, MapMaker.possibleY));
                    powerCheckValid = false;
                    return true;
                case "shield":
                    powerUps.add(new Shield(root, handy, MapMaker.possibleX, MapMaker.possibleY));
                    powerCheckValid = false;
                    return true;
                case "speed":
                    powerUps.add(new Speed(root, handy, MapMaker.possibleX, MapMaker.possibleY));
                    powerCheckValid = false;
                    return true;
                case "bomb":
                    powerUps.add(new Bomb(root, handy, MapMaker.possibleX, MapMaker.possibleY, scoreManager, enemyFactory));
                    powerCheckValid = false;
                    return true;
                case "heart":
                    powerUps.add(new Heart(root, handy, MapMaker.possibleX, MapMaker.possibleY));
                    powerCheckValid = false;
                    return true;
            }
        }
        return false;
    }
}
