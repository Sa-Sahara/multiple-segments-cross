public class GUISegment {
    private Point mP0;
    private Point mP1;

    public GUISegment(Point iP0, Point iP1) {
        this.mP0 = new Point(iP0);
        this.mP1 = new Point(iP1);
    }
    public Point getP0() {
        return new Point(mP0);
    }

    public Point getP1() {
        return new Point(mP1);
    }
}
