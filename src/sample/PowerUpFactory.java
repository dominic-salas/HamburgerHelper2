package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class PowerUpFactory {
    private Timeline timeline;
    static Image bombImg = new Image("bomb.png");
    static Image coinImg = new Image("coin.png");
    static Image shieldImg = new Image("shield.png");
    static Image speedImg = new Image("speed.png");
    static ArrayList<PowerUp> powerUps = new ArrayList<>();
    static int spawnCounter = 0;
    Random rand = new Random();

    public PowerUpFactory(Group root, HamburgerHelper handy, ScoreManager scoreManager) {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (!HamburgerHelper.dead) {
                            spawnCounter++;
                            if (spawnCounter >= 1250) {
                                spawnNew(root, handy, scoreManager);
                                spawnCounter = 0;
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    public void spawnNew(Group root, HamburgerHelper handy, ScoreManager scoreManager) {
        powerUps.add(new Coin(root, handy, scoreManager, rand.nextInt(600), rand.nextInt(600)));
    }
}
