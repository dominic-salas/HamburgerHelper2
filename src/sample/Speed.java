package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Speed power up that makes handy move 1.5 times as fast
 * by Dominic Salas
 */
public class Speed extends PowerUp {
    public Timeline timeline = new Timeline();
    boolean activated = false;
    int counter = 0;

    /**
     * Speed class for initializing variables
     *
     * @param root  to spawn to group
     * @param handy to check for collisions
     * @param xpos  to spawn at a certain spot
     * @param ypos  to spawn at a certain spot
     */
    public Speed(Group root, HamburgerHelper handy, double xpos, double ypos) {
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
        sprite.setImage(PowerUpFactory.speedImg);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    /**
                     * timeline that handles the amount of time that this effect should be applied.
                     */
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !HamburgerHelper.dead) {
                            try {
                                if (hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) {
                                    activated = true;
                                    despawn(root, Speed.this);
                                }
                                if (activated) {
                                    counter++;
                                    HamburgerHelper.speed = 6;
                                }
                                if (counter >= 625) {
                                    HamburgerHelper.speed = 4;
                                    PowerUpFactory.powerUps.remove(Speed.this);
                                    sprite = null;
                                    hitbox = null;
                                    activated = false;
                                }
                                convertMotion();
                            } catch (java.lang.NullPointerException e) {
                                despawn(root, Speed.this);
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
