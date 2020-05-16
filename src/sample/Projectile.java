package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * projectile object that is spawned by weapon, and then managed by itself until it either collides with something or moves off map
 * then despawns
 * each projectile child has unique bullet damage, and image, and other special abilities
 * By David Rogers
 */
public abstract class Projectile implements Spawnable {
    double xpos;
    double ypos;
    double xSpeed;
    double ySpeed;
    public ImageView sprite;
    public int damage;
    protected Timeline timeline;
    protected Group root;

    public Projectile(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.root = root;
        sprite = new ImageView();
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        move();
                        checkDespawn();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * takes in slope when spawned, and then follows x and y speed until despawned
     */
    public void move() {  //TODO movement also has to also include relative handy movement
        xpos += xSpeed;
        ypos += ySpeed;
        sprite.relocate(xpos, ypos);
    }

    /**
     * remove if its gone off the map
     */
    private void checkDespawn() {
        if (xpos > 700 || xpos < -100 || ypos > 700 | ypos < -100) {
            despawn();
        }
    }

    /**
     * implementation of spawnable
     * removes from root, stops timeline, and removes from projectiles arraylist
     */
    public void despawn() {
        despawn(sprite, root);
        timeline.stop();
        Weapon.projectiles.remove(this);
    }
}
