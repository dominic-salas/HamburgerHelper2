package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameInitializer {
    Scene scene;
    Stage primaryStage;
    Group root;
    public static MapMaker mapMaker;

    public GameInitializer(Scene scene, Stage primaryStage, Group root){
        this.scene = scene;
        this.primaryStage = primaryStage;
        this.root = root;
    }
    public void startGame(){
        UserInput inputs = new UserInput(scene, primaryStage);
        HamburgerHelper handy = new HamburgerHelper(root, inputs, primaryStage);
        EnemyFactory enemy = new EnemyFactory(root, handy, Controller.scoreManager);
        mapMaker = new MapMaker(handy);
        mapMaker.initSpawn(root);
        handy.spawn(handy.sprite, root);


        primaryStage.setScene(scene); //changes scene from gui to game
    }

}
