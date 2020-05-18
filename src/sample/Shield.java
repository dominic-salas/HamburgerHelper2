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


public class Shield extends PowerUp {
    public Timeline timeline = new Timeline();
    boolean activated = false;
    int counter = 0;

    public Shield(Group root, HamburgerHelper handy, double xpos, double ypos) {

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
        sprite.setImage(PowerUpFactory.shieldImg);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !HamburgerHelper.dead) {
                            try {
                                if (hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent()) && !activated) {
                                    HamburgerHelper.lives += 1000;
                                    activated = true;
                                    despawn(root, Shield.this);
                                }
                                if (activated) {
                                    counter++;
                                }
                                if (counter >= 500) {
                                    HamburgerHelper.lives -= 1000;
                                    PowerUpFactory.powerUps.remove(Shield.this);
                                    sprite = null;
                                    hitbox = null;
                                    activated = false;
                                }
                                convertMotion();
                            } catch (java.lang.NullPointerException e) {
                                despawn(root, Shield.this);
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
