package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.naming.ldap.Control;

public class GameInitializer {
    Scene scene;
    Stage primaryStage;
    Group root;
    Scene GUI;
    UserInput inputs;
    static DeadGUIController deathController;
    public static MapMaker mapMaker;

    public GameInitializer(Scene scene, Stage primaryStage, Group root, Scene GUI) {
        this.scene = scene;
        this.primaryStage = primaryStage;
        this.root = root;
        this.GUI = GUI;
    }

    public void startGame() {

        inputs = new UserInput(scene, primaryStage);
        HamburgerHelper handy = new HamburgerHelper(root, inputs, primaryStage, GameInitializer.this);
        mapMaker = new MapMaker(handy);
        mapMaker.initSpawn(root);
        EnemyFactory enemy = new EnemyFactory(root, handy, startGUIController.scoreManager);
        handy.spawn(handy.sprite, root);
        PowerUpFactory powerUpFactory = new PowerUpFactory(root, handy, startGUIController.scoreManager);
        primaryStage.setScene(scene); //changes scene from gui to game
    }

    public void restartGame() {
        primaryStage.setScene(GUI);
        deathController.printScores();
    }

    public void swapScene() {
        primaryStage.setScene(scene);
        MainScene.initializer.inputs.downPress = false;
        MainScene.initializer.inputs.upPress = false;
        MainScene.initializer.inputs.rightPress = false;
        MainScene.initializer.inputs.leftPress = false;
    }
}
