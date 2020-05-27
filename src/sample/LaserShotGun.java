package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;


/**
 * Semi-Auto weapon that spawns 3 laser projectiles with medium bullet speed
 */
public class LaserShotGun extends Weapon {
    private double bulletSpeed=8;
    static Image laserImage = new Image("laser.png");

    public LaserShotGun(Group root) {
        super(root);
    }

    /**
     * generates slope from bulletspeed
     * spawns 3 lasers using addBullets method
     * adds them to projectiles arraylist
     */
    @Override
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        Laser laser1 = new Laser(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
        projectiles.add(laser1);
        addBullets(slope);

    }
    /**
     * spawns two additional lasers along the perpendicular axis to trajectory as bullet
     * essentially shoots all three lasers in a line
     * @param slope
     */
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
