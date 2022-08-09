public class Box {

    final private Point pMin;
    final private Point pMax;

    public Box (float iX1, float iY1, float iX2, float iY2) {
        pMin = new Point(Math.min(iX1, iX2), Math.min(iY1, iY2));
        pMax = new Point(Math.max(iX1, iX2), Math.max(iY1, iY2));
    }

    public boolean ifPointInside (Point p) {
        return
                (p.x >= this.getPMin().x &&
                p.x <= this.getPMax().x &&
                p.y >= this.getPMin().y &&
                p.y <= this.getPMax().y);
    }

    public boolean ifSegmInside (Segment s) {
        return (this.ifPointInside(s.getP0()) && this.ifPointInside(s.getP1()));
    }

    public Point getPMin() {
        return new Point(pMin);
    }
    public Point getPMax() {
        return new Point(pMax);
    }
}
