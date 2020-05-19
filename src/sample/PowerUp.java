package sample;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public abstract class PowerUp implements Spawnable {
    double xpos;
    double ypos;
    public ImageView sprite;
    public Rectangle hitbox;

    /**
     * checks if PowerUp hits handy
     *
     * @param handy to check if handy is hit
     */
    public boolean checkIntersect(HamburgerHelper handy) {
        return hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent());
    }

    /**
     * makes sure PowerUp moves with the map
     */
    public void convertMotion() {
        xpos -= Obstacle.xSpeed;
        ypos -= Obstacle.ySpeed;
        sprite.setX(xpos);
        sprite.setY(ypos);
        sprite.relocate(xpos, ypos);
        hitbox.setX(xpos);
        hitbox.setY(ypos);
        hitbox.relocate(xpos, ypos);
    }

    /**
     * despawns and removes all sprites and hitboxes of the power up
     *
     * @param root    to remove from group
     * @param powerUp to know which powerUp to remove
     */
    public void despawn(Group root, PowerUp powerUp) {
        root.getChildren().remove(powerUp.sprite);
        root.getChildren().remove(powerUp.hitbox);
        PowerUpFactory.powerUps.remove(powerUp);
        sprite = null;
        hitbox = null;
    }
}
