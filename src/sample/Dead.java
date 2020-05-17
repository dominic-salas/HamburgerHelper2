package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.scoreManager;

public class Dead implements Initializable {
    ObservableList<String> weaponOptions = FXCollections.observableArrayList("Basic Gun", "Shotgun", "Laser Gun", "Laser Shotgun", "Minigun");

    @FXML
    Button respawnButton;
    @FXML
    Button leaveButton;
    @FXML
    ChoiceBox weaponSelect = new ChoiceBox();
    @FXML
    Label scoreValue = new Label();
    @FXML
    Label highscore = new Label();

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weaponSelect.setValue("Basic Gun");
        weaponSelect.setItems(weaponOptions);
        /* //TODO fix this so that printing scores to the screen works without returning an error
        scoreValue.setText("Credits: "+ scoreManager.activeProfile.credits);
        if (ScoreManager.activeProfile.credits >= scoreManager.activeProfile.highscore){
            highscore.setText("You beat your highscore! \nNew highscore: " + scoreManager.activeProfile.highscore);
        }else {
            highscore.setText("Your highscore:" + scoreManager.activeProfile.highscore);
        }
         */
    }

    public void changeScene() {
        MainScene.initializer.swapScene();
        switch (weaponSelect.getValue().toString()) {
            case "Basic Gun": {
                HamburgerHelper.weapon = new BasicGun(MainScene.root);
                break;
            }
            case "Shotgun": {
                HamburgerHelper.weapon = new ShotGun(MainScene.root);
                break;
            }
            case "Laser Gun": {
                HamburgerHelper.weapon = new LaserGun(MainScene.root);
                break;
            }
            case "Laser Shotgun": {
                HamburgerHelper.weapon = new LaserShotGun(MainScene.root);
                break;
            }
            case "Minigun": {
                HamburgerHelper.weapon = new Minigun(MainScene.root);
                break;
            }
        }
        HamburgerHelper.dead = false;
        HamburgerHelper.waitRestart = true;
    }

    public void exitGame() {
        System.exit(0);
    }
}
