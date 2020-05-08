package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameInitializer {
    Scene scene;
    Stage primaryStage;
    Group root;

    public GameInitializer(Scene scene, Stage primaryStage, Group root){
        this.scene = scene;
        this.primaryStage = primaryStage;
        this.root = root;
    }
    public void startGame(){
        UserInput inputs = new UserInput(scene, primaryStage);

        MapMaker mapMaker = new MapMaker();
        mapMaker.initSpawn(root);
        HamburgerHelper handy = new HamburgerHelper(root, inputs, primaryStage, mapMaker.obstacles);
        handy.spawn(handy.sprite, root);

        primaryStage.setScene(scene); //changes scene from gui to game
    }

}
