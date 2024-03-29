package sample;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Creates 2d array to represent map.
 * take in handy's position, and the clicked position
 * generates path using BFS to find shortest path, without intersecting obstacle from origin to target
 * used to generate projectile path for ELectroGun
 * By David Rogers
 */

public class pathFinder {
    static Boolean[][] gridMap = new Boolean[62][]; //x, y
    Rectangle pointChecker = new Rectangle(0,0,10,10);
    Boolean freePos;
    private Rectangle targetBox = new Rectangle();
    private Point2D targetArrayXY = new Point2D(0,0);
    private Point2D origin = new Point2D(28,26);
    private static int counter = 0;
    private ArrayList<Rectangle> path = new ArrayList<>();
    ArrayList<Point2D> pathPoints= new ArrayList<Point2D>();
    Queue<SearcherNode> posQueue = new LinkedList<>();
    private Point2D[] movements = new Point2D[4];

    public pathFinder() {
        for (int i = 0; i < 62; i++) {
            gridMap[i] = new Boolean[62];
        }
        // four types of movement that will move current position to any of the neighbours
        movements[0] = new Point2D(-1,0); //left
        movements[1] = new Point2D(1,0); //right
        movements[2] = new Point2D(0,-1); //up
        movements[3] = new Point2D(0,1); //down


    }

    /**
     * basic BFS grid search.
     * Uses gridmap, origin, and target to form shortest path from handy to point clicked, without intersecting any obstacles
     * Starting point is set as origin
     * Builds up FIFO queue of neighbouring positions that need to be checked and continues to move/check/add until reaches target position
     * Determines shortest path because tries every path of certain length, before moving onto next path length. (ex. cannot find path of length 10 before all paths of length 9 are tried)
     * Worst case efficiency is 14400 loops. (Was tested and still kept up with timeline)
     *  Determined by N(e), where N is number of positions, and e is number of neighbours per position
     * @param target
     * @return
     */
    public ArrayList<Point2D> findPath2(Point2D target){
        posQueue.clear();

        checkMap(target);
        Point2D currentPos = new Point2D(0,0); // just setup
        SearcherNode currentNode;
        posQueue.add(new SearcherNode(origin,null)); // set start
        while(!posQueue.isEmpty()){
           // System.out.println(posQueue.size());
            currentNode = posQueue.peek();
            currentPos = posQueue.remove().pos; //move to next position in queue
            if(currentPos.equals(targetArrayXY)){
                return backTrace(currentNode);
            }
            gridMap[(int)currentPos.getX()][(int)currentPos.getY()] = false; // mark it as no longer available position
            for (int i = 0; i <4 ; i++) { //try all posible movements
                currentPos= new Point2D(currentPos.getX()+movements[i].getX(),currentPos.getY()+movements[i].getY());
                if (gridMap[(int)currentPos.getX()][(int)currentPos.getY()]){ //if it aint taken
                    posQueue.add(new SearcherNode(currentPos, currentNode)); //add it to the path queue
                    gridMap[(int) currentPos.getX()][(int) currentPos.getY()] = false;
                }
                currentPos= new Point2D(currentPos.getX()-movements[i].getX(),currentPos.getY()-movements[i].getY()); //put it back where it belongs
            }
        }
        return (null); //if no path is found, don't return anything
    }

    /**
     * builds path of points by backtracking through searcher nodes.
     * Find the point that is the target, then find the neighbour that moved to it (prev)
     * do this until you reach the origin, (prev= null).
     * Called when findpath2 finds the target point.
     * @param startPos
     * @return
     */
    public ArrayList<Point2D> backTrace(SearcherNode startPos){
        ArrayList<Point2D> backTrace = new ArrayList<>();
        SearcherNode current = startPos;
        while(current.prev!=null){
            backTrace.add(new Point2D(current.pos.getX()*10,current.pos.getY()*10));
            current=current.prev;
        }
        return backTrace;
    }

    /**
     * Difficult to create array because obstacles are all random positions and sizes (both doubles as well)
     * creates the 2D array as a grid to represent the map.
     * sweeps 10*10 rectangle accross entire map, and for every position that causes an intersection, mark that position in the 2d array as occupied
     * Also determines the location of both the origin and target positions for the pathfinder to use.
     * @param target
     */
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
        for (int i = 0; i < 62; i++) { //log the ascii model of map to console
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



    //CODE BELOW IS NOT USED



    /**
     * Old path finder DFS
     * Faster, but doesn't find shortest path.
     * This is not used at all, but is a demonstration of previous code
     * @param target
     * @return
     */



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
    public boolean UpRight(Point2D currentPos, boolean u, boolean d, boolean r, boolean l) {
        if (u && extendPath(moveUP(currentPos))) { //if you can go up
            path.add(new Rectangle(currentPos.getX() * 10, currentPos.getY() * 10, 10, 10));
            return true;
        } else if (r && extendPath(moveRight(currentPos))) {
            path.add(new Rectangle(currentPos.getX() * 10, currentPos.getY() * 10, 10, 10));
            return true; //try going right
        } else if (d && extendPath(moveDown(currentPos))) {
            path.add(new Rectangle(currentPos.getX() * 10, currentPos.getY() * 10, 10, 10));
            return true; //try going down
        } else if (l && extendPath(moveLeft(currentPos))) {
            path.add(new Rectangle(currentPos.getX() * 10, currentPos.getY() * 10, 10, 10));
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
