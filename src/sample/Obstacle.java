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
    public static double xSpeed;
    public static double ySpeed;
    public static boolean collision=false;
    private double xSize;
    private double ySize;
    private Random rand = new Random();
    private Timeline timeline;
    private HamburgerHelper handy;
    private MapMaker mapMaker;

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

                    }
                });
        //timeline.getKeyFrames().add(action);
        //timeline.play();
    }


    public void predictIntersect() {
        handy.hitbox.relocate(handy.xpos+92+xSpeed,handy.ypos+88); //move hitbox to predicted x position
        if(handy.hitbox.getBoundsInParent().intersects(sprite.getBoundsInParent())){
            xSpeed =0;
        }
        handy.hitbox.relocate(handy.xpos+92,handy.ypos+88); //return hitbox to og position


        handy.hitbox.relocate(handy.xpos+92,handy.ypos+88+ySpeed); //move hitbox to predicted y position
        if(handy.hitbox.getBoundsInParent().intersects(sprite.getBoundsInParent())){
            ySpeed =0;
        }
        handy.hitbox.relocate(handy.xpos+92,handy.ypos+88); //return to og position

    }
    public void convertMotion(){
        xPos-=xSpeed;
        yPos-=ySpeed;
        sprite.relocate(xPos,yPos);
    }

}
