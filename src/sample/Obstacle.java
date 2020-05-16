package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Random;

import static sample.GameInitializer.mapMaker;
import static sample.MapMaker.obsImage;

/**
 * By Dominic Salas
 */
public class Obstacle implements Spawnable {
    public ImageView sprite = new ImageView();
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


    public Obstacle(double xPos, double yPos, HamburgerHelper handy, Group root) {
        this.handy = handy;
        timeline = new Timeline();
        this.xPos = xPos;
        this.yPos = yPos;
        sprite.setX(xPos);
        sprite.setY(yPos);
        sprite.setFitWidth(rand.nextInt(50) + 50);
        sprite.setFitHeight(rand.nextInt(50) + 50);
        sprite.setImage(obsImage);

        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null) {
                            checkPos(root);
                            if ((rand.nextInt(1000) == 1 && root.getChildren().contains(sprite)) && mapMaker.obstacles.size() <= 30) {
                                mapMaker.spawnNewColumn(root);
                                mapMaker.spawnNewRow(root);
                            }
                            if (mapMaker.obstacles.isEmpty()) {
                                mapMaker.spawnNewColumn(root);
                                mapMaker.spawnNewRow(root);
                            }
                        }
                        //mapMaker.avoidLag(root);
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * Predicts obstacle collision from handy x and y speeds
     * prevents individual x and y motion if thinks it will cause collision
     * By David Rogers
     */
    public void predictIntersectHandy() {
        handy.hitbox.relocate(HamburgerHelper.xpos + 92 + xSpeed, HamburgerHelper.ypos + 88); //move hitbox to predicted x position
        if (handy.hitbox.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
            xSpeed = 0;
        }
        handy.hitbox.relocate(HamburgerHelper.xpos + 92, HamburgerHelper.ypos + 88); //return hitbox to og position


        handy.hitbox.relocate(HamburgerHelper.xpos + 92, HamburgerHelper.ypos + 88 + ySpeed); //move hitbox to predicted y position
        if (handy.hitbox.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
            ySpeed = 0;
        }
        handy.hitbox.relocate(HamburgerHelper.xpos + 92, HamburgerHelper.ypos + 88); //return to og position
    }

    public static void predictIntersectEnemies(Rectangle hitbox, Enemy enemy) {
        hitbox.relocate(hitbox.getX() + 12.5 + enemy.xSpeed, hitbox.getY() + 1); //move hitbox to predicted x position
        if (hitbox.getBoundsInParent().intersects(enemy.sprite.getBoundsInParent())) {
            if (enemy.xSpeed > 0) {
                enemy.xSpeed -= enemy.ySpeed; // are these backwards?
            } else if (enemy.xSpeed < 0) {
                enemy.xSpeed += enemy.ySpeed;
            }

            System.out.println("intersect");
        }
        hitbox.relocate(hitbox.getX() + 12.5, hitbox.getY() + 1); //return hitbox to og position


        hitbox.relocate(hitbox.getX() + 12.5, hitbox.getY() + 1 + enemy.ySpeed); //move hitbox to predicted y position
        if (hitbox.getBoundsInParent().intersects(enemy.sprite.getBoundsInParent())) {
            if (enemy.ySpeed > 0) {
                enemy.ySpeed -= enemy.ySpeed;
            } else if (enemy.ySpeed < 0) {
                enemy.ySpeed += enemy.ySpeed;
            }
            System.out.println("intersect");
        }
        hitbox.relocate(hitbox.getX() + 12.5, hitbox.getY() + 1); //return to og position
    }

    public void convertMotion() {
        xPos -= xSpeed;
        yPos -= ySpeed;
        sprite.relocate(xPos, yPos);
    }

    private void checkPos(Group root) {
        if ((xPos >= 660 || xPos <= (-60 - sprite.getFitWidth()) || yPos >= 660 || yPos <= (-60 - sprite.getFitHeight()))) {
            mapMaker.obstacles.remove(this);
            despawn(sprite, root);
            sprite = null;
        }
    }

}
