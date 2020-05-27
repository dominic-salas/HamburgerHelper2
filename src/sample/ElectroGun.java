package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Unique weapon that uses pathfinder to create bolt of electroBolt projectiles along path, avoiding all obstacles
 * basically a point and click gun for killing all enemies between handy and target clicked on
 * By David Rogers
 */
public class ElectroGun extends Weapon {
    public ImageView sprite;
    public double bulletSpeed = 0;
    static Image boltImage = new Image("blueBolt.png");
    private pathFinder pf = new pathFinder();
    private ArrayList<Point2D> points = new ArrayList<>();
    public ElectroGun(Group root) {
        super(root);

    }

    /**
     * spawns a series of electroBolt projectiles along path returned by pathfinder
     */
    @Override
    public void shoot() {
        points=pf.findPath2(new Point2D(UserInput.mousePosX-18,UserInput.mousePosY-40));
        if(!points.isEmpty()){ //if a path is created, spawn projectiles along each point in list
            points.forEach(point2D -> {
                ElectroBolt ElectroBolt = new ElectroBolt(point2D.getX(),point2D.getY(),root);
                projectiles.add(ElectroBolt);
            });
        }
        points.clear(); //remove points for next call
    }
}
