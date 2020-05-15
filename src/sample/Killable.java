package sample;

import javafx.scene.Group;

public interface Killable extends Spawnable {
    private void dropHealth(double damage, Group root, Enemy enemy) {
    }

    private void checkDeath(int health) {
    }
}
