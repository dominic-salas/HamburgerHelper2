package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    ScoreManager scoreManager = new ScoreManager();
    @FXML Button searchButton;
    @FXML TextField profileInput;
    @FXML Label profileNameDisplay = new Label();
    @FXML Label highScoreDisplay = new Label();
    @FXML Label creditsDisplay = new Label();
    @FXML Label dateCreatedDisplay = new Label();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoreManager.loadProfiles();

    }

    public void changeScene() {
        MainScene.initializer.startGame();
    }

    public void setProfile(){
        scoreManager.selectAccount(profileInput.getText());
        profileNameDisplay.setText("Profile Name: "+scoreManager.activeProfile.playerName);
        highScoreDisplay.setText("Highscore: "+scoreManager.activeProfile.highscore);
        creditsDisplay.setText("Credits: "+scoreManager.activeProfile.credits);
        dateCreatedDisplay.setText("Created On: "+scoreManager.activeProfile.dateCreated);

    }


}
