package sample;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public interface Spawnable {
    void spawn(ImageView sprite, Group root);

    private void despawn(ImageView sprite, Group root) {
    }
}
