package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

import static sample.EnemyFactory.basicImage;

/**
 * BasicEnemy object that spawns in a standard enemy with 15 health.
 * by Dominic Salas
 */
public class BasicEnemy extends Enemy {
    public Timeline timeline = new Timeline();

    /**
     * BasicEnemy object that initializes all of the sprite, hitbox and lives properties.
     * Also includes a timeline to constantly check collisions and if it is dead
     *
     * @param root  to print object to group
     * @param handy to know what registers a hit to handy
     * @param xpos  where to spawn on x axis
     * @param ypos  where to spawn on y axis
     */
    public BasicEnemy(Group root, HamburgerHelper handy, double xpos, double ypos, ScoreManager scoreManager) {
        scoreAdd=100;
        lives = 3;
        damage = 5;
        speed = 1;
        sprite = new ImageView();
        sprite.setFitWidth(67.158);
        sprite.setFitHeight(45.162);
        this.xpos = xpos;
        this.ypos = ypos;
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setImage(basicImage);
        hitbox = new Rectangle(sprite.getX() + 12.5, sprite.getY() + 1, 45.5, 43);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !handy.dead) {
                            try {
                                chase(speed);
                                for (int i = 0; i < Weapon.projectiles.size(); i++) {
                                    if (hitbox.getBoundsInParent().intersects(Weapon.projectiles.get(i).sprite.getBoundsInParent())) {
                                        dropHealth(Weapon.projectiles.get(i).damage, root, BasicEnemy.this, true, scoreManager);
                                        Weapon.projectiles.get(i).despawn();
                                    }
                                }
                                checkAttack(handy, root, BasicEnemy.this, scoreManager);
                                xSpeed = 0;
                                ySpeed = 0;
                                hitbox.setX(sprite.getX() + 12.5);
                                hitbox.setY(sprite.getY() + 1);
                                hitbox.relocate(sprite.getX() + 12.5, sprite.getY() + 1);
                                checkBounds(root, BasicEnemy.this, scoreManager);
                            } catch (java.lang.NullPointerException ignore) {
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * makes sure BasicEnemy moves with the map
     */
    public void convertMotion() {
        xpos -= Obstacle.xSpeed;
        ypos -= Obstacle.ySpeed;
    }

    /**
     * chases "handy"'s position, at 237.5, 237.5
     */
    private void chase(double speed) {
        convertMotion();
        if (sprite.getX() - 16.7895 < 237.5) {
            xSpeed += speed;
        } else if (sprite.getX() - 16.7895 > 237.5) {
            xSpeed -= speed;
        }
        if (sprite.getY() - 22.581 < 217.5) {
            ySpeed += speed;
        } else if (sprite.getY() - 22.581 > 217.5) {
            ySpeed -= speed;
        }

        xpos += xSpeed;
        ypos += ySpeed;
        sprite.setX(xpos);
        sprite.setY(ypos);
        sprite.relocate(xpos, ypos);
    }


}
