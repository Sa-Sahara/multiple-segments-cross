public class Box {
    private Point pMin;
    private Point pMax;

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
        return p.x >= getPMin().x
                && p.x <= getPMax().x
                && p.y >= getPMin().y
                && p.y <= getPMax().y;
    }

    public boolean isSegmentInside(Segment s) {
        return (isPointInside(s.getP0()) && isPointInside(s.getP1()));
    }

    public Point getPMin() {
        return new Point(pMin);
    }
    public Point getPMax() {
        return new Point(pMax);
    }

    public float height() {
        return (getPMax().y - getPMin().y);
    }
    public float width() {
        return (getPMax().x - getPMin().x);
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
        Segment[] segments = new Segment[4];
        Point minXMaxY = new Point(minX(),maxY());
        Point maxXMinY = new Point(maxX(),minY());
        segments [0] = new Segment(pMin, minXMaxY);
        segments [1] = new Segment(pMin, maxXMinY);
        segments [2] = new Segment(pMax, minXMaxY);
        segments [3] = new Segment(pMax, maxXMinY);
        return segments;
    }
}
