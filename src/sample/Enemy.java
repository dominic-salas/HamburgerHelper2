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

    public void updateEnemy(Enemy enemy, Group root, ScoreManager scoreManager, HamburgerHelper handy, double hitPadX, double hitPadY) {
        chase(speed);
        for (int i = 0; i < Weapon.projectiles.size(); i++) {
            if (hitbox.getBoundsInParent().intersects(Weapon.projectiles.get(i).sprite.getBoundsInParent())) {
                if(Weapon.projectiles.get(i) instanceof Grenade){
                    ((Grenade) Weapon.projectiles.get(i)).explode();
                }
                else if(!(Weapon.projectiles.get(i) instanceof Explosion)){
                    Weapon.projectiles.get(i).despawn();
                }
                dropHealth(Weapon.projectiles.get(i).damage, root, enemy, true, scoreManager);
            }
        }
        checkAttack(handy, root, enemy, scoreManager);
        xSpeed = 0;
        ySpeed = 0;
        hitbox.setX(sprite.getX() + hitPadX);
        hitbox.setY(sprite.getY() + hitPadY);
        hitbox.relocate(sprite.getX() + hitPadX, sprite.getY() + hitPadY);
        checkBounds(root, enemy, scoreManager);
    }

    /**
     * makes sure Enemy moves with the map
     */
    private void convertMotion() {
        xpos -= Obstacle.xSpeed;
        ypos -= Obstacle.ySpeed;
    }

    /**
     * chases "handy"'s position, at 237.5, 237.5
     */
    public void chase(double speed) {
        convertMotion();
        if (sprite.getX() - 16.7895 < 237.5) {
            xSpeed += speed;
        } else if (sprite.getX() - 16.7895 > 237.5) {
            xSpeed -= speed;
        }
        if (sprite.getY() - 22.581 < 217.5) {
            ySpeed += speed;
        } else if (sprite.getY() - 22.581 > 217.5) {
            ySpeed -= speed;
        }

        xpos += xSpeed;
        ypos += ySpeed;
        sprite.setX(xpos);
        sprite.setY(ypos);
        sprite.relocate(xpos, ypos);
    }

    /**
     * checks if BasicEnemy hits handy
     *
     * @param handy to check if handy is hit
     * @param root  to kill BasicEnemy and delete from group when hit
     */
    public void checkAttack(HamburgerHelper handy, Group root, Enemy enemy, ScoreManager scoreManager) {
        if (hitbox.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) {
            handy.dropHealth(damage, root);
            lives = 0;
            die(root, enemy, false, scoreManager);
        }
    }

    /**
     * decreases health when a projectile hits BasicEnemy
     *
     * @param damage amount of damage, for different bullets
     * @param root   to be passed into die()
     * @param enemy  to list which enemy dies
     */
    public void dropHealth(double damage, Group root, Enemy enemy, boolean shot, ScoreManager scoreManager) {
        enemy.lives -= damage;
        die(root, enemy, shot, scoreManager);
    }

    /**
     * removes BasicEnemy from group, simulating dying
     *
     * @param root  to remove from group
     * @param enemy to know which enemy should die
     */
    public void die(Group root, Enemy enemy, boolean shot, ScoreManager scoreManager) {
        if (enemy.lives <= 0) {
            despawn(enemy.sprite, root);
            root.getChildren().remove(hitbox);
            isDead = true;
            sprite = null;
            hitbox = null;
            if (shot) {
                scoreManager.addScore(scoreAdd);
                scoreManager.storeProfiles();
            }
        }
    }

    /**
     * makes all enemies that are outside of -300, 900 on both x and y despawn and die
     *
     * @param root to remove from group
     */
    public void checkBounds(Group root, Enemy enemy, ScoreManager scoreManager) {
        if (xpos > 900 || xpos < -300 || ypos > 900 || ypos < -300) {
            lives = 0;
            die(root, enemy, false, scoreManager);
        }
    }
}
