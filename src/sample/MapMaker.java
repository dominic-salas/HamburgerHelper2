package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

/**
 * Mapmaker for spawning in obstacles in the map
 * by Dominic Salas
 */
public class MapMaker {
    static Image obsImage = new Image("brick_wall.png");
    public double ySpeed;
    public double xSpeed;
    private int xRand;
    private int yRand;
    private Random rand = new Random();
    private Obstacle[][] mapSpread = new Obstacle[6][6];
    public static ArrayList<Obstacle> obstacles = new ArrayList<>();
    public static int possibleX = 0;
    public static int possibleY = 0;
    private HamburgerHelper handy;
    private Timeline timeline;

    /**
     * Mapmaker object.
     *
     * @param handy to prevent handy from passing through obstacles
     */
    public MapMaker(HamburgerHelper handy) {
        timeline = new Timeline();
        this.handy = handy;
        PowerUpFactory.powerCheck.setHeight(30);
        PowerUpFactory.powerCheck.setWidth(30);
        timeline.setCycleCount(Animation.INDEFINITE);
/**
 * timeline for moving all obstacles according to handy movement
 * have to keep all obstacles in central timeline because they dont stay coordinated in movement otherwise
 * checks motion input, determines if it will cause collision, permits movement that wont cause collision
 * By David Rogers
 */
        KeyFrame action = new KeyFrame(Duration.seconds(.0080),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) { //this is my pride and joy please don't break this. It took me 3 hours to get collisions working
                        handy.convertInput(); //get inputs
                        if (!PowerUpFactory.powerCheckValid) {
                            possibleX = rand.nextInt(600);
                            possibleY = rand.nextInt(600);
                            PowerUpFactory.powerCheck.setX(possibleX - 15);
                            PowerUpFactory.powerCheck.setY(possibleY - 15);
                        }
                        for (int i = 0; i < obstacles.size(); i++) {
                            if (obstacles.get(i).sprite.getBoundsInParent().intersects(PowerUpFactory.powerCheck.getBoundsInParent())) {
                                i = obstacles.size();
                            } else {
                                PowerUpFactory.powerCheckValid = true;
                            }
                        }
                        obstacles.forEach(Obstacle::predictIntersectHandy); //check if inputs will cause collision and set to 0 if they will
                        obstacles.forEach(Obstacle::convertMotion); //implement movement


                    }
                });
        timeline.getKeyFrames().add(action);
        timeline.play();

    }

    /**
     * creates a grid for the initial spawning of obstacles
     *
     * @param root to spawn obstacles to group
     */
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

    /**
     * initially spawns random obstacles throughout the grid of the map
     *
     * @param root to spawn obstacles to group
     */
    public void initSpawn(Group root) {
        spreadMap(root);
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).sprite.getBoundsInParent().intersects(handy.sprite.getBoundsInParent())) { //gets rid of obstacles that spawn inside of handy
                //System.out.println("spawnkill removed at " + obstacles.get(i).sprite.getX() + "," + obstacles.get(i).sprite.getY() + ", at " + i);
                obstacles.set(i, new Obstacle(1000, 1000, handy, root));
            } else {
                obstacles.get(i).spawn(obstacles.get(i).sprite, root);
            }
            if (obstacles.get(i).sprite.getBoundsInParent().intersects(PowerUpFactory.powerCheck.getBoundsInParent())) {
                PowerUpFactory.powerCheckValid = true;
            }
        }
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).sprite.getX() == 1000) {
                obstacles.remove(i);
                i = -1;
            }
        }
    }

    /**
     * spawns a random amount of obstacles on the right of left sides of the map
     *
     * @param root to spawn obstacles to map
     */
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

    /**
     * spawns a new row of obstacles on the bottom and top portions of the screen.
     * @param root to spawn obstacles to the map
     */
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

    /**
     * makes it possible to spawn double platforms initially
     *
     * @param x    where obstacles should be spawned
     * @param y    where obstacles should be spawned
     * @param root to spawn obstacles to group
     */
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
}
