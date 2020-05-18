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


public class Coin extends PowerUp {
    public Timeline timeline = new Timeline();

    public Coin(Group root, HamburgerHelper handy, ScoreManager scoreManager, double xpos, double ypos) {
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

    /**
     * makes sure PowerUp moves with the map
     */
    public void convertMotion() {
        xpos -= Obstacle.xSpeed;
        ypos -= Obstacle.ySpeed;
        sprite.setX(xpos);
        sprite.setY(ypos);
        sprite.relocate(xpos, ypos);
        hitbox.setX(xpos);
        hitbox.setY(ypos);
        hitbox.relocate(xpos, ypos);
    }

    public void despawn(Group root, PowerUp powerUp) {
        root.getChildren().remove(powerUp.sprite);
        root.getChildren().remove(powerUp.hitbox);
        PowerUpFactory.powerUps.remove(powerUp);
        sprite = null;
        hitbox = null;
    }

    private void pickup() {
    }

    private void special() {
    }
}
