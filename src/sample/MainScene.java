package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainScene extends Application {
    static GameInitializer initializer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //scene primaryStage starts out as GUI
        Parent GUI = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setScene(new Scene(GUI,600,600));
        primaryStage.setTitle("Hamburger Helper 2: Electric Boogaloo");
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        scene.setFill(Color.WHITE); //color of the scene

        primaryStage.show();
        primaryStage.getX();
        primaryStage.setResizable(false);

       initializer= new GameInitializer(scene,primaryStage,root);

    }



    public static void main(String[] args) {
        launch(args);
    }
}
