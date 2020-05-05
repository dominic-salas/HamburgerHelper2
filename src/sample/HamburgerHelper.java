package sample;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class HamburgerHelper implements Spawnable, Killable {
    public ImageView sprite;
    private Image handImage;
    public int lives;
    public double ySpeed;
    public double xSpeed;
    public int score;
    public ArrayList powerups;
    public Timeline timeline;

    public void move() {
    }

    public void shoot(boolean mouseHover) {
    }

    public void checkIntersect() {
    }

    public void pickupWeapon() {
    }

    @Override
    public void spawn(ImageView sprite, Group root) {
    }

    @Override
    public void dropHealth(double damage) {

    }
}
