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

import java.sql.Time;
import java.util.Random;

public class Obstacle implements Spawnable {
    public ImageView sprite = new ImageView();
    private Image image = new Image("brick_wall.png");
    private double xPos;
    private double yPos;
    private double xSize;
    private double ySize;
    private Random rand = new Random();

    private Timeline timeline = new Timeline();


    public Obstacle(double xPos, double yPos) {
        /*xPos = rand.nextInt(560) + rand.nextDouble();
        yPos = rand.nextInt(560) + rand.nextDouble();
        xSize = rand.nextInt(100);
        ySize = rand.nextInt(100);*/
        sprite.setX(xPos);
        sprite.setY(yPos);
        sprite.setFitWidth(rand.nextInt(80) + 20);
        sprite.setFitHeight(rand.nextInt(80) + 20);
        sprite.setImage(image);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    private void despawn(Group root) {
        root.getChildren().remove(sprite);
    }

    public void checkIntersect(HamburgerHelper handy) {
        if (handy.sprite.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
            System.out.println("collision");
        }
    }
}
