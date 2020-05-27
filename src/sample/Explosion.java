package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * secondary projectile that is spawned at the location of a grenade being detonated
 * large hitbox and high damage
 * no movement
 * lasts for 2 seconds, while playing gif through animation of explosion frames
 * By David Rogers
 */
public class Explosion extends Projectile implements Spawnable {
    Circle hitbox = new Circle();
    int frameCounter =0;
    public Explosion(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        super(xpos-50, ypos-50, xSpeed, ySpeed, root);

        this.root = root;
        sprite.setImage(RPG.images[0]);
        sprite.setY(ypos-50);
        sprite.setX(xpos-50);
        sprite.setFitHeight(100);
        sprite.setFitWidth(100);
        spawn(sprite, root);
        hitbox.setRadius(50);
        hitbox.setCenterX(xpos);
        hitbox.setCenterY(ypos);
        damage = 10;
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame action = new KeyFrame(Duration.seconds(.05),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) { //cycle through explosion frames until there are no frames left, then despawn
                        frameCounter++;
                        sprite.setImage(RPG.images[frameCounter]);
                        if(frameCounter>=11){
                            timeline.stop();
                            despawn();
                            Weapon.projectiles.remove(this);
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }
}
