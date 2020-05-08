package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class Obstacle implements Spawnable {
    public ImageView sprite = new ImageView();
    private Image image = new Image("brick_wall.png");
    private double xPos;
    private double yPos;
    private double xSize;
    private double ySize;
    private Random rand = new Random();

    public Obstacle(double xPos, double yPos) {
        /*xPos = rand.nextInt(560) + rand.nextDouble();
        yPos = rand.nextInt(560) + rand.nextDouble();
        xSize = rand.nextInt(100);
        ySize = rand.nextInt(100);*/
        this.xPos = xPos;
        this.yPos = yPos;
        sprite.setX(xPos);
        sprite.setY(yPos);
        sprite.setFitWidth(rand.nextInt(50) + 50);
        sprite.setFitHeight(rand.nextInt(50) + 50);
        sprite.setImage(image);
    }

    public void checkIntersect(HamburgerHelper handy) {
        if (handy.sprite.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
            System.out.println("collision");
        }
    }
}
