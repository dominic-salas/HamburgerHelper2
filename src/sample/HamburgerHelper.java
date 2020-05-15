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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HamburgerHelper implements Spawnable, Killable {
    public ImageView sprite = new ImageView();
    private Image leftForward = new Image("/handy forwards left.png");
    private Image rightForward = new Image("/handy forwards right.png");
    private Image leftBack = new Image("/backwardshandleft.png");
    private Image rightBack = new Image("/backwardshandright.png");
    public int lives = 100;
    public ArrayList powerups;
    public static Weapon weapon;
    public Timeline timeline = new Timeline();
    public static double xpos = 170;
    public static double ypos = 150;
    int imageOffsetCorrectY = 142;
    int imageOffsetCorrectX = 115;
    UserInput userInput;
    Stage primaryStage;
    boolean relativeLeft;
    boolean relativeRight;
    boolean relativeDown;
    boolean relativeUp;
    public Rectangle hitbox;
    boolean dead = false;
    private Text dedLmao = new Text("u ded lol, just press enter to retry for now");
    private Text liveText = new Text("Health: " + lives);
    private Text zeroText = new Text("Health: DEAD lol");

    public HamburgerHelper(Group root, UserInput userInput, Stage primaryStage) {
        hitbox = new Rectangle(xpos + 92, ypos + 88, 40, 50);
        //root.getChildren().add(hitbox); // for hitbox testing
        this.userInput = userInput;
        this.primaryStage = primaryStage;
        sprite.setScaleY(.3);
        sprite.setScaleX(.3);
        sprite.setY(300);
        sprite.setX(300);
        sprite.setImage(leftForward);
        sprite.relocate(xpos, ypos);
        weapon = new ShotGun(root);

        liveText.setX(0);
        liveText.setY(10);
        liveText.setTextAlignment(TextAlignment.CENTER);
        zeroText.setX(0);
        zeroText.setY(10);
        zeroText.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(liveText);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (userInput.enterPress && dead) {
                            lives = 100;
                            liveText.setText("Health: " + lives);
                            root.getChildren().remove(dedLmao);
                            root.getChildren().remove(zeroText);
                            root.getChildren().add(liveText);
                            dead = false;
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    private void updateImage(){
        //mouse to left or right of handy
        if(UserInput.mousePosX >xpos+imageOffsetCorrectX){
            relativeRight=true;
            relativeLeft=false;
        }else{
            relativeLeft=true;
            relativeRight=false;
        }
        //mouse above or below handy
        if(UserInput.mousePosY >ypos+imageOffsetCorrectY){
            relativeUp=false;
            relativeDown=true;
        }else{
            relativeDown=false;
            relativeUp=true;
        }

        if(relativeLeft){
            if(relativeUp){
                sprite.setImage(leftBack);
            }else{
                sprite.setImage(leftForward);
            }
        }else{
            if(relativeUp){
                sprite.setImage(rightBack);
            }else {
                sprite.setImage(rightForward);
            }
        }
    }

    public void convertInput() {
        if (!dead) {
            if (userInput.leftPress) { //update x speed
                Obstacle.xSpeed = -4;
            } else if (userInput.rightPress) {
                Obstacle.xSpeed = +4;
            }
            if (!userInput.leftPress && !userInput.rightPress || (userInput.rightPress && userInput.leftPress)) {
                Obstacle.xSpeed = 0;
            } //set to zero speed if nothing pressed

            if (userInput.upPress) { //update y speed
                Obstacle.ySpeed = -4;
            } else if (userInput.downPress) {
                Obstacle.ySpeed = +4;
            }
            if (!userInput.upPress && !userInput.downPress || (userInput.upPress && userInput.downPress)) {
                Obstacle.ySpeed = 0;
            } //set to zero speed if nothing pressed

            updateImage();
        } else {
            Obstacle.xSpeed = 0;
            Obstacle.ySpeed = 0;
        }
    }

    public void shoot(boolean mouseHover) {
    }

    public void checkIntersect() {
    }

    public void pickupWeapon() {
    }

    public void dropHealth(double damage, Group root) {
        liveText.setText("Health: " + lives);
        lives -= damage;
        if (lives <= 0 && !dead) {
            root.getChildren().remove(liveText);
            die(root);
            dead = true;
            root.getChildren().add(zeroText);
        }
    }

    private void die(Group root) {
        dedLmao.setTextAlignment(TextAlignment.CENTER);
        dedLmao.maxWidth(200);
        dedLmao.setX(200);
        dedLmao.setY(200);
        root.getChildren().add(dedLmao);
    }
}
