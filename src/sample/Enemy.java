package sample;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public abstract class Enemy implements Spawnable, Killable {
    public ImageView sprite;
    public int lives;
    public Rectangle hitbox;
    public boolean noXMovement;
    public boolean noYMovement;
    public double ySpeed = 0;
    public double xSpeed = 0;
    public boolean isDead = false;

    private void attack() {
    }

    private void shoot() {
    }

    private void chase() {
    }
}
