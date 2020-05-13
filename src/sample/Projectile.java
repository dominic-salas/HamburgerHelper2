package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Projectile implements Spawnable {
    double xpos;
    double ypos;
    double xSpeed;
    double ySpeed;
    public ImageView sprite;
    protected Timeline timeline;
    protected Group root;

    public Projectile(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.root = root;
        sprite = new ImageView();
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        move();
                        checkDespawn();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    public void move() {
        xpos += xSpeed;
        ypos += ySpeed;
        sprite.relocate(xpos, ypos);
    }

    private void checkDespawn() {
        if (xpos > 700 || xpos < -100 || ypos > 700 | ypos < -100) {
            despawn(sprite, root);
            timeline.stop();

        }
    }
}
