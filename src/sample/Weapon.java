package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Weapon {
    protected Group root;
    static Image basicBulletImg = new Image("projectile.png");
    static ArrayList<Projectile> projectiles = new ArrayList<>();

    public Weapon(Group root) {
        this.root = root;

    }

    public void shoot() {
    }
    protected Point2D slopeGenerator(double projectileSpeed) {
        double xdiff = (UserInput.mousePosX-(HamburgerHelper.xpos+115));
        double ydiff = (UserInput.mousePosY-(HamburgerHelper.ypos+142));
        double slopeMultiplier = (projectileSpeed)/Math.sqrt(Math.pow(xdiff,2)+Math.pow(ydiff,2));
        xdiff=xdiff*slopeMultiplier;
        ydiff=ydiff*slopeMultiplier;
        return new Point2D(xdiff,ydiff);
    }


    private void pickup() {
    }
}
