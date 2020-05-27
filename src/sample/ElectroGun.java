package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ElectroGun extends Weapon {
    public ImageView sprite;
    public double bulletSpeed = 0;
    static Image boltImage = new Image("blueBolt.png");
    private pathFinder pf = new pathFinder();
    private ArrayList<Point2D> points = new ArrayList<>();
    public ElectroGun(Group root) {
        super(root);


    }
    @Override
    public void shoot() {
        points=pf.findPath2(new Point2D(UserInput.mousePosX-18,UserInput.mousePosY-40));
        if(!points.isEmpty()){
            points.forEach(point2D -> {
                ElectroBolt ElectroBolt = new ElectroBolt(point2D.getX(),point2D.getY(),root);
                projectiles.add(ElectroBolt);
            });
        }
        points.clear();
    }
}
