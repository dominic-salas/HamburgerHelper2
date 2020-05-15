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
    public int lives = 3;
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

    public HamburgerHelper(Group root, UserInput userInput, Stage primaryStage) {
        hitbox = new Rectangle(xpos+92,ypos+88,40,50);
        //root.getChildren().add(hitbox); // for hitbox testing
        this.userInput = userInput;
        this.primaryStage = primaryStage;
        sprite.setScaleY(.3);
        sprite.setScaleX(.3);
        sprite.setY(300);
        sprite.setX(300);
        sprite.setImage(leftForward);
        sprite.relocate(xpos,ypos);
        weapon= new BasicGun(root);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
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
        if(userInput.leftPress){ //update x speed
            Obstacle.xSpeed=-4;
        }else if(userInput.rightPress){
            Obstacle.xSpeed=+4;
        }if(!userInput.leftPress&&!userInput.rightPress||(userInput.rightPress&&userInput.leftPress)){Obstacle.xSpeed=0;} //set to zero speed if nothing pressed

        if(userInput.upPress){ //update y speed
            Obstacle.ySpeed=-4;
        }else if(userInput.downPress){
            Obstacle.ySpeed=+4;
        }if(!userInput.upPress&&!userInput.downPress||(userInput.upPress&&userInput.downPress)){Obstacle.ySpeed=0;} //set to zero speed if nothing pressed

        updateImage();
    }

    public void shoot(boolean mouseHover) {
    }

    public void checkIntersect() {
    }

    public void pickupWeapon() {
    }


}
