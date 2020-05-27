package sample;

import javafx.geometry.Point2D;

/**
 * simple node to store current location and previous position for pathfinder.
 * Used by backtracker function to move back from target to generate list of points to represent path
 * By David Rogers
 */

public class SearcherNode {
    Point2D pos;
    SearcherNode prev;
    public SearcherNode(Point2D pos, SearcherNode prev){
        this.pos=pos;
        this.prev=prev;
    }
}
