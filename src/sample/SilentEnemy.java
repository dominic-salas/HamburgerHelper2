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


import static sample.EnemyFactory.silentImageClosed;
import static sample.EnemyFactory.silentImageOpen;
import static sample.UserInput.mouseHeld;

/**
 * SilentEnemy is a very quick, low health enemy that attacks the player
 * only if the player shot his weapon.
 * by Dominic Salas
 */
public class SilentEnemy extends Enemy {
    public Timeline timeline = new Timeline();
    public boolean awake = false;

    /**
     * SilentEnemy object that initializes all of the sprite, hitbox and lives properties.
     * includes a timeline and manages the sprite's activites when the player shoots.
     *
     * @param root  to print object to group
     * @param handy to know what registers a hit to handy
     * @param xpos  where to spawn on x axis
     * @param ypos  where to spawn on y axis
     */
    public SilentEnemy(Group root, HamburgerHelper handy, double xpos, double ypos, ScoreManager scoreManager) {
        scoreAdd = 150;
        lives = 1;
        damage = 3;
        speed = 0;
        sprite = new ImageView();
        sprite.setFitWidth(40);
        sprite.setFitHeight(46.34);
        this.xpos = xpos;
        this.ypos = ypos;
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setImage(silentImageClosed);
        hitbox = new Rectangle(sprite.getX(), sprite.getY(), 40, 46.34);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null && !handy.dead) {
                            try {
                                checkSleeping();
                                if (awake) {
                                    speed = 2.75;
                                    root.getChildren().remove(sprite);
                                    sprite.setImage(silentImageOpen);
                                    root.getChildren().add(sprite);
                                }
                                chase(speed);
                                for (int i = 0; i < Weapon.projectiles.size(); i++) {
                                    if (hitbox.getBoundsInParent().intersects(Weapon.projectiles.get(i).sprite.getBoundsInParent())) {
                                        int damage = Weapon.projectiles.get(i).damage;
                                        Weapon.projectiles.get(i).despawn();
                                        dropHealth(damage, root, SilentEnemy.this, true, scoreManager);
                                    }
                                }
                                checkAttack(handy, root, SilentEnemy.this, scoreManager);
                                xSpeed = 0;
                                ySpeed = 0;
                                hitbox.setX(sprite.getX());
                                hitbox.setY(sprite.getY());
                                hitbox.relocate(sprite.getX(), sprite.getY());
                                checkBounds(root, SilentEnemy.this, scoreManager);
                            } catch (java.lang.NullPointerException ignore) {
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    private void checkSleeping() {
        if (mouseHeld && !awake) {
            awake = true;
        }
    }
}
