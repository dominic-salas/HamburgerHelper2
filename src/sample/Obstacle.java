package sample;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle implements Spawnable {
    public Rectangle sprite;
    private Color color;
    private double xPos;
    private double yPos;
    private double xSize;
    private double ySize;
    private Timeline timeline;

    public void spawn() {
    }

    private void despawn() {
    }

    private void checkIntersect() {
    }

    @Override
    public void spawn(ImageView sprite, Group root) {
    }
}
