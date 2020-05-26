package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;

/**
 * manages all user inputs through keyevents and mouse events
 * uses a little feature from AWT that allows constant tracking of mouse hover position
 * looked on stackoverflow and apparently this is one of the only option for mousetracking in javafx
 * By David Rogers
 */
public class UserInput {
    public boolean leftPress;
    public boolean rightPress;
    public boolean upPress;
    public boolean downPress;
    public static double mousePosX;
    public static double mousePosY;
    public static boolean mouseHeld;
    public boolean enterPress;
    private Timeline timeline = new Timeline();
    private Scene scene;
    private Stage primaryStage;
    private pathFinder stuckFinder = new pathFinder();


    public UserInput(Scene scene, Stage primaryStage){
        this.scene=scene;
        this.primaryStage = primaryStage;
        timeline.setCycleCount(Animation.INDEFINITE);
//timeline for checking inputs at given frequency
        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (!HamburgerHelper.dead) {
                            checkInputs();
                            checkMouseHover();
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    /**
     * checks WASD, and ENTER for both pressed and released
     * checks for mouse clicked for semi-auto weapons
     * checks for mouse pressed and released for full-auto weapons
     * stores current value as boolean
     */
    public void checkInputs() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        leftPress = true;
                        break;
                    case D:
                        rightPress = true;
                        break;
                    case W:
                        upPress = true;
                        break;
                    case S:
                        downPress = true;
                        break;
                    case ENTER:
                        enterPress = true;
                        break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        leftPress = false;
                        break;
                    case D:
                        rightPress = false;
                        break;
                    case W:
                        upPress = false;
                        break;
                    case S:
                        downPress = false;
                        break;
                    case ENTER:
                        enterPress = false;
                }
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!HamburgerHelper.dead) {
                    HamburgerHelper.weapon.shoot();
                    stuckFinder.findPath2(new Point2D(mousePosX-18,mousePosY-40));
                }
            }
        });
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseHeld = !HamburgerHelper.dead;
            }
        });
        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseHeld = false;
            }
        });
    }

    /**
     * constantly tracks x and y position of cursor
     * automotically adjusts values for when mouse is off scene, or window is moved around
     * using just a wee bit of AWT because it works
     */
    private void checkMouseHover() {
        double mousePosXRough = MouseInfo.getPointerInfo().getLocation().x;
        double mousePosYRough = MouseInfo.getPointerInfo().getLocation().y;

        if(mousePosXRough>primaryStage.getX()+600){
            mousePosX=600;
        }
        else if(mousePosXRough<primaryStage.getX()){
            mousePosX=0;
        }else{
            mousePosX=mousePosXRough-primaryStage.getX();
        }

        if(mousePosYRough>primaryStage.getY()+600){
            mousePosY=600;
        }
        else if(mousePosYRough<primaryStage.getY()){
            mousePosY=0;
        } else{
            mousePosY=mousePosYRough-primaryStage.getY();
        }

    }
}
