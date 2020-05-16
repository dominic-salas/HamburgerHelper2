package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LaserGun extends Weapon {
    public ImageView sprite;
    private Image weaponImage;
    public double spawnChance;
    public double bulletSpeed = 10;
    static Image laserImg = new Image("Resources/laser.png");

    public LaserGun(Group root) {
        super(root);
    }

    @Override
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        Laser laser = new Laser(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
        projectiles.add(laser);
    }


    public void pickup() {
    }

}
