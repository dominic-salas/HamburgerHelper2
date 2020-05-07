package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainScene extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hamburger Helper 2: Electric Boogaloo");
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        scene.setFill(Color.WHITE); //color of the scene
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getX();
        primaryStage.setResizable(false);

        UserInput inputs = new UserInput(scene,primaryStage);

        HamburgerHelper handy = new HamburgerHelper(root,inputs,primaryStage);
        handy.spawn(handy.sprite,root);

        ScoreManager scoreManager = new ScoreManager();
        scoreManager.loadProfiles();
        scoreManager.selectAccount();
        scoreManager.storeProfiles();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
