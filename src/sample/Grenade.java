package sample;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;

/**
 * grenade projectile spawned by RPG
 * High damage and small hitbox
 * spawns explosion projectile when it intersects with either enemy or obstacle
 * By David Rogers
 */
public class Grenade extends Projectile implements Spawnable {

    public Grenade(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        super(xpos, ypos, xSpeed, ySpeed, root);
        this.root = root;
        sprite.setImage(RPG.grenadeImage);
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setFitHeight(30);
        sprite.setFitWidth(30);
        spawn(sprite, root);
        damage = 10;

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
     * if grenade intersects with obstacle, trigger explosion and remove surrounding enemies
     */
    public void checkObstacleIntersect(){
        MapMaker.obstacles.forEach(obstacle -> {
            if (sprite.getBoundsInParent().intersects(obstacle.sprite.getBoundsInParent())) {
                this.sprite.setFitHeight(100);
                this.sprite.setFitWidth(100);
                explode();
                despawn();
                this.timeline.stop();
                Weapon.projectiles.remove(this);
                return;
            }
        });

    }

    /**
     * spawn explosion projectile
     * despawn self and stop timeline
     */
    public void explode(){
        Weapon.projectiles.add(new Explosion(xpos,ypos,0,0,root));
        despawn();
        this.timeline.stop();
        Weapon.projectiles.remove(this);
    }
}

