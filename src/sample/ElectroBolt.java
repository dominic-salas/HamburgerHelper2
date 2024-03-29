package sample;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;

/**
 * low damage projectile with no bullet speed
 * spawned by electroGun
 * despawns after half a second
 * By David Rogers
 */
public class ElectroBolt extends Projectile implements Spawnable {
    private double timetracker=0;
    public ElectroBolt(double xpos, double ypos, Group root) {
        super(xpos, ypos, 0, 0, root);
        this.root = root;
        sprite.setImage(ElectroGun.boltImage);
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setFitHeight(10);
        sprite.setFitWidth(10);
        spawn(sprite, root);
        damage = 1;

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        timetracker+=.008; //despawn after half a second
                        if (timetracker>.5){
                            removeSelf();
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    private void removeSelf() { //despawns self if half second has elapsed and has not intersected with an enemy yet
        despawn();
        this.timeline.stop();
        Weapon.projectiles.remove(this);
    }
}
