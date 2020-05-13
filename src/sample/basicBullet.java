package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class basicBullet extends Projectile{
    public Group root;

    public basicBullet(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        super(xpos, ypos, xSpeed, ySpeed,root);
        this.root=root;
        Image image = new Image("Resources/projectile.png");
        sprite.setImage(image);
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setFitHeight(20);
        sprite.setFitWidth(20);
        spawn(sprite,root);
    }
}
