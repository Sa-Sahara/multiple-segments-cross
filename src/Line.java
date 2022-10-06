public class Line {
    private float mA, mB, mC;

    public Line(Point p0, Point p1) {
        mA = -(p1.y - p0.y);
        mB = p1.x - p0.x;
        mC = (p1.y - p0.y) * p0.x - (p1.x - p0.x) * p0.y;
    }

    public Line(Line other){
        this.mA = other.mA;
        this.mB = other.mB;
        this.mC = other.mC;
    }

    public float substitutePoint(Point p) {
        return mA * p.x + mB * p.y + mC;
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
}
