package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicGun extends Weapon {
    public ImageView sprite;
    private Image weaponImage;
    public Bullet bulletType;
    public double spawnChance;
    public double bulletSpeed=5;

    public BasicGun(Group root) {
        super(root);
    }

    @Override
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        basicBullet basicBullet = new basicBullet(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
    }


    private void pickup() {
    }

}
