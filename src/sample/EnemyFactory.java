package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.util.Random;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class EnemyFactory {
    static Image basicImage = new Image("/Grimace.png");

    public int lives;
    public double ySpeed;
    public double xSpeed;
    private Timeline timeline;
    Random rand = new Random();
    static ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyFactory(Group root, HamburgerHelper handy) {
        spawnInit(root, handy);
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        for (int i = 0; i < enemies.size(); i++) {
                            if (enemies.get(i).isDead) {
                                spawnNew(root, handy);
                                enemies.remove(i);
                            }
                            /*try {
                                noOverlap(enemies.get(i), enemies.get(i+1));
                            }catch (java.lang.IndexOutOfBoundsException e){
                                noOverlap(enemies.get(i), enemies.get(0));
                            }

                             */
                        }
                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();
    }

    public void spawnNew(Group root, HamburgerHelper handy) {
        enemies.add(new BasicEnemy(root, handy, rand.nextInt(700), rand.nextInt(700)));
    }

    private void spawnInit(Group root, HamburgerHelper handy) {
        for (int i = 0; i < 4; i++) {
            spawnNew(root, handy);
        }
    }
    /*
    private void noOverlap(Enemy enemy, Enemy enemy2){
        if (enemy.sprite.getBoundsInParent().intersects(enemy2.sprite.getBoundsInParent())){
            int negativeSwitch = 1;
            if (rand.nextInt(1) == 0){
                negativeSwitch = -1;
            }
            if (rand.nextInt(1) == 1){
                enemy.sprite.relocate(enemy.sprite.getX(), enemy2.sprite.getY() + (50 * negativeSwitch));
            }else{
                enemy.sprite.relocate(enemy2.sprite.getX() + (50 * negativeSwitch), enemy.sprite.getY());
            }
        }
    }

     */
}
