package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin extends PowerUp {
    public ImageView sprite;
    private Image coinImage;
    public int spawnChance;
    public double xPos;
    public double yPos;

    @Override
    public void spawn(ImageView sprite, Group root) {
    }

    private void pickup() {
    }

    private void checkIntersect() {
    }

    private void despawn() {
    }

    private void special() {
    }
}
