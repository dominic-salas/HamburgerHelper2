package sample;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class basicBullet extends Projectile{
    public Group root;

    public basicBullet(double xpos, double ypos, double xSpeed, double ySpeed, Group root) {
        super(xpos, ypos, xSpeed, ySpeed, root);
        this.root = root;
        Image image = new Image("Resources/projectile.png");
        sprite.setImage(image);
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setFitHeight(20);
        sprite.setFitWidth(20);
        spawn(sprite, root);

        damage = 1;

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        checkObstacleIntersect();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    public void checkObstacleIntersect(){
        MapMaker.obstacles.forEach(obstacle -> {
            if(sprite.getBoundsInParent().intersects(obstacle.sprite.getBoundsInParent())){
                despawn();
                this.timeline.pause();
            }
        });

    }
}
