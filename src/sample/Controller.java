package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    ScoreManager scoreManager = new ScoreManager();
    ObservableList<String> weaponOptions = FXCollections.observableArrayList("Basic Gun","Shotgun","Laser Gun","Laser Shotgun");

    @FXML Button searchButton;
    @FXML TextField profileInput;
    @FXML Label profileNameDisplay = new Label();
    @FXML Label highScoreDisplay = new Label();
    @FXML Label creditsDisplay = new Label();
    @FXML Label dateCreatedDisplay = new Label();
    @FXML ChoiceBox weaponSelect = new ChoiceBox();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoreManager.loadProfiles();
        weaponSelect.setValue("Basic Gun");
        weaponSelect.setItems(weaponOptions);
    }

    public void changeScene() {
        MainScene.initializer.startGame();
        switch (weaponSelect.getValue().toString()){
            case "Basic Gun":{
                HamburgerHelper.weapon = new BasicGun(MainScene.root);
                break;
            }
            case "Shotgun":{
                HamburgerHelper.weapon = new ShotGun(MainScene.root);
                break;
            }
            case "Laser Gun":{
                HamburgerHelper.weapon = new LaserGun(MainScene.root);
                break;
            }
            case "Laser Shotgun":{
                HamburgerHelper.weapon = new LaserShotGun(MainScene.root);
                break;
            }
        }

    }

    public void setProfile(){
        scoreManager.selectAccount(profileInput.getText());
        profileNameDisplay.setText("Profile Name: "+scoreManager.activeProfile.playerName);
        highScoreDisplay.setText("Highscore: "+scoreManager.activeProfile.highscore);
        creditsDisplay.setText("Credits: "+scoreManager.activeProfile.credits);
        dateCreatedDisplay.setText("Created On: "+scoreManager.activeProfile.dateCreated);

    }


}
