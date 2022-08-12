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

        int numOfSegments = 4;

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

            Segment shortestS = findShortest (fixedP, variableP, iBox);

            if (iBox.ifSegmInside(shortestS)) {
                segments.add(shortestS);
            } else {

                Segment[] split = fitBySplit(shortestS, iBox);
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
            if (s.getP0().x >= iBox.boxWidth()/2) {
                Point variable = new Point(s.getP1().x - iBox.boxWidth(), s.getP1().y);
                float yNewPoints = computeY(s, iBox.getPMax().x);
                sArray[0] = new Segment(s.getP0(), new Point(iBox.getPMax().x, yNewPoints));
                sArray[1] = new Segment(variable, new Point(iBox.getPMin().x, yNewPoints));
            } else {
                Point variable = new Point(s.getP1().x + iBox.boxWidth(), s.getP1().y);
                float yNewPoints = computeY(s, iBox.getPMin().x);
                sArray[0] = new Segment(s.getP0(), new Point(iBox.getPMin().x, yNewPoints));
                sArray[1] = new Segment(variable, new Point(iBox.getPMax().x, yNewPoints));
            }
        } else {
            if (s.getP0().y >= iBox.boxHeight()/2) {
                Point variable = new Point(s.getP1().x,s.getP1().y - iBox.boxHeight());
                float xNewPoints = computeX(s, iBox.getPMax().y);
                sArray[0] = new Segment(s.getP0(), new Point(xNewPoints, iBox.getPMax().y));
                sArray[1] = new Segment(variable, new Point(xNewPoints, iBox.getPMin().y));
            } else {
                Point variable = new Point(s.getP1().x,s.getP1().y + iBox.boxHeight());
                float xNewPoints = computeX(s, iBox.getPMin().y);
                sArray[0] = new Segment(s.getP0(), new Point(xNewPoints, iBox.getPMin().y));
                sArray[1] = new Segment(variable, new Point(xNewPoints, iBox.getPMax().y));
            }
        }
        return sArray;
    }

    private static float computeX (Segment s, float y) {
        return - (s.getC() + s.getB() * y) / s.getA();
    }
    private static float computeY(Segment s, float x) {
        return - (s.getC() + s.getA() * x) / s.getB();
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
