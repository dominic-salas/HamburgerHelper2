package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Weapon that spawns grenade.
 * Low bullet speed
 * Grenade is shot, when grenade collides with enemy, create explosion projectile and despawn grenade
 * stores grenade images so more efficient spawning of projectiles (dont have to load from disc every time)
 * By David Rogers
 */
public class RPG extends Weapon {
    public ImageView sprite;
    public static Image[] images = new Image[12];
    public static Image grenadeImage = new Image("grenade.png");
    public double bulletSpeed =3;

    public RPG(Group root) {
        super(root);
        images[0] = new Image("0.png");
        images[1] = new Image("1.png");
        images[2] = new Image("2.png");
        images[3] = new Image("3.png");
        images[4] = new Image("4.png");
        images[5] = new Image("5.png");
        images[6] = new Image("6.png");
        images[7] = new Image("7.png");
        images[8] = new Image("8.png");
        images[9] = new Image("9.png");
        images[10] = new Image("10.png");
        images[11] = new Image("11.png");

    }

    @Override
    /**
     * create slope and then spawn a grenade along that trajectory
     * add grenade to projectiles arraylist
     */
    public void shoot() {
        Point2D slope = super.slopeGenerator(bulletSpeed);
        Grenade grenade = new Grenade(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
        projectiles.add(grenade);
    }


}
