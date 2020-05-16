package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * child of weapon abstract class
 * spawns laser projectile with relatively high bullet speed
 * also used to store laser.png for spawning efficiency
 * By David Rogers
 */
public class LaserGun extends Weapon {
    public ImageView sprite;
    private Image weaponImage;
    public double spawnChance;
    public double bulletSpeed = 10;
    static Image laserImg = new Image("laser.png");

    public LaserGun(Group root) {
        super(root);
    }

    /**
     * same as other shoot methods, just spawns laser projectile instead
     * generates slope
     * spawns laser at handy location with returned slope
     * adds new laser to projectile arraylist in weapon superclass
     */
    @Override
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        Laser laser = new Laser(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
        projectiles.add(laser);
    }


    public void pickup() {
    }

}
