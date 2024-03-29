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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Hamburger helper class for spawning handy and managing all handy behavior
 * By Dominic and David
 */
public class HamburgerHelper implements Spawnable, Killable {
    public ImageView sprite = new ImageView();
    private Image leftForward = new Image("handy forwards left.png");
    private Image rightForward = new Image("handy forwards right.png");
    private Image leftBack = new Image("backwardshandleft.png");
    private Image rightBack = new Image("backwardshandright.png");
    static int lives = 100;
    public static Weapon weapon = new BasicGun(MainScene.root);
    public Timeline timeline = new Timeline();
    public static double xpos = 170;
    public static double ypos = 150;
    int imageOffsetCorrectY = 142;
    int imageOffsetCorrectX = 115;
    private int seconds = 5;
    private int counter;
    Rectangle healthBar = new Rectangle();
    Rectangle barBlank = new Rectangle();
    UserInput userInput;
    Stage primaryStage;
    boolean relativeLeft;
    boolean relativeRight;
    boolean relativeDown;
    boolean relativeUp;
    public static boolean waitRestart = true;
    public Rectangle hitbox;
    public static boolean dead = false;
    private boolean wait = false;
    private Text secondLife = new Text("Extra Life! Press Enter to Keep Going...");
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
        secondLife.prefWidth(100);
        secondLife.prefHeight(100);
        secondLife.setX(140);
        secondLife.setY(100);
        secondLife.setTextAlignment(TextAlignment.CENTER);
        secondLife.setFont(Font.font(20));
        liveText.setX(1);
        liveText.setY(14);
        liveText.setFont(new Font(16));
        liveText.setTextAlignment(TextAlignment.CENTER);
        healthBar.setFill(Color.GREEN);
        healthBar.setX(0);
        healthBar.setY(1);
        healthBar.setWidth(200);
        healthBar.setHeight(16);
        barBlank.setFill(Color.RED);
        barBlank.setX(200);
        barBlank.setY(1);
        barBlank.setWidth(0);
        barBlank.setHeight(16);
        root.getChildren().add(healthBar);
        root.getChildren().add(barBlank);
        root.getChildren().add(liveText);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        dropHealth(0, root); //to make sure health bar is always on top of everything else, essentially replaces it every frame
                        manageHealth(root, gameInitializer);
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
            lives -= damage;
            root.getChildren().remove(healthBar);
            healthBar.setWidth(lives * 2);
            root.getChildren().add(healthBar);
            root.getChildren().remove(barBlank);
            barBlank.setWidth(200 - (lives * 2));
            barBlank.setX(200 - barBlank.getWidth());
            root.getChildren().add(barBlank);
            root.getChildren().remove(liveText);
            liveText.setText("Health: " + lives);
            root.getChildren().add(liveText);
        }
        if (lives <= 0 && !dead) {
            root.getChildren().remove(liveText);
            dead = true;
        }
    }

    /**
     * Large chunk of code that handles all situations where health and healthbars would change
     *
     * @param root            to add healthbar and health to group
     * @param gameInitializer to call the death screen when life is less than 0 (handy dies)
     */
    private void manageHealth(Group root, GameInitializer gameInitializer) {
        if (dead && !PowerUpFactory.secondLife) {
            root.getChildren().removeAll();
            lives = 100;
            gameInitializer.restartGame();
            if (waitRestart) {
                healthBar.setWidth(200);
                barBlank.setWidth(0);
                dropHealth(0, root);
                waitRestart = false;
            }
        } else if (dead && !wait) {
            root.getChildren().remove(liveText);
            root.getChildren().remove(healthBar);
            root.getChildren().remove(barBlank);
            root.getChildren().add(secondLife);
            wait = true;
        }
        if (wait) {
            UserInput.mouseHeld = false;
        }
        if (userInput.enterPress && dead) {
            root.getChildren().remove(secondLife);
            healthBar.setWidth(200);
            barBlank.setWidth(0);
            lives = 100;
            dropHealth(0, root);
            PowerUpFactory.secondLife = false;
            dead = false;
            wait = false;
        }
        if (lives >= 200 && seconds > 0) {
            counter++;
            liveText.setText("Invulnerable for " + seconds);
            healthBar.setWidth(200);
            healthBar.setFill(Color.BLUE);
            barBlank.setWidth(0);
            if (counter >= 100) {
                seconds--;
                counter = 0;
            }
        } else if (lives <= 100) {
            healthBar.setFill(Color.GREEN);
            if (!wait) {
                dropHealth(0, root);
            } else {
                root.getChildren().remove(liveText);
                root.getChildren().remove(healthBar);
                root.getChildren().remove(barBlank);
            }
            seconds = 5;
            counter = 0;
        }
    }
}
