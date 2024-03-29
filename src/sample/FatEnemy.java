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


import static sample.EnemyFactory.fatImage;

/**
 * FatEnemy that has a lot of health but is very slow. Enemy takes off a lot of damage.
 * by Dominic Salas
 */
public class FatEnemy extends Enemy {
    public Timeline timeline = new Timeline();

    /**
     * FatEnemy object that initializes all of the sprite, hitbox and lives properties.
     * timeline behaves completely similar to BasicEnemy
     *
     * @param root  to print object to group
     * @param handy to know what registers a hit to handy
     * @param xpos  where to spawn on x axis
     * @param ypos  where to spawn on y axis
     */
    public FatEnemy(Group root, HamburgerHelper handy, double xpos, double ypos, ScoreManager scoreManager) {
        scoreAdd = 300;
        lives = 10;
        damage = 20;
        speed = 0.5;
        sprite = new ImageView();
        sprite.setFitWidth(68);
        sprite.setFitHeight(68);
        this.xpos = xpos;
        this.ypos = ypos;
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setImage(fatImage);
        hitbox = new Rectangle(sprite.getX() + 10, sprite.getY() + 4, 51.5, 63);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !handy.dead) {
                            try {
                                updateEnemy(FatEnemy.this, root, scoreManager, handy, 10, 4);
                            } catch (java.lang.NullPointerException ignore) {
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }
}
