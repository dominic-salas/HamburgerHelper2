package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * basic semi-auto weapon with low bullet speed, that spawns basicBullet
 * stores projectile.png for more efficient projectile spawning
 * By David Rogers
 */
public class BasicGun extends Weapon {
    public ImageView sprite;
    private Image weaponImage;
    public double spawnChance;
    public double bulletSpeed = 5;
    static Image basicBulletImg = new Image("projectile.png");
    public BasicGun(Group root) {
        super(root);
    }

    /**
     *generates slope from bulletspeed
     * spawn new basicBullet projectile at that location with slope as x and y speeds
     * add basicbullet to projectiles arraylist
     */
    @Override
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        basicBullet basicBullet = new basicBullet(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
        projectiles.add(basicBullet);
    }


    private void pickup() {
    }

}
