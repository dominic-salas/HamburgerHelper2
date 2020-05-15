package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;

public class ShotGun extends Weapon {
    private double bulletSpeed=4;
    public ShotGun(Group root) {
        super(root);
    }

    @Override
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        basicBullet basicBullet1 = new basicBullet(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
        projectiles.add(basicBullet1);
        addBullets(slope);

    }
    private void addBullets(Point2D slope){
        Point2D perpSlope = new Point2D(-slope.getY(),slope.getX());
        Point2D origin1 = new Point2D(HamburgerHelper.xpos+105 +perpSlope.getX()*10,HamburgerHelper.ypos+105+perpSlope.getY()*10);
        Point2D origin2 = new Point2D(HamburgerHelper.xpos+105 +perpSlope.getX()*-10,HamburgerHelper.ypos+105+perpSlope.getY()*-10);

        basicBullet basicBullet2 = new basicBullet(origin1.getX(),origin1.getY(),slope.getX(),slope.getY(),root);
        basicBullet basicBullet3 = new basicBullet(origin2.getX(),origin2.getY(),slope.getX(),slope.getY(),root);
        projectiles.add(basicBullet2);
        projectiles.add(basicBullet3);
    }
}
