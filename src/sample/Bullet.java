package sample;

import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet {
    public double damageValue;
    private Color color;
    private Circle sprite;
    private double xSpeed;
    private double ySpeed;
    private Timeline timeline;

    private boolean checkIntersect() {
        return false;
    }

    private void spawn() {
    }

    private void despawn() {
    }

    private void move(int xSpeed, int ySpeed) {
    }
}
