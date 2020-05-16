package sample;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Enemy abstract class for all types of enemies.
 * by Dominic Salas
 */
public abstract class Enemy implements Spawnable, Killable {
    public double xpos;
    public double ypos;
    public ImageView sprite;
    public int lives;
    public Rectangle hitbox;
    int damage;
    int scoreAdd;
    public boolean noXMovement;
    public boolean noYMovement;
    public double ySpeed = 0;
    public double xSpeed = 0;
    public double speed;
    public boolean isDead = false;

    private void attack() {
    }

    private void chase() {
    }

    /**
     * checks if BasicEnemy hits handy
     *
     * @param handy to check if handy is hit
     * @param root  to kill BasicEnemy and delete from group when hit
     */
    public void checkAttack(HamburgerHelper handy, Group root, Enemy enemy) {
        if (hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) {
            handy.dropHealth(damage, root);
            lives = 0;
            die(root, enemy);
        }
    }

    /**
     * decreases health when a projectile hits BasicEnemy
     *
     * @param damage amount of damage, for different bullets
     * @param root   to be passed into die()
     * @param enemy  to list which enemy dies
     */
    public void dropHealth(double damage, Group root, Enemy enemy, boolean shot, ScoreManager scoreManager) { //put this in an abstract class or something
        if (shot) {
            scoreManager.addScore(scoreAdd);
            scoreManager.storeProfiles();
        }
        enemy.lives -= damage;
        die(root, enemy);
    }

    /**
     * removes BasicEnemy from group, simulating dying
     *
     * @param root  to remove from group
     * @param enemy to know which enemy should die
     */
    public void die(Group root, Enemy enemy) {
        if (enemy.lives <= 0) {
            despawn(enemy.sprite, root);
            root.getChildren().remove(hitbox);
            isDead = true;
            sprite = null;
            hitbox = null;
        }
    }

    /**
     * makes all enemies that are outside of -300, 900 on both x and y despawn and die
     *
     * @param root to remove from group
     */
    public void checkBounds(Group root, Enemy enemy) {
        if (xpos > 900 || xpos < -300 || ypos > 900 || ypos < -300) {
            lives = 0;
            die(root, enemy);
        }
    }
}
