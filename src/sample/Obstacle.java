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

import static sample.GameInitializer.mapMaker;

public class Obstacle implements Spawnable {
    public ImageView sprite = new ImageView();
    private Image image = new Image("brick_wall.png");
    private double xPos;
    private double yPos;
    private double xSize;
    private double ySize;
    private Random rand = new Random();
    private Timeline timeline;
    private HamburgerHelper handy;
    private boolean deleted;


    public Obstacle(double xPos, double yPos, HamburgerHelper handy, Group root) {
        this.handy = handy;
        timeline = new Timeline();
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
                        if (sprite != null) {
                            convertMotion();
                            checkIntersect();
                            checkPos(root);
                            if ((rand.nextInt(300) == 1 && root.getChildren().contains(sprite)) && mapMaker.obstacles.size() <= 30) {
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


    public void checkIntersect() {
        if (handy.sprite.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
            System.out.println("collision");
        }
    }

    public void convertMotion() {
        yPos -= handy.ySpeed;
        xPos -= handy.xSpeed;
        sprite.relocate(xPos, yPos);
    }

    private void checkPos(Group root) {
        if ((xPos >= 660 || xPos <= (-60 - sprite.getFitWidth()) || yPos >= 660 || yPos <= (-60 - sprite.getFitHeight())) && !deleted) {
            System.out.println("before delete: " + mapMaker.obstacles.size());
            mapMaker.obstacles.remove(this);
            System.out.println("after delete: " + mapMaker.obstacles.size());
            despawn(sprite, root);
            sprite = null;
        }
    }

}
