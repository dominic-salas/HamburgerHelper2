package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HamburgerHelper implements Spawnable, Killable {
    public ImageView sprite = new ImageView();
    private Image leftForward = new Image("Resources/handy forwards left.png");
    private Image rightForward = new Image("Resources/handy forwards right.png");
    private Image leftBack = new Image("Resources/backwardshandleft.png");
    private Image rightBack = new Image("Resources/backwardshandright.png");
    public int lives;
    public double ySpeed = 0;
    public double xSpeed = 0;
    public int score;
    public ArrayList powerups;
    public Timeline timeline = new Timeline();
    public double xpos = 170;
    public double ypos = 150;
    int imageOffsetCorrectY = 142;
    int imageOffsetCorrectX = 115;
    UserInput userInput;
    Stage primaryStage;
    boolean relativeLeft;
    boolean relativeRight;
    boolean relativeDown;
    boolean relativeUp;

    public HamburgerHelper(Group root, UserInput userInput, Stage primaryStage) {
        this.userInput = userInput;
        this.primaryStage = primaryStage;
        sprite.setScaleY(.3);
        sprite.setScaleX(.3);
        sprite.setY(300);
        sprite.setX(300);
        sprite.setImage(leftForward);
        sprite.relocate(xpos,ypos);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        move();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    private void updateImage(){
        //mouse to left or right of handy
        if(userInput.mousePosX>xpos+imageOffsetCorrectX){
            relativeRight=true;
            relativeLeft=false;
        }else{
            relativeLeft=true;
            relativeRight=false;
        }
        //mouse above or below handy
        if(userInput.mousePosY>ypos+imageOffsetCorrectY){
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

    public void move() {
        if(userInput.leftPress&&xSpeed>-5){ //update x speed
            if(xSpeed>0){
                xSpeed=0;
            }
            xSpeed-=.15;
        }else if(userInput.rightPress&&xSpeed<5){
            if(xSpeed<0){
                xSpeed=0;
            }
            xSpeed+=.15;
        }if(!userInput.leftPress&&!userInput.rightPress||(userInput.rightPress&&userInput.leftPress)){xSpeed=0;} //set to zero speed if nothing pressed
        //xpos+=xSpeed;

        if(userInput.upPress&&ySpeed>-5){ //update y speed
            if(ySpeed>0){
                ySpeed=0;
            }
            ySpeed-=.15;
        }else if(userInput.downPress&&ySpeed<5){
            if(ySpeed<0){
                ySpeed=0;
            }
            ySpeed+=.15;
        }if(!userInput.upPress&&!userInput.downPress||(userInput.upPress&&userInput.downPress)){ySpeed=0;} //set to zero speed if nothing pressed
        //ypos+=ySpeed;

        updateImage();
    }

    public void shoot(boolean mouseHover) {
    }

    public void checkIntersect() {
    }

    public void pickupWeapon() {
    }

    @Override
    public void dropHealth(double damage) {

    }
}
