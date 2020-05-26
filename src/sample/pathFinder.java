package sample;


import com.sun.tools.javac.Main;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class pathFinder {
    static Boolean[][] gridMap = new Boolean[62][]; //x, y
    Rectangle pointChecker = new Rectangle(0,0,10,10);
    Boolean freePos;
    private Point2D target = new Point2D(0,0);
    private Rectangle targetBox = new Rectangle();
    private Point2D targetArrayXY = new Point2D(0,0);
    private final Point2D origin = new Point2D(28,26);
    private static int counter = 0;
    private ArrayList<Rectangle> path = new ArrayList<>();
    ArrayList<Point2D> pathPoints= new ArrayList<Point2D>();

    public pathFinder() {
        for (int i = 0; i < 62; i++) {
            gridMap[i] = new Boolean[62];
        }
    }

    public ArrayList<Point2D> findPath(Point2D target){ //DFS
        checkMap(target);
        path.clear();
        pathPoints.clear();
        System.out.println(extendPath(origin));

        path.forEach(Rectangle->{
            pathPoints.add(new Point2D(Rectangle.getX(),Rectangle.getY()));
            //MainScene.root.getChildren().add(Rectangle);
        });
        return pathPoints;
    }

    public void checkMap(Point2D target){
        targetBox.setX(target.getX());
        targetBox.setY(target.getY());
        targetBox.setHeight(10);
        targetBox.setWidth(10);
        for (int i = 0; i <62; i++) { //change x
            System.out.println("\n");
            for (int j = 0; j < 62; j++) { //nested loop go brrr
                if(i==0||i==61||j==0||j==61){ // make solid border
                    freePos=false;
                }else{
                    pointChecker.relocate(i*10,j*10); //change y
                    freePos = true;
                    if(targetBox.getBoundsInParent().intersects(pointChecker.getBoundsInParent())){
                        targetArrayXY = new Point2D(i,j);
                    }
                    MapMaker.obstacles.forEach(obstacle -> {
                        if(obstacle.sprite.getBoundsInParent().intersects(pointChecker.getBoundsInParent())){
                            freePos = false;
                            if(targetBox.getBoundsInParent().intersects(obstacle.sprite.getBoundsInParent())){
                                targetArrayXY = origin; //if clicks ontop of obstacle, make it like clicked ontop of origin, effectively cancellling request for new shots
                            }
                        }
                    });
                }gridMap[i][j]=freePos;
            }
        }
        for (int i = 0; i < 62; i++) {
            System.out.println();
            for (int j = 0; j < 62; j++) {
                if(gridMap[j][i]){
                    System.out.print("- ");
                }else {
                    System.out.print("+ ");
                }
            }
        }

    }



    private boolean extendPath(Point2D currentPos){
        counter++;
        System.out.println(counter+":: "+currentPos.getX()+" : "+currentPos.getY());
        if((currentPos.getY()==targetArrayXY.getY())&&(currentPos.getX()==targetArrayXY.getX())){ //base case, if this be it, then return true;
            return true;
        }
        gridMap[(int)currentPos.getX()][(int)currentPos.getY()] = false; // this current space is taken, so the next one wont come back

        boolean u = tryUP(currentPos);
        boolean d = tryDown(currentPos);
        boolean r = tryRight(currentPos);
        boolean l = tryLeft(currentPos);

        if((!u)&&(!d)&&(!r)&&(!l)){ // if you cant go anywhere
            gridMap[(int)currentPos.getX()][(int)currentPos.getY()] = true;
            return false; //go back a layer

        }

        if(currentPos.getX()>=targetArrayXY.getX()&&currentPos.getY()<=targetArrayXY.getY()){ //if target is down left
            return (DownLeft(currentPos,u,d,r,l));
        }else if(currentPos.getX()>=targetArrayXY.getX()&&currentPos.getY()>=targetArrayXY.getY()){ //if target is up left
            return (UpLeft(currentPos,u,d,r,l));
        }else if(currentPos.getX()<=targetArrayXY.getX()&&currentPos.getY()<=targetArrayXY.getY()){ //if target is down right
            return (DownRight(currentPos,u,d,r,l));
        }else if(currentPos.getX()<=targetArrayXY.getX()&&currentPos.getY()>=targetArrayXY.getY()){ //if target is up right
            return (UpRight(currentPos,u,d,r,l));
        }


        if(u&&extendPath(moveUP(currentPos))){ //if you can go up
            MainScene.root.getChildren().add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
           return true;
        }else if(d&&extendPath(moveDown(currentPos))){
            MainScene.root.getChildren().add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going down
        }else if(r&&extendPath(moveRight(currentPos))){
            MainScene.root.getChildren().add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going right
        }else if(l&&extendPath(moveLeft(currentPos))){
            MainScene.root.getChildren().add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going down
        }
        return false;

    }
 //these check if bordering cells are open
private boolean tryUP(Point2D currentPos){
    return (gridMap[(int)currentPos.getX()][(int)currentPos.getY()-1]);
}
private boolean tryDown(Point2D currentPos){
    return (gridMap[(int)currentPos.getX()][(int)currentPos.getY()+1]);
}
private boolean tryLeft(Point2D currentPos){
    return (gridMap[(int)currentPos.getX()-1][(int)currentPos.getY()]);
}
private boolean tryRight(Point2D currentPos){
    return (gridMap[(int)currentPos.getX()+1][(int)currentPos.getY()+1]);
}

    //reorders direction movement priority for more efficiency
public boolean UpRight(Point2D currentPos, boolean u, boolean d, boolean r, boolean l){
    if(u&&extendPath(moveUP(currentPos))){ //if you can go up
        path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
        return true;
    }else if(r&&extendPath(moveRight(currentPos))){
        path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
        return true; //try going right
    }else if(d&&extendPath(moveDown(currentPos))){
        path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
        return true; //try going down
    }else if(l&&extendPath(moveLeft(currentPos))){
        path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
        return true; //try going left
    }
    return false;
}

    public boolean UpLeft(Point2D currentPos, boolean u, boolean d, boolean r, boolean l){
        if(u&&extendPath(moveUP(currentPos))){ //if you can go up
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true;
        }else if(l&&extendPath(moveLeft(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going left
        }else if(r&&extendPath(moveRight(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going right
        }else if(d&&extendPath(moveDown(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going down
        }
        return false;
    }

    public boolean DownRight(Point2D currentPos, boolean u, boolean d, boolean r, boolean l){
        if(d&&extendPath(moveDown(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going down
        }else if(r&&extendPath(moveRight(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going right
        }else if(u&&extendPath(moveUP(currentPos))){ //if you can go up
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true;
        }else if(l&&extendPath(moveLeft(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going down
        }
        return false;
    }
    public boolean DownLeft(Point2D currentPos, boolean u, boolean d, boolean r, boolean l){
        if(d&&extendPath(moveDown(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going down
        }else if(l&&extendPath(moveLeft(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going down
        }else if(r&&extendPath(moveRight(currentPos))){
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true; //try going right
        }else if(u&&extendPath(moveUP(currentPos))){ //if you can go up
            path.add(new Rectangle(currentPos.getX()*10,currentPos.getY()*10,10,10));
            return true;
        }
        return false;
    }



//this moves current position into next position
    private Point2D moveUP(Point2D currentPos){
        return (new Point2D((int)currentPos.getX(),(int)currentPos.getY()-1));
    }
    private Point2D moveDown(Point2D currentPos){
        return (new Point2D((int)currentPos.getX(),(int)currentPos.getY()+1));
    }
    private Point2D moveLeft(Point2D currentPos){
        return (new Point2D((int)currentPos.getX()-1,(int)currentPos.getY()));
    }
    private Point2D moveRight(Point2D currentPos){
        return (new Point2D((int)currentPos.getX()+1,(int)currentPos.getY()));
    }


}