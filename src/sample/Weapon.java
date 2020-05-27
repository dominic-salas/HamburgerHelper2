package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;

import java.util.ArrayList;

/**
 * abstract class extended by all weapon types
 * has universal slope generator used by all children
 * universal arraylist of all active projectiles
 * By David Rogers
 */
public abstract class Weapon {
    protected Group root;
    static ArrayList<Projectile> projectiles = new ArrayList<>();

    public Weapon(Group root) {
        this.root = root;

    }

    /**
     * creates x speed and y speed for projectile to follow to clicked target
     * called by children when overriding shoot
     * maintains constant velocity no matter how far away is clicked, or what x to y ratio for clicked location
     * starts with delta in x and y between handy and clicked location
     * assures constant speed by matching hypotenuse length of slope to bulletspeed
     * maintains x to y ratio for trajectory when adjusting speed
     *
     * @param projectileSpeed
     * @return
     */
    protected Point2D slopeGenerator(double projectileSpeed) {
        double xdiff = (UserInput.mousePosX-(HamburgerHelper.xpos+115));
        double ydiff = (UserInput.mousePosY-(HamburgerHelper.ypos+142));
        double slopeMultiplier = (projectileSpeed)/Math.sqrt(Math.pow(xdiff,2)+Math.pow(ydiff,2));
        xdiff=xdiff*slopeMultiplier;
        ydiff=ydiff*slopeMultiplier;
        return new Point2D(xdiff,ydiff);
    }

    public void shoot() {}
}
