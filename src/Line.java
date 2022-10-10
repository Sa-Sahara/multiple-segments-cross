public class Line {
    float mA, mB, mC;

    public Line(Point p0, Point p1) {
        mA = -(p1.y - p0.y);
        mB = p1.x - p0.x;
        mC = (p1.y - p0.y) * p0.x - (p1.x - p0.x) * p0.y;
    }

    public Line(Line other){
        mA = other.mA;
        mB = other.mB;
        mC = other.mC;
    }

    public float distToPoint(Point p) {
        return mA * p.x + mB * p.y + mC;
    }
}
