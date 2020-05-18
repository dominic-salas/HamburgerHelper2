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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * By Dominic and David
 */
public class HamburgerHelper implements Spawnable, Killable {
    public ImageView sprite = new ImageView();
    private Image leftForward = new Image("handy forwards left.png");
    private Image rightForward = new Image("handy forwards right.png");
    private Image leftBack = new Image("backwardshandleft.png");
    private Image rightBack = new Image("backwardshandright.png");
    public static int lives = 100;
    public ArrayList powerups;
    public static Weapon weapon;
    public Timeline timeline = new Timeline();
    public static double xpos = 170;
    public static double ypos = 150;
    int imageOffsetCorrectY = 142;
    int imageOffsetCorrectX = 115;
    int seconds = 5;
    int counter;
    UserInput userInput;
    Stage primaryStage;
    boolean relativeLeft;
    boolean relativeRight;
    boolean relativeDown;
    boolean relativeUp;
    public static boolean waitRestart = true;
    public Rectangle hitbox;
    public static boolean dead = false;
    private Text liveText = new Text("Health: " + lives);
    public static double speed = 4;


    /**
     * hamburger helper object that initializes everything and handles what handy does
     *
     * @param root         to spawn to group
     * @param userInput    to handle inputs from handy
     * @param primaryStage to manage primary stage
     */
    public HamburgerHelper(Group root, UserInput userInput, Stage primaryStage, GameInitializer gameInitializer) {
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
        liveText.setX(1);
        liveText.setY(15);
        liveText.setFont(new Font(18));
        liveText.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(liveText);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (dead) {
                            root.getChildren().removeAll();
                            lives = 100;
                            liveText.setText("Health: " + lives);
                            gameInitializer.restartGame();
                            if (waitRestart) {
                                root.getChildren().add(liveText);
                                waitRestart = false;
                            }
                        }
                        if (lives >= 200 && seconds > 0) {
                            counter++;
                            liveText.setText("Invulnerable for " + seconds);
                            if (counter >= 100) {
                                seconds--;
                                counter = 0;
                            }
                        } else if (lives <= 100) {
                            liveText.setText("Health: " + lives);
                            seconds = 5;
                            counter = 0;
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * updates hand image to always be facing cursor
     * By David Rogers
     */
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

    /**
     * uses userinput to convert into hand motion
     * hand motion is actually just obstacle motion relative to stationary hand
     * By David Rogers
     */
    public void convertInput() {
        if (!dead) {
            if (userInput.leftPress) { //update x speed
                Obstacle.xSpeed = -speed;
            } else if (userInput.rightPress) {
                Obstacle.xSpeed = +speed;
            }
            if (!userInput.leftPress && !userInput.rightPress || (userInput.rightPress && userInput.leftPress)) {
                Obstacle.xSpeed = 0;
            } //set to zero speed if nothing pressed

            if (userInput.upPress) { //update y speed
                Obstacle.ySpeed = -speed;
            } else if (userInput.downPress) {
                Obstacle.ySpeed = +speed;
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

    /**
     * drops the health of handy
     *
     * @param damage to show how much damage should be subtracted
     * @param root   to add text
     */
    public void dropHealth(double damage, Group root) {
        if (lives <= 100) {
            liveText.setText("Health: " + lives);
            lives -= damage;
        }
        if (lives <= 0 && !dead) {
            root.getChildren().remove(liveText);
            dead = true;
        }
    }
}
