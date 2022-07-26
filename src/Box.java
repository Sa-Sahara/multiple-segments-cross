public class Box {
    private float x1, y1, x2, y2;
    final private Point pMin;
    final private Point pMax;
    public Box (float otherX1, float otherY1, float otherX2, float otherY2) {
        this.x1 = otherX1;
        this.y1 = otherY1;
        this.x2 = otherX2;
        this.y2 = otherY2;

        pMin = new Point(Math.min(x1, x2), Math.min(y1, y2));
        pMax = new Point(Math.max(x1, x2), Math.max(y1, y2));
    }
    public Point getPMin() {
        return new Point(pMin);
    }
    public Point getPMax() {
        return new Point(pMax);
    }
}
