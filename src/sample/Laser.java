package sample;

import javafx.scene.Group;


/**
 * projectile extension with medium bullet damage,small hitbox, a redish cube image
 * can travel through obstacles
 * By David Rogers
 */

public class Laser extends Projectile implements Spawnable {
    public Group root;

    public Laser(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        super(xpos, ypos, xSpeed, ySpeed, root);
        this.root = root;
        sprite.setImage(LaserGun.laserImg);
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setFitHeight(10);
        sprite.setFitWidth(10);
        spawn(sprite, root);

        damage = 2;
    }

}
