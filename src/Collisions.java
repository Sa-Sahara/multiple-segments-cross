import java.util.ArrayList;
import java.util.Random;


public class Collisions {
    public static final float EPSILON = 0.0001f;

    public static void main(String[] args) {
        float minX = -500f;
        float minY = -50f;
        float maxX = 500f;
        float maxY = 50f;
        Box mInputLimitBox = new Box(minX, minY, maxX, maxY);

        int windowWidth = 500;
        int windowHeight = 500;

        int numOfSegments = 4;

        ArrayList<Segment> mSegments = createSegmentsInBox(numOfSegments, mInputLimitBox);

        CrossMode mMode = CrossMode.MULTIPLE;
        CrossSearch alg = new CrossSearch(mMode);
        int numOfCrosses = alg.numOfCrosses(mSegments);
        System.out.println("Total amount of crosses: " + numOfCrosses);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CreateAndShowGUI.createAndShowGUI(mSegments, windowWidth, windowHeight, mInputLimitBox);
            }
        });
    }

    private static ArrayList<Segment> createSegmentsInBox(int iNumOfSegments, Box iBox) {
        Random r = new Random();
        ArrayList<Segment> segments = new ArrayList<>();
        ArrayList<Segment> tmp;

        for (int i = 0; i < iNumOfSegments; i++) {
            Point fixedP = pointInBox(r, iBox);
            Point variableP = pointInBox(r, iBox);
//            Point fixedP = new Point(-460, -40);
//            Point variableP = new Point(480, 39);

            Segment shortestS = findShortest(fixedP, variableP, iBox);

            if (iBox.isSegmentInside(shortestS)) {
                segments.add(shortestS);
            } else if ((Float.compare(shortestS.getP1().x, iBox.minX()) >= 0 &&
                    Float.compare(shortestS.getP1().x, iBox.maxX()) <= 0) ^
                    (Float.compare(shortestS.getP1().y, iBox.minY()) >= 0 &&
                    Float.compare(shortestS.getP1().y, iBox.maxY()) <= 0)) {
                tmp = transformTo2Segments(shortestS, iBox);
                for (Segment s : tmp) {
                    segments.add(new Segment(s));
                }
            } else {
                tmp = transformTo3Segments(shortestS, iBox);
                for (Segment s : tmp) {
                    segments.add(new Segment(s));
                }
            }
        }
        return segments;
    }

    private static ArrayList<Segment> transformTo3Segments(Segment iShortest, Box iBox) {
        ArrayList<Segment> transformedArray = new ArrayList<>();
        ArrayList<Segment> firstSplit = transformTo2Segments(iShortest, iBox);
        ArrayList<Segment> secondSplit = transformTo2Segments(firstSplit.get(1), iBox);
        transformedArray.add(firstSplit.get(0));
        transformedArray.addAll(secondSplit);

        return transformedArray;
    }

    private static ArrayList<Segment> transformTo2Segments(Segment iShortest, Box iBox) {
        ArrayList<Segment> resultArray = new ArrayList<>();
        Point cross = new CrossSearch(CrossMode.SINGLE).crossSearch(iShortest,iBox).p;

        resultArray.add(new Segment(iShortest.getP0(), cross));
        if (Math.abs(cross.x - iBox.minX()) < EPSILON) {
            resultArray.add(new Segment(new Point(cross.x + iBox.boxWidth(), cross.y),
                    new Point(iShortest.getP1().x + iBox.boxWidth(), iShortest.getP1().y)));
            /* swap P1 & P0 because P1 of resulArray[1] became fix-position-point inside box
            * in case of split for 3 segments*/
        } else if (Math.abs(cross.x - iBox.maxX()) < EPSILON) {
            resultArray.add(new Segment(new Point(cross.x - iBox.boxWidth(), cross.y),
                    new Point(iShortest.getP1().x - iBox.boxWidth(), iShortest.getP1().y)));
        } else if (Math.abs(cross.y - iBox.minY()) < EPSILON) {
            resultArray.add(new Segment(new Point(cross.x, cross.y + iBox.boxHeight()),
                    new Point(iShortest.getP1().x, iShortest.getP1().y + iBox.boxHeight())));
        } else if (Math.abs(cross.y - iBox.maxY()) < EPSILON) {
            resultArray.add(new Segment(new Point(cross.x, cross.y - iBox.boxHeight()),
                    new Point(iShortest.getP1().x, iShortest.getP1().y - iBox.boxHeight())));
        }
        return resultArray;
    }

    private static Segment findShortest(Point iFixed, Point iVariable, Box iBox) {
        ArrayList<Segment> projections = new ArrayList<>();
        Segment shortest = new Segment(iFixed, iVariable);
        float width = iBox.boxWidth();
        float height = iBox.boxHeight();

        for (float i = -width; i <= width; i += width) {
            for (float j = -height; j <= height; j += height) {
                projections.add(new Segment(iFixed, new Point(iVariable.x + i, iVariable.y + j)));
            }
        }

        for (Segment p : projections) {
            if (Segment.length(p) < Segment.length(shortest)) {
                shortest = p;
            }
        }
        return shortest;
    }

    private static Point pointInBox(Random r, Box iBox) {
        float x = r.nextFloat() * (iBox.maxX() - iBox.minX()) + iBox.minX();
        float y = r.nextFloat() * (iBox.maxY() - iBox.minY()) + iBox.minY();
        return new Point(x, y);
    }
}
