package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Minigun extends Weapon {
    Timeline timeline = new Timeline();
    public ImageView sprite;
    private Image weaponImage;
    public double spawnChance;
    public double bulletSpeed = 10;

    public Minigun(Group root) {
        super(root);
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame action = new KeyFrame(Duration.seconds(.010),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        shoot();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }
    @Override
    public void shoot(){
        if(UserInput.mouseHeld){
            Point2D slope = super.slopeGenerator(bulletSpeed);
            basicBullet bullet = new basicBullet(HamburgerHelper.xpos+105,HamburgerHelper.ypos+105,slope.getX(),slope.getY(),root);
            projectiles.add(bullet);
        }
    }


}
