public class Box {

    final private Point pMin;
    final private Point pMax;

    public Box (float iX1, float iY1, float iX2, float iY2) {
        pMin = new Point(Math.min(iX1, iX2), Math.min(iY1, iY2));
        pMax = new Point(Math.max(iX1, iX2), Math.max(iY1, iY2));
    }

    public Point getPMin() {
        return new Point(pMin);
    }
    public Point getPMax() {
        return new Point(pMax);
    }
}
