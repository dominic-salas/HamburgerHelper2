package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.startGUIController.scoreManager;

/**
 * DeadGUIController is a controller for the death screen the player sees when handy dies without an extra life.
 * by Dominic Salas
 */
public class DeadGUIController implements Initializable {
    ObservableList<String> weaponOptions = FXCollections.observableArrayList("Basic Gun -- Free", "Shotgun -- 5,000", "Laser Gun -- 12,500", "Laser Shotgun -- 25,000", "Minigun -- 100,000", "RPG -- 100,000", "ElectroGun -- 150,000");
    String weaponNameHolder = "Basic Gun -- Free";

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
    @FXML
    Button buyWeaponButton;

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
        weaponSelect.setValue("Basic Gun -- Free");
        weaponSelect.setItems(weaponOptions);
        GameInitializer.deathController = this;
        printScores();
    }

    /**
     * Replays the game and makes sure handy starts with the weapon the player selects
     */
    public void changeScene() {
        MainScene.initializer.swapScene();
        switch (weaponSelect.getValue().toString()) {
            case "Basic Gun -- Free": {
                HamburgerHelper.weapon = new BasicGun(MainScene.root);
                break;
            }
            case "Shotgun -- 5,000": {
                HamburgerHelper.weapon = new ShotGun(MainScene.root);
                break;
            }
            case "Laser Gun -- 12,500": {
                HamburgerHelper.weapon = new LaserGun(MainScene.root);
                break;
            }
            case "Laser Shotgun -- 25,000": {
                HamburgerHelper.weapon = new LaserShotGun(MainScene.root);
                break;
            }
            case "Minigun -- 100,000": {
                HamburgerHelper.weapon = new Minigun(MainScene.root);
                break;
            }
            case "RPG -- 100,000": {
                HamburgerHelper.weapon = new RPG(MainScene.root);
            }
        }
        HamburgerHelper.dead = false;
        HamburgerHelper.waitRestart = true;
    }

    public void buyWeapon() {
        if (ScoreManager.activeProfile == null) {
            weaponSelect.setValue("Basic Gun -- Free");
            return;
        }
        switch (weaponSelect.getValue().toString()) {
            case "Basic Gun -- Free": {
                HamburgerHelper.weapon = new BasicGun(MainScene.root);
                break;
            }
            case "Shotgun -- 5,000": {
                if (ScoreManager.activeProfile.credits >= 5000 && !weaponNameHolder.equals(weaponSelect.getValue().toString())) {
                    ScoreManager.activeProfile.credits -= 5000;
                    HamburgerHelper.weapon = new ShotGun(MainScene.root);
                    break;
                } else {
                    weaponSelect.setValue(weaponNameHolder);
                }
            }
            case "Laser Gun -- 12,500": {
                if (ScoreManager.activeProfile.credits >= 12500 && !weaponNameHolder.equals(weaponSelect.getValue().toString())) {
                    ScoreManager.activeProfile.credits -= 12500;
                    HamburgerHelper.weapon = new LaserGun(MainScene.root);
                    break;
                } else {
                    weaponSelect.setValue(weaponNameHolder);
                }
            }
            case "Laser Shotgun -- 25,000": {
                if (ScoreManager.activeProfile.credits >= 25000 && !weaponNameHolder.equals(weaponSelect.getValue().toString())) {
                    ScoreManager.activeProfile.credits -= 25000;
                    HamburgerHelper.weapon = new LaserShotGun(MainScene.root);
                    break;
                } else {
                    weaponSelect.setValue(weaponNameHolder);
                }
            }
            case "Minigun -- 100,000": {
                if (ScoreManager.activeProfile.credits >= 100000 && !weaponNameHolder.equals(weaponSelect.getValue().toString())) {
                    ScoreManager.activeProfile.credits -= 100000;
                    HamburgerHelper.weapon = new Minigun(MainScene.root);
                    break;
                } else {
                    weaponSelect.setValue(weaponNameHolder);
                }
            }
            case "RPG -- 100,000": {
                if (ScoreManager.activeProfile.credits >= 100000 && !weaponNameHolder.equals(weaponSelect.getValue().toString())) {
                    ScoreManager.activeProfile.credits -= 100000;
                    HamburgerHelper.weapon = new RPG(MainScene.root);
                    break;
                } else {
                    weaponSelect.setValue(weaponNameHolder);
                }
            }
            case "ElectroGun -- 150,000": {
                if (ScoreManager.activeProfile.credits >= 150000 && !weaponNameHolder.equals(weaponSelect.getValue().toString())) {
                    ScoreManager.activeProfile.credits -= 150000;
                    HamburgerHelper.weapon = new ElectroGun(MainScene.root);
                    break;
                } else {
                    weaponSelect.setValue(weaponNameHolder);
                }
            }

        }
        weaponNameHolder = weaponSelect.getValue().toString();
        scoreManager.storeProfiles();
    }

    /**
     * prints scores to the screen for the player to see
     * shows if the player beat his/her highscore
     */
    public void printScores() {
        if (ScoreManager.activeProfile != null) {
            scoreValue.setText("Score: " + ScoreManager.activeProfile.score);
            if (ScoreManager.activeProfile.score >= ScoreManager.activeProfile.highscore) {
                highscore.setText("You beat your highscore! New highscore: " + ScoreManager.activeProfile.score);
            } else {
                highscore.setText("Your highscore: " + ScoreManager.activeProfile.highscore);
            }
        }
    }

    public void exitGame() {
        System.exit(0);
    }
}
