public class Segment {
    private Point mP0;
    private Point mP1;
    private float mA, mB, mC;

    public float length(Segment iSegm) {
        return
                (float) Math.sqrt(
                        Math.pow(iSegm.mP0.mX - iSegm.mP1.mX, 2) +
                        Math.pow(iSegm.mP0.mY - iSegm.mP1.mY, 2));
    }

    public Segment(Point iP0, Point iP1) {
        setP(iP0, iP1);
    }

    public Segment(Segment other) {
        this(other.getP0(), other.getP1());
    }

    public void setP(Point iP0, Point iP1) {
        this.mP0 = new Point(iP0);
        this.mP1 = new Point(iP1);
        computeABC();
    }

    public void computeABC() {
        mA = -(mP1.mY - mP0.mY);
        mB = (mP1.mX - mP0.mX);
        mC = ((mP1.mY - mP0.mY) * mP0.mX - (mP1.mX - mP0.mX) * mP0.mY);
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
