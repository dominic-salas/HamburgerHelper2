package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainScene extends Application {
    static GameInitializer initializer;
    static Group root = new Group();
    static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //scene primaryStage starts out as GUI
        Parent GUI = FXMLLoader.load(getClass().getResource("main.fxml"));
        Parent GUIdead = FXMLLoader.load(getClass().getResource("dead.fxml"));
        Scene GUIDead = new Scene(GUIdead, 600, 600);
        primaryStage.setScene(new Scene(GUI, 600, 600));
        primaryStage.setTitle("Hamburger Helper 2: Electric Boogaloo");

        scene = new Scene(root, 600, 600);
        scene.setFill(Color.WHITE); //color of the scene
        primaryStage.show();
        primaryStage.getX();
        primaryStage.setResizable(false);

        initializer = new GameInitializer(scene, primaryStage, root, GUIDead);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
