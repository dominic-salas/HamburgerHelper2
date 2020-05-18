package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static sample.EnemyFactory.enemies;


public class Bomb extends PowerUp {
    public Timeline timeline = new Timeline();
    static boolean activated = false;
    int counter = 0;

    public Bomb(Group root, HamburgerHelper handy, double xpos, double ypos, ScoreManager scoreManager, EnemyFactory enemyFactory) {
        hitbox = new Rectangle();
        this.xpos = xpos;
        this.ypos = ypos;
        sprite = new ImageView();
        sprite.setX(xpos);
        sprite.setY(ypos);
        hitbox.setX(xpos);
        hitbox.setY(ypos);
        sprite.setFitWidth(15.09);
        sprite.setFitHeight(20);
        hitbox.setWidth(15.09);
        hitbox.setHeight(20);
        sprite.setImage(PowerUpFactory.bombImg);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !HamburgerHelper.dead) {
                            try {
                                if (hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) {
                                    activated = true;
                                    for (int i = 0; i < enemies.size(); i++) {
                                        enemies.get(i).lives = 0;
                                        enemies.get(i).die(root, enemies.get(i), false, scoreManager);
                                        enemies.remove(i);
                                    }
                                    despawn(root, Bomb.this);
                                }
                                if (activated) {
                                    counter++;
                                }
                                if (counter >= 750) {
                                    enemyFactory.spawnInit(root, handy, scoreManager);
                                    PowerUpFactory.powerUps.remove(Bomb.this);
                                    sprite = null;
                                    hitbox = null;
                                    activated = false;
                                }
                                convertMotion();
                            } catch (java.lang.NullPointerException e) {
                                despawn(root, Bomb.this);
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    @Override
    public void despawn(Group root, PowerUp powerUp) {
        root.getChildren().remove(powerUp.sprite);
        root.getChildren().remove(powerUp.hitbox);
    }
}
