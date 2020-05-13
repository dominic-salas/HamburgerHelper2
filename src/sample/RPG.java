package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RPG extends Weapon {
    public ImageView sprite;
    private Image weaponImage;
    public Bullet bulletType;
    public double spawnChance;
    public double bulletSpeed;

    public RPG(Group root) {
        super(root);
    }


    public void spawn(ImageView sprite, Group root) {
    }

    public void shoot() {
    }

    public void pickup() {
    }

}
