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

    public Cross crossSearch(Segment iSegment1, Segment iSegment2) {
        Cross cross = new Cross();
        Point tmpP = findCrossPoint(iSegment1, iSegment2);
        if (Float.isFinite(tmpP.x) && Float.isFinite(tmpP.y)) {
            cross = new Cross(tmpP, isCrossing(iSegment1.getLine(), iSegment2) &&
                    isCrossing(iSegment2.getLine(), iSegment1));
        } else if (Float.isNaN(tmpP.x) && Float.isNaN(tmpP.y)) {
            switch (mMode) {
                case MULTIPLE:
                    cross = caseMultipleCross(iSegment1, iSegment2);
                    break;
                case SINGLE:
                    cross = caseSingleCross(iSegment1, iSegment2);
                    break;
                default:
                    System.out.println("Incorrect Mode input, calculation proceeded in MULTIPLE");
                    cross = caseMultipleCross(iSegment1, iSegment2);
            }
        }
        return cross;
    }

    public Cross crossSearch(Segment iShortest, Box iBox) {
        Cross cross = new Cross();
        Segment[] boxSides = iBox.createBoxSides();
        for (Segment s : boxSides) {
            cross = crossSearch(iShortest, s);
            if (cross.ok)
                break;
        }
        return cross;
    }

    private boolean isSamePoint(Segment iSegment) {
        return iSegment.getP1().equals(iSegment.getP0());
    }

    private Point findCrossPoint(Segment iSegment1, Segment iSegment2) {
        Line line1 = iSegment1.getLine();
        Line line2 = iSegment2.getLine();

        float denominator = line1.getA() * line2.getB() - line2.getA() * line1.getB();
        float y = (line1.getC() * line2.getA() - line2.getC() * line1.getA()) /
                denominator;
        float x = (line1.getB() * line2.getC() - line2.getB() * line1.getC()) /
                denominator;
        return new Point(x, y);
    }

    private boolean isInsideSegment(Point center, Segment iSegment) {
        return new Box(iSegment).isPointInside(center);
    }

    private Cross caseMultipleCross(Segment iSegment1, Segment iSegment2) {
        Cross cross = new Cross();
        for (int i = 0; i < Segment.POINTS_IN_SEGMENT; i++) {
            if (isInsideSegment(iSegment1.getP(i), iSegment2)) {
                cross.p = new Point(iSegment1.getP(i));
                cross.ok = true;
                break;
            }
            if (isInsideSegment(iSegment2.getP(i), iSegment1)) {
                cross.p = new Point(iSegment2.getP(i));
                cross.ok = true;
                break;
            }
        }
        return cross;
    }

    private Cross caseSingleCross(Segment iSegment1, Segment iSegment2) {
        Cross cross = new Cross();
        if (isSamePoint(iSegment1) && isInsideSegment(iSegment1.getP0(), iSegment2)) {
            cross.p = new Point(iSegment1.getP0());
            cross.ok = true;
        }
        if (isSamePoint(iSegment2) && isInsideSegment(iSegment2.getP0(), iSegment1)) {
            cross.p = new Point(iSegment2.getP0());
            cross.ok = true;
        }

        for (int i = 0; i < Segment.POINTS_IN_SEGMENT; i++) {
            for (int j = 0; j < Segment.POINTS_IN_SEGMENT; j++) {
                if (iSegment1.getP(i).equals(iSegment2.getP(j)) &&
                        isInsideSegment(iSegment2.getP(1 - j), iSegment1)) {
                    cross.p = new Point(iSegment1.getP(i));
                    cross.ok = true;
                }
            }
        }
        return cross;
    }

    private boolean isCrossing(Line l, Segment s) {
        return Float.compare(
                l.substitutePoint(s.getP0()) *
                l.substitutePoint(s.getP1()), 0)
                <= 0;
    }
}

