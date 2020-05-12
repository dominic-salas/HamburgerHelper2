package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class MapMaker {
    public double exploredXMax;
    public double exploredXMin;
    public double exploredYMax;
    public double exploredYMin;
    public double ySpeed;
    public double xSpeed;

    public int obstaclesLength;
    private int xRand;
    private int yRand;
    private Random rand = new Random();
    private Obstacle[][] mapSpread = new Obstacle[6][6];
    public ArrayList<Obstacle> obstacles = new ArrayList<>();
    private HamburgerHelper handy;
    private Timeline timeline;

    public MapMaker(HamburgerHelper handy){
        timeline = new Timeline();
        this.handy = handy;
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) { //this is my pride and joy please don't break this. It took me 4 hours to get collisions working
                        handy.convertInput(); //get inputs
                        obstacles.forEach(Obstacle::predictIntersect); //check if inputs will cause collision and set to 0 if they will
                        obstacles.forEach(Obstacle::convertMotion); //implement movement

                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();

    }

    private void spreadMap(Group root) {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                initMapSpawn(x, y, root);
                if (mapSpread[x][y] != null) {
                    obstacles.add(mapSpread[x][y]);
                }
            }
        }
    }

    public void initSpawn(Group root) {
        spreadMap(root);
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).sprite.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) { //gets rid of obstacles that spawn inside of handy
                //System.out.println("spawnkill removed at " + obstacles.get(i).sprite.getX() + "," + obstacles.get(i).sprite.getY() + ", at " + i);
                obstacles.set(i, new Obstacle(1000, 1000, handy, root));
            } else {
                obstacles.get(i).spawn(obstacles.get(i).sprite, root);
            }
        }
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).sprite.getX() == 1000) {
                obstacles.remove(i);
                i = -1;
            }
        }
    }

    public void spawnNewColumn(Group root) {
        int platformNum = rand.nextInt(2) + 3;
        Obstacle newObs;
        if (Obstacle.xSpeed >= -1) {
            for (int i = 0; i < platformNum; i++) {
                newObs = new Obstacle(650, rand.nextInt(500) + 100, handy, root);
                obstacles.add(newObs);
                newObs.spawn(newObs.sprite, root);
            }
        }
        if (Obstacle.xSpeed <= 1) {
            for (int i = 0; i < platformNum; i++) {
                newObs = new Obstacle(-50, rand.nextInt(500) + 100, handy, root);
                obstacles.add(newObs);
                newObs.spawn(newObs.sprite, root);
            }
        }
    }


    public void spawnNewRow(Group root) {
        int platformNum = rand.nextInt(2) + 3;
        Obstacle newObs;
        if (Obstacle.ySpeed >= -1) {
            for (int i = 0; i < platformNum; i++) {
                newObs = new Obstacle(rand.nextInt(500) + 100, 650, handy, root);
                obstacles.add(newObs);
                newObs.spawn(newObs.sprite, root);
            }
        }
        if (Obstacle.ySpeed <= 1) {
            for (int i = 0; i < platformNum; i++) {
                newObs = new Obstacle(rand.nextInt(500) + 100, -50, handy, root);
                obstacles.add(newObs);
                newObs.spawn(newObs.sprite, root);
            }
        }
    }

    private void initMapSpawn(int x, int y, Group root) {
        int expandInt = rand.nextInt(4);
        if (rand.nextInt(4) == 1) {
            xRand = rand.nextInt(8);
            yRand = rand.nextInt(8);
            mapSpread[x][y] = new Obstacle((x * 100) + xRand, (y * 100) + yRand, handy, root);
            if (rand.nextInt(3) == 0) { //chance for a platform to take up two spaces of mapSpread
                if (expandInt == 0) { //platform spreads at x integer.
                    mapSpread[x][y].sprite.setFitWidth(rand.nextInt(50) + 150);
                    //System.out.println("Double x spawned at " + mapSpread[x][y].sprite.getX() + ", " + mapSpread[x][y].sprite.getY());
                } else if (expandInt == 1) { //platform spreads at y integer.
                    mapSpread[x][y].sprite.setFitHeight(rand.nextInt(50) + 150);
                    //System.out.println("Double y spawned at " + mapSpread[x][y].sprite.getX() + ", " + mapSpread[x][y].sprite.getY());
                }
            }
        }
    }

    public void avoidLag(Group root) {
        int random = rand.nextInt(obstacles.size());
        if (obstacles.size() >= 35) {
            obstacles.get(random).despawn(obstacles.get(random).sprite, root);
            obstacles.remove(random);
        }
    }
}
