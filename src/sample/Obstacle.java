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
    private Image image = new Image("Resources/brick_wall.png");
    private double xPos;
    private double yPos;
    private double xSize;
    private double ySize;
    private Random rand = new Random();
    private Timeline timeline;
    private HamburgerHelper handy;

    public Obstacle(double xPos, double yPos, HamburgerHelper handy) {
        /*xPos = rand.nextInt(560) + rand.nextDouble();
        yPos = rand.nextInt(560) + rand.nextDouble();
        xSize = rand.nextInt(100);
        ySize = rand.nextInt(100);*/
        this.handy = handy;
        timeline= new Timeline();
        this.xPos = xPos;
        this.yPos = yPos;
        sprite.setX(xPos);
        sprite.setY(yPos);
        sprite.setFitWidth(rand.nextInt(50) + 50);
        sprite.setFitHeight(rand.nextInt(50) + 50);
        sprite.setImage(image);

        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        convertMotion();
                        checkIntersect();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }


    public void checkIntersect() {
        if (handy.sprite.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
            System.out.println("collision");
        }
    }

    public void convertMotion(){
        yPos-=handy.ySpeed;
        xPos-=handy.xSpeed;
        sprite.relocate(xPos,yPos);
    }
}
