package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.naming.ldap.Control;

/**
 * Creates all necessary classes to be initialized for the game
 * by David Rogers
 */
public class GameInitializer {
    Scene scene;
    Stage primaryStage;
    Group root;
    Scene GUI;
    UserInput inputs;
    static DeadGUIController deathController;
    public static MapMaker mapMaker;

    /**
     * GameInitializer class to get variables from MainScene
     */
    public GameInitializer(Scene scene, Stage primaryStage, Group root, Scene GUI) {
        this.scene = scene;
        this.primaryStage = primaryStage;
        this.root = root;
        this.GUI = GUI;
    }

    /**
     * Method called to make handy, obstacles, enemies, etc.
     * launches the game
     */
    public void startGame() {

        inputs = new UserInput(scene, primaryStage);
        HamburgerHelper handy = new HamburgerHelper(root, inputs, primaryStage, GameInitializer.this);
        mapMaker = new MapMaker(handy);
        mapMaker.initSpawn(root);
        EnemyFactory enemy = new EnemyFactory(root, handy, startGUIController.scoreManager);
        handy.spawn(handy.sprite, root);
        PowerUpFactory powerUpFactory = new PowerUpFactory(root, handy, startGUIController.scoreManager, enemy);
        primaryStage.setScene(scene); //changes scene from gui to game
    }

    /**
     * swaps the scene to the death screen
     * passes scores to be printed by death screen
     */
    public void restartGame() {
        primaryStage.setScene(GUI);
        deathController.printScores();
    }

    /**
     * swaps the scene from the dead screen to the game, resets some inputs to prevent
     * glitches
     */
    public void swapScene() {
        primaryStage.setScene(scene);
        MainScene.initializer.inputs.downPress = false;
        MainScene.initializer.inputs.upPress = false;
        MainScene.initializer.inputs.rightPress = false;
        MainScene.initializer.inputs.leftPress = false;
    }
}
