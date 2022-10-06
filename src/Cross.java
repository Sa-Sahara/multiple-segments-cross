public class Cross {
    Point p;
    boolean ok;

    Cross(){
        this.p = new Point(Point.DEFAULT_COORDINATE, Point.DEFAULT_COORDINATE);
        ok = false;
    }

    Cross (Point iP, boolean iOk) {
        this.p = new Point(iP);
        this.ok = iOk;
    }
}
