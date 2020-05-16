package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Laser extends Projectile implements Spawnable {
    public Group root;

    public Laser(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        super(xpos, ypos, xSpeed, ySpeed, root);
        this.root = root;
        sprite.setImage(LaserGun.laserImg);
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setFitHeight(20);
        sprite.setFitWidth(20);
        spawn(sprite, root);

        damage = 1;
    }

}
