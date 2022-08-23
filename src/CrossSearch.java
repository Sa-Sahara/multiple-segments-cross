import java.util.ArrayList;

public final class CrossSearch {
    private CrossMode mMode;

    public CrossSearch(CrossMode iMode) {
        this.mMode = iMode;
    }

    public void setMode(CrossMode iMode) {
        this.mMode = iMode;
    }

    CrossMode mode() {
        return mMode;
    }

    public int numOfCrosses(ArrayList<Segment> iSegments) {
        int numOfCrosses = 0;
        if (iSegments.size() >= 2) {
            for (int i = 0; i < iSegments.size(); i++) {
                for (int j = i + 1; j < iSegments.size(); j++) {
                    Cross cross = crossSearch(iSegments.get(i), iSegments.get(j));
                    if (cross.ok) {
                        numOfCrosses++;
                    }
                }
            }
        }
        return numOfCrosses;
    }


    public Cross crossSearch(Segment iSegm1, Segment iSegm2) {
        Cross cross = new Cross(new Point(0, 0), false);
        Point tmpP = findCrossPoint(iSegm1, iSegm2);
        if (Float.isFinite(tmpP.x) && Float.isFinite(tmpP.y)) {
            cross = new Cross(tmpP, insideSegment(tmpP, iSegm1) && insideSegment(tmpP, iSegm2));
        } else if (Float.isNaN(tmpP.x) && Float.isNaN(tmpP.y)) {
            switch (mMode) {
                case MULTIPLE:
                    cross = caseMultipleCross(iSegm1, iSegm2);
                    break;
                case SINGLE:
                    cross = caseSingleCross(iSegm1, iSegm2);
                    break;
                default:
                    System.out.println("Incorrect Mode input, calculation proceeded in MULTIPLE");
                    cross = caseMultipleCross(iSegm1, iSegm2);
            }
        }
        return cross;
    }

    private boolean isSamePoint(Segment iSegment) {
        return iSegment.getP1().equals(iSegment.getP0());
    }

    private Point findCrossPoint(Segment iSegm1, Segment iSegm2) {
        float denominator = iSegm1.getA() * iSegm2.getB() - iSegm2.getA() * iSegm1.getB();
        float y = (iSegm1.getC() * iSegm2.getA() - iSegm2.getC() * iSegm1.getA()) /
                denominator;
        float x = (iSegm1.getB() * iSegm2.getC() - iSegm2.getB() * iSegm1.getC()) /
                denominator;
        return new Point(x, y);
    }

    private boolean insideSegment(Point center, Segment iSegment) {
        return betweenPoints(center, iSegment.getP0(), iSegment.getP1());
    }

    private boolean betweenPoints(Point iCenter, Point iP0, Point iP1) {
        return iCenter.x >= Math.min(iP0.x, iP1.x) - Collisions.EPSILON &&
                iCenter.x <= Math.max(iP0.x, iP1.x) + Collisions.EPSILON &&
                iCenter.y >= Math.min(iP0.y, iP1.y) - Collisions.EPSILON &&
                iCenter.y <= Math.max(iP0.y, iP1.y) + Collisions.EPSILON;
    }

    private Cross caseMultipleCross(Segment iSegm1, Segment iSegm2) {
        Point p = new Point(0, 0);
        boolean bool = false;
        for (int i = 0; i < 2; i++) {
            if (insideSegment(iSegm1.getP(i), iSegm2)) {
                p = new Point(iSegm1.getP(i));
                bool = true;
                break;
            }
            if (insideSegment(iSegm2.getP(i), iSegm1)) {
                p = new Point(iSegm2.getP(i));
                bool = true;
                break;
            }
        }
        return new Cross(p, bool);
    }

    private Cross caseSingleCross(Segment iSegm1, Segment iSegm2) {
        Point p = new Point(0, 0);
        boolean bool = false;
        if (isSamePoint(iSegm1) && insideSegment(iSegm1.getP0(), iSegm2)) {
            p = new Point(iSegm1.getP0());
            bool = true;
        }
        if (isSamePoint(iSegm2) && insideSegment(iSegm2.getP0(), iSegm1)) {
            p = new Point(iSegm2.getP0());
            bool = true;
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (iSegm1.getP(i).equals(iSegm2.getP(j)) &&
                        betweenPoints(iSegm1.getP(i), iSegm1.getP(1 - i), iSegm2.getP(1 - j))) {
                    p = new Point(iSegm1.getP(i));
                    bool = true;
                }
            }
        }
        return new Cross(p, bool);
    }
}

