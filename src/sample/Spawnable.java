package sample;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
/**
 * set of methods for spawnable objects that adds and removes them from root
 * by David Rogers
 */
public interface Spawnable {

    default void spawn(ImageView sprite, Group root) {
        root.getChildren().add(sprite);
    }

    default void despawn(ImageView sprite, Group root) {
        root.getChildren().remove(sprite);
    }

}
