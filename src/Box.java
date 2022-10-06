public class Box {
    private static final int BOX_SIDES = 4;
    final private Point pMin;
    final private Point pMax;

    public Box (float iX1, float iY1, float iX2, float iY2) {
        pMin = new Point(Math.min(iX1, iX2), Math.min(iY1, iY2));
        pMax = new Point(Math.max(iX1, iX2), Math.max(iY1, iY2));
    }

    public Box (Point p1, Point p2) {
        pMin = new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
        pMax = new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
    }

    public Box (Segment s) {
        this(s.getP0(), s.getP1());
    }

    public boolean isPointInside(Point p) {
        return Float.compare(p.x, this.getPMin().x) >= 0 &&
                Float.compare(p.x, this.getPMax().x) <= 0 &&
                Float.compare(p.y, this.getPMin().y) >= 0 &&
                Float.compare(p.y, this.getPMax().y) <= 0;
    }

    public boolean isSegmentInside(Segment s) {
        return (this.isPointInside(s.getP0()) && this.isPointInside(s.getP1()));
    }

    public Point getPMin() {
        return new Point(pMin);
    }
    public Point getPMax() {
        return new Point(pMax);
    }

    public float boxHeight () {
        return (this.getPMax().y - this.getPMin().y);
    }
    public float boxWidth () {
        return (this.getPMax().x - this.getPMin().x);
    }

    public float minX(){
        return pMin.x;
    }
    public float maxX(){
        return pMax.x;
    }
    public float minY(){
        return pMin.y;
    }
    public float maxY(){
        return pMax.y;
    }


    public Segment[] createBoxSides(){
        Segment[] segments = new Segment[BOX_SIDES];
        Point minXMaxY = new Point(minX(),maxY());
        Point maxXMinY = new Point(maxX(),minY());
        segments [0] = new Segment(pMin, minXMaxY);
        segments [1] = new Segment(pMin, maxXMinY);
        segments [2] = new Segment(pMax, minXMaxY);
        segments [3] = new Segment(pMax, maxXMinY);
        return segments;
    }
}
