import java.util.ArrayList;
import java.util.Random;

public class Collisions {

    public static void main(String[] args) {

        float minX = -500f;
        float minY = -50f;
        float maxX = 300f;
        float maxY = 900f;
        Box mInputLimitBox = new Box(minX, minY, maxX, maxY);

        int windowWidth = 500;
        int windowHeight = 500;

        int numOfSegments = 1;

        ArrayList<Segment> mSegments = segmentsInBox(numOfSegments, mInputLimitBox); //random! verb

        CrossMode mMode = CrossMode.MULTIPLE;
        CrossSearch alg = new CrossSearch(mMode);
        int numOfCrosses = alg.numOfCrosses(mSegments);
        System.out.println("Total amount of crosses: " + numOfCrosses);

        coordsTransform xFitting = new coordsTransform(new float[]{minX, maxX}, new float[]{0, windowWidth});
        coordsTransform yFitting = new coordsTransform(new float[]{minY, maxY}, new float[]{0, windowHeight});
        ArrayList<GUISegment> GUISegments = fitSegments(mSegments, xFitting, yFitting);

        new GraphicFrameChart(GUISegments, windowWidth, windowHeight);
        //new GraphicFrameBar(mSegments);
    }

    private static Point fitPoint (Point p, coordsTransform transformX, coordsTransform transformY) {
        return new Point(transformX.fit(p.x), transformY.fit(p.y));
    }

    private static GUISegment fitSegment (Segment iSegment, coordsTransform transX, coordsTransform transY) {
        return new GUISegment(fitPoint(iSegment.getP0(), transX, transY),
                            fitPoint(iSegment.getP1(), transX, transY));
    }

    private static ArrayList<GUISegment> fitSegments(ArrayList<Segment> iSegments,
                                                  coordsTransform transformX,
                                                  coordsTransform transformY) {
        ArrayList<GUISegment> fitted = new ArrayList<>();
        for(Segment s : iSegments){
            fitted.add(fitSegment(s, transformX, transformY));
        }
        return fitted;
    }

    private static ArrayList<Segment> segmentsInBox(int iNumOfSegments, Box iBox) {
        Random r = new Random();
        ArrayList<Segment> segments = new ArrayList<>();

        for (int i = 0; i < iNumOfSegments; i++) {
            Point fixedP = pointInBox(r, iBox);
            Point variableP = pointInBox(r, iBox);

            Segment shortest = findShortest (fixedP, variableP, iBox);

            if (iBox.ifSegmInside(shortest)) {
                segments.add(shortest);
            } else {
                Segment[] split = fitBySplit(shortest, iBox);
                for (Segment s : split) {
                    segments.add(s);
                }
            }
        }
        return segments;
    }

    private static Segment[] fitBySplit (Segment s, Box iBox) {
        Segment[] sArray = new Segment[2]; // magic number?

        if (s.getP1().x > iBox.getPMax().x || s.getP1().x < iBox.getPMin().x) {

            Point pMax = findMaxPoint(s, "x"); // magic string?
            Point pMin = (s.getP0() == pMax ? s.getP1() : s.getP0());

            sArray[0] = new Segment(pMax, new Point(computeX(s, iBox.getPMax().y), iBox.getPMax().y));
            sArray[1] = new Segment(pMin, new Point(computeX(s, iBox.getPMin().y), iBox.getPMin().y));
        } else {
            float yMid = (s.getP0().y + s.getP1().y)/2;
            Point pMax = findMaxPoint(s, "y"); // magic string?
            Point pMin = (s.getP0() == pMax ? s.getP1() : s.getP0());

            sArray[0] = new Segment(pMax, new Point(iBox.getPMax().x, computeY(s, iBox.getPMax().x)));
            sArray[1] = new Segment(pMin, new Point(iBox.getPMin().x, computeY(s, iBox.getPMin().x)));
        }
        return sArray;
    }

    private static float computeX (Segment s, float y) {
        return - (s.getC() + s.getB() * y) / s.getA();
    }
    private static float computeY(Segment s, float x) {
        return - (s.getC() + s.getA() * x) / s.getB();
    }

    private static Point findMaxPoint (Segment s, String str) { //stupid decision "str"
        Point pMax = s.getP1();
        if (str.equals("x")) {
            if (s.getP0().x >= s.getP1().x) {pMax = s.getP0();}
        }
        if (str.equals("y")) {
            if (s.getP0().y >= s.getP1().y) {pMax = s.getP0();}
        }
        return pMax;
    }

    private static Segment findShortest (Point iFixed, Point iVariable, Box iBox) {
        ArrayList<Segment> projections = new ArrayList<>();
        Segment shortest = new Segment(iFixed, iVariable);
        float width = iBox.getPMax().x - iBox.getPMin().x;
        float height = iBox.getPMax().y - iBox.getPMin().y;

        projections.add(shortest);
        projections.add(new Segment(iFixed, new Point(iVariable.x - width, iVariable.y)));
        projections.add(new Segment(iFixed, new Point(iVariable.x + width, iVariable.y)));
        projections.add(new Segment(iFixed, new Point(iVariable.x, iVariable.y - height)));
        projections.add(new Segment(iFixed, new Point(iVariable.x, iVariable.y + height)));

        for (Segment p : projections) {
            if (Segment.length(p) < Segment.length(shortest)) {
                shortest = p;
            }
        }
        return shortest;
    }

    private static Point pointInBox(Random r, Box iBox) {
        float x = r.nextFloat()*
                (iBox.getPMax().x - iBox.getPMin().x) + iBox.getPMin().x;
        float y = r.nextFloat()*
                (iBox.getPMax().y - iBox.getPMin().y) + iBox.getPMin().y;
        return new Point(x, y);
    }
}
