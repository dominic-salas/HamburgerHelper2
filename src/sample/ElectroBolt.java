package sample;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;

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
                        timetracker+=.008;
                        if (timetracker>.5){
                            removeSelf();
                        }

                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    private void removeSelf() {
        despawn();
        this.timeline.stop();
        Weapon.projectiles.remove(this);
    }
}
