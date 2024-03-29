package sample;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;

/**
 * bullet projectile
 * low damage, large hitbox, stopped by obstacles
 * has internal timeline for managing obstacle intersections
 * By David Rogers
 */

public class basicBullet extends Projectile{
    public Group root;

    public basicBullet(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        super(xpos, ypos, xSpeed, ySpeed, root);
        this.root = root;
        sprite.setImage(BasicGun.basicBulletImg);
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setFitHeight(20);
        sprite.setFitWidth(20);
        spawn(sprite, root);
        damage = 1;

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        checkObstacleIntersect();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * if it collides with obstacle, remove from root, stop timeline, remove from projectiles arraylist
     */
    public void checkObstacleIntersect(){
        MapMaker.obstacles.forEach(obstacle -> {
            if(sprite.getBoundsInParent().intersects(obstacle.sprite.getBoundsInParent())){
                despawn();
                this.timeline.stop();
                Weapon.projectiles.remove(this);
            }
        });

    }
}
