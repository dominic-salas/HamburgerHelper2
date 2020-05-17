package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Manages GUI actions
 * calls scoreManager methods to load, store, and search scoreprofiles
 * displays profile info
 * displays highscores and passes sorting criteria to scoreManager to sort them
 * weaopn shop section
 * launches game
 * By David Rogers
 */

public class Controller implements Initializable {
    static ScoreManager scoreManager = new ScoreManager();
    ObservableList<String> weaponOptions = FXCollections.observableArrayList("Basic Gun", "Shotgun", "Laser Gun", "Laser Shotgun", "Minigun");

    @FXML
    Button searchButton;
    @FXML
    TextField profileInput;
    @FXML
    Label profileNameDisplay = new Label();
    @FXML
    Label highScoreDisplay = new Label();
    @FXML
    Label creditsDisplay = new Label();
    @FXML
    Label dateCreatedDisplay = new Label();
    @FXML
    ChoiceBox weaponSelect = new ChoiceBox();


    /**
     * makes scoremanager load profiles in when GUI starts up
     * fills weapon choicebox
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoreManager.loadProfiles();
        weaponSelect.setValue("Basic Gun");
        weaponSelect.setItems(weaponOptions);
    }

    /**
     * when start game button is pressed, set handy's weapon selection
     * makes scene initializer change scene to game map
     */
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
            case "Minigun":{
                HamburgerHelper.weapon = new Minigun(MainScene.root);
                break;
            }
        }
    }

    /**
     * displays text on GUI including all profile fields
     */
    public void setProfile(){
        scoreManager.selectAccount(profileInput.getText());
        profileNameDisplay.setText("Profile Name: "+scoreManager.activeProfile.playerName);
        highScoreDisplay.setText("Highscore: "+scoreManager.activeProfile.highscore);
        creditsDisplay.setText("Credits: "+scoreManager.activeProfile.credits);
        dateCreatedDisplay.setText("Created On: "+scoreManager.activeProfile.dateCreated);

    }

}
