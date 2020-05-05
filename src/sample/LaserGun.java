package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LaserGun extends Weapon {
    public ImageView sprite;
    private Image weaponImage;
    public Bullet bulletType;
    public double spawnChance;
    public double bulletSpeed;

    @Override
    public void spawn(ImageView sprite, Group root) {
    }

    private void shoot() {
    }

    private void pickup() {
    }

}
