public class Segment {
    private Point mP0;
    private Point mP1;
    private float mA, mB, mC;

    public static float length(Segment iSegm) {
        return distance2Points (iSegm.mP0, iSegm.mP1);
    }

    private static float distance2Points(Point p0, Point p1) {
        return (float) Math.sqrt(
                Math.pow(p0.x - p1.x, 2) +
                        Math.pow(p0.y - p1.y, 2));
    }

    public Segment(Point iP0, Point iP1) {
        setP(iP0, iP1);
    }

    public Segment(Segment other) {this(other.getP0(), other.getP1());}

    public void setP(Point iP0, Point iP1) {
        this.mP0 = new Point(iP0);
        this.mP1 = new Point(iP1);
        computeABC();
    }

    public void computeABC() {
        mA = -(mP1.y - mP0.y);
        mB = (mP1.x - mP0.x);
        mC = ((mP1.y - mP0.y) * mP0.x - (mP1.x - mP0.x) * mP0.y);
    }

    public Point getP0() {
        return new Point(mP0);
    }

    public Point getP1() {
        return new Point(mP1);
    }

    public float getA() {
        return mA;
    }

    public float getB() {
        return mB;
    }

    public float getC() {
        return mC;
    }

    Point getP(int i) throws RuntimeException {
        if (i < 0 || i > 1) {
            throw new RuntimeException("incorrect input in Line.getP");
        }
        Point p = ((i == 0) ? mP0 : mP1);
        return new Point(p);
    }
}
