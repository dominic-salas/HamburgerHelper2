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


}
