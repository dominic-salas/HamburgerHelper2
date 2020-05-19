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
 * Coin collectible that adds score when handy collects it.
 * by Dominic Salas
 */
public class Coin extends PowerUp {
    public Timeline timeline = new Timeline();

    /**
     * Coin class to initialize all of coin's variables
     *
     * @param root  to add coin to group
     * @param handy to know when handy "collects" the coin
     * @param xpos  to spawn coin in a certain spot
     * @param ypos  to spawn coin in a certain spot
     */
    public Coin(Group root, HamburgerHelper handy, double xpos, double ypos) {
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
        sprite.setImage(PowerUpFactory.coinImg);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !HamburgerHelper.dead) {
                            try {
                                if (hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) {
                                    ScoreManager.addScore(500);
                                    despawn(root, Coin.this);
                                }
                                convertMotion();
                            } catch (java.lang.NullPointerException e) {
                                despawn(root, Coin.this);
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }
}
