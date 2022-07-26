import java.util.ArrayList;

public class CrossSearch {

    public int numOfCrosses(ArrayList<Segment> iSegments) {
        int numOfCrosses = 0;
        int l = iSegments.size();
        while (l > 1) {
            for (int i = 1; i < l; i++) {
                if (isCrossing(iSegments.get(0), iSegments.get(i))) {
                    numOfCrosses++;
                }
            }
            iSegments.remove(0);
            l = iSegments.size();
        }
        return numOfCrosses;
    }
    public boolean isCrossing (Segment iSegm1, Segment iSegm2) {
        Cross cross = new Cross(new Point(0, 0), false);
        Point tmpP = findCrossPoint(iSegm1, iSegm2);
        if (Float.isFinite(tmpP.mX) && Float.isFinite(tmpP.mY)) {
            cross = new Cross(tmpP, betweenLinePoints(tmpP, iSegm1) && betweenLinePoints(tmpP, iSegm2));
        } else if (Float.isNaN(tmpP.mX) && Float.isNaN(tmpP.mY)) {
            cross = caseMultipleCross(iSegm1, iSegm2);
        }
        return cross.ok;
    }

    private Point findCrossPoint (Segment line1, Segment line2){
        float denominator = line1.getA() * line2.getB() - line2.getA() * line1.getB();
        float y = (line1.getC() * line2.getA() - line2.getC() * line1.getA()) /
                denominator;
        float x = (line1.getB() * line2.getC() - line2.getB() * line1.getC()) /
                denominator;
        return new Point(x,y);
    }
    private boolean betweenLinePoints (Point center, Segment s) {
        return betweenPoints(center, s.getP0(), s.getP1());
    }
    private boolean betweenPoints (Point iCenter, Point iP0, Point iP1){
        return iCenter.mX >= Math.min(iP0.mX, iP1.mX) &&
                iCenter.mX <= Math.max(iP0.mX, iP1.mX) &&
                iCenter.mY >= Math.min(iP0.mY, iP1.mY) &&
                iCenter.mY <= Math.max(iP0.mY, iP1.mY);
    }
    private Cross caseMultipleCross(Segment iLine1, Segment iLine2) {
        Point p = new Point(0, 0);
        boolean bool = false;
        for (int i = 0; i < 2; i++) {
            if (betweenLinePoints(iLine1.getP(i), iLine2)) {
                p = new Point(iLine1.getP(i));
                bool = true;
                break;
            }
            if (betweenLinePoints(iLine2.getP(i), iLine1)) {
                p = new Point(iLine2.getP(i));
                bool = true;
                break;
            }
        }
        return new Cross(p, bool);
    }
}
