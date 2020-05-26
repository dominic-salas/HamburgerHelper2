package sample;

import javafx.geometry.Point2D;

public class SearcherNode {
    Point2D pos;
    SearcherNode prev;
    public SearcherNode(Point2D pos, SearcherNode prev){
        this.pos=pos;
        this.prev=prev;
    }
}
