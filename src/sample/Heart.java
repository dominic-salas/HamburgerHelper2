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
 * Heart collectible that gives handy an extra life
 * doesn't redirect handy to the "death screen" when he dies, instead
 * gets a prompt to respawn with enter key
 * <p>
 * by Dominic Salas
 */
public class Heart extends PowerUp {
    public Timeline timeline = new Timeline();

    /**
     * Heart class to initialize all variables of heart
     * @param root to add heart to group
     * @param handy to know when heart is collected by handy
     * @param xpos to know where to spawn heart
     * @param ypos to know where to spawn heart
     */
    public Heart(Group root, HamburgerHelper handy, double xpos, double ypos) {
        hitbox = new Rectangle();
        this.xpos = xpos;
        this.ypos = ypos;
        sprite = new ImageView();
        sprite.setX(xpos);
        sprite.setY(ypos);
        hitbox.setX(xpos);
        hitbox.setY(ypos);
        sprite.setFitWidth(20);
        sprite.setFitHeight(20);
        hitbox.setWidth(15.09);
        hitbox.setHeight(20);
        sprite.setImage(PowerUpFactory.heartImg);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !HamburgerHelper.dead) {
                            try {
                                if (hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) {
                                    PowerUpFactory.secondLife = true;
                                    despawn(root, Heart.this);
                                }
                                convertMotion();
                            } catch (java.lang.NullPointerException e) {
                                despawn(root, Heart.this);
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }
}
