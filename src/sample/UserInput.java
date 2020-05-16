package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;

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


    public UserInput(Scene scene, Stage primaryStage){
        this.scene=scene;
        this.primaryStage = primaryStage;
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        checkInputs();
                        checkMouseHover();
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

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
        /**
         * check for key releases to update hand movements
         */
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
                HamburgerHelper.weapon.shoot();
            }
        });
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseHeld=true;
            }
        });
        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseHeld=false;
            }
        });
    }


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
