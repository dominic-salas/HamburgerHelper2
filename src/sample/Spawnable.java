package sample;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public interface Spawnable {

    default void spawn(ImageView sprite, Group root) {
        root.getChildren().add(sprite);
    }

    default void despawn(ImageView sprite, Group root) {
        root.getChildren().remove(sprite);
    }
}
