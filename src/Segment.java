public class Segment {
    public static final int POINTS_IN_SEGMENT = 2;
    private Point mP0;
    private Point mP1;
    private Line mLine;

    public float length() {
        return mP0.dist(mP1);
    }

    public Segment(Point iP0, Point iP1) {
        setP(iP0, iP1);
    }

    public Segment(Segment other) {
        setP(other.getP0(), other.getP1());
    }

    public void setP(Point iP0, Point iP1) {
        mP0 = new Point(iP0);
        mP1 = new Point(iP1);
        mLine = new Line(iP0, iP1);
    }

    public Point getP0() {
        return new Point(mP0);
    }

    public Point getP1() {
        return new Point(mP1);
    }

    Point getP(int i) throws RuntimeException {
        if (i < 0 || i > 1) {
            throw new RuntimeException("incorrect input in Line.getP");
        }
        Point p = ((i == 0) ? mP0 : mP1);
        return new Point(p);
    }

    public Line getLine() {
        return new Line(mLine);
    }
}
