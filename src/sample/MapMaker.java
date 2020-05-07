package sample;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Random;

public class MapMaker {
    public double exploredXMax;
    public double exploredXMin;
    public double exploredYMax;
    public double exploredYMin;
    public double ySpeed;
    public double xSpeed;
    private int xRand;
    private int yRand;
    private Random rand = new Random();
    private Obstacle[][] mapSpread = new Obstacle[6][6];
    public ArrayList<Obstacle> obstacles = new ArrayList<>();

    private void spreadMap() {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                if (rand.nextInt(4) == 1) {
                    xRand = rand.nextInt(8);
                    yRand = rand.nextInt(8);
                    mapSpread[x][y] = new Obstacle((x * 100) + xRand, (y * 100) + yRand);
                }
                if (mapSpread[x][y] != null) {
                    obstacles.add(mapSpread[x][y]);
                }
            }
        }
    }

    public void initSpawn(Group root) {
        spreadMap();
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).spawn(obstacles.get(i).sprite, root);
        }
    }

    public void spawnNewColumn() {

    }

    public void spawnNewRow() {
    }
}
