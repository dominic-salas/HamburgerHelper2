package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;

//lasershotgun lmao

public class LaserShotGun extends Weapon {
    private double bulletSpeed=10;
    static Image laserImage = new Image("laser.png");

    public LaserShotGun(Group root) {
        super(root);
    }

    @Override
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        Laser laser1 = new Laser(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
        projectiles.add(laser1);
        addBullets(slope);

    }
    private void addBullets(Point2D slope){
        Point2D perpSlope = new Point2D(-slope.getY(),slope.getX());
        Point2D origin1 = new Point2D(HamburgerHelper.xpos+105 +perpSlope.getX()*5,HamburgerHelper.ypos+105+perpSlope.getY()*5);
        Point2D origin2 = new Point2D(HamburgerHelper.xpos+105 +perpSlope.getX()*-5,HamburgerHelper.ypos+105+perpSlope.getY()*-5);

        Laser laser2 = new Laser(origin1.getX(),origin1.getY(),slope.getX(),slope.getY(),root);
        Laser laser3 = new Laser(origin2.getX(),origin2.getY(),slope.getX(),slope.getY(),root);
        projectiles.add(laser2);
        projectiles.add(laser3);
    }
}
