package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

import static sample.EnemyFactory.basicImage;

public class BasicEnemy extends Enemy {
    private double xpos;
    private double ypos;
    public Timeline timeline = new Timeline();

    public BasicEnemy(Group root, double xpos, double ypos) {
        lives = 3;
        sprite = new ImageView();
        sprite.setFitWidth(67.158);
        sprite.setFitHeight(45.162);
        this.xpos = xpos;
        this.ypos = ypos;
        sprite.setY(ypos);
        sprite.setX(xpos);
        sprite.setImage(basicImage);
        hitbox = new Rectangle(sprite.getX() + 12.5, sprite.getY() + 1, 45.5, 43);
        root.getChildren().add(sprite);
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (sprite != null && hitbox != null) {
                            try {
                                chase();
                                for (int i = 0; i < Weapon.projectiles.size(); i++) {
                                    if (hitbox.getBoundsInParent().intersects(Weapon.projectiles.get(i).sprite.getBoundsInParent())) {
                                        dropHealth(Weapon.projectiles.get(i).damage, root, BasicEnemy.this);
                                        Weapon.projectiles.get(i).despawn();
                                    }
                                }
                                xSpeed = 0;
                                ySpeed = 0;
                                hitbox.setX(sprite.getX() + 12.5);
                                hitbox.setY(sprite.getY() + 1);
                                hitbox.relocate(sprite.getX() + 12.5, sprite.getY() + 1);
                            } catch (java.lang.NullPointerException ignore) {
                            }
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    private void attack() {
    }

    private void shoot() {
    }

    public void convertMotion() {
        xpos -= Obstacle.xSpeed;
        ypos -= Obstacle.ySpeed;
    }

    private void chase() {
        convertMotion();
        if (sprite.getX() - 16.7895 < 237.5) {
            xSpeed += 1;
        } else if (sprite.getX() - 16.7895 > 237.5) {
            xSpeed -= 1;
        }
        if (sprite.getY() - 22.581 < 217.5) {
            ySpeed += 1;
        } else if (sprite.getY() - 22.581 > 217.5) {
            ySpeed -= 1;
        }

        xpos += xSpeed;
        ypos += ySpeed;
        sprite.setX(xpos);
        sprite.setY(ypos);
        sprite.relocate(xpos, ypos);
    }

    public void dropHealth(double damage, Group root, Enemy enemy) {
        enemy.lives -= damage;
        if (enemy.lives <= 0) {
            despawn(enemy.sprite, root);
            root.getChildren().remove(hitbox);
            isDead = true;
            sprite = null;
            hitbox = null;
        }
    }
}
