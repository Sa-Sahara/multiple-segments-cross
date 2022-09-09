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

            if (iBox.ifSegmentInside(shortestS)) {
                segments.add(shortestS);
            } else if ((shortestS.getP1().x >= iBox.getPMin().x && shortestS.getP1().x <= iBox.getPMax().x) ^
                    (shortestS.getP1().y >= iBox.getPMin().y && shortestS.getP1().y <= iBox.getPMax().y)) {
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
        Point cross = crossFor2SegmentsSlit(iShortest, iBox);

        if (Math.abs(cross.x - iBox.getPMin().x) < EPSILON) {
            resultArray.add(new Segment(iShortest.getP0(), cross));
            resultArray.add(new Segment(new Point(cross.x + iBox.boxWidth(), cross.y),
                    new Point(iShortest.getP1().x + iBox.boxWidth(), iShortest.getP1().y)));
            /* swap P1 & P0 because P1 of resulArray[1] became fix-position-point inside box
            * in case of split for 3 segments*/

        } else if (Math.abs(cross.x - iBox.getPMax().x) < EPSILON) {
            resultArray.add(new Segment(iShortest.getP0(), cross));
            resultArray.add(new Segment(new Point(cross.x - iBox.boxWidth(), cross.y),
                    new Point(iShortest.getP1().x - iBox.boxWidth(), iShortest.getP1().y)));
        } else if (Math.abs(cross.y - iBox.getPMin().y) < EPSILON) {
            resultArray.add(new Segment(iShortest.getP0(), cross));
            resultArray.add(new Segment(new Point(cross.x, cross.y + iBox.boxHeight()),
                    new Point(iShortest.getP1().x, iShortest.getP1().y + iBox.boxHeight())));
        } else if (Math.abs(cross.y - iBox.getPMax().y) < EPSILON) {
            resultArray.add(new Segment(iShortest.getP0(), cross));
            resultArray.add(new Segment(new Point(cross.x, cross.y - iBox.boxHeight()),
                    new Point(iShortest.getP1().x, iShortest.getP1().y - iBox.boxHeight())));
        }
        return resultArray;
    }

    private static Point crossFor2SegmentsSlit(Segment iShortest, Box iBox) {
        Point crossPoint = new Point(0, 0);
        CrossMode mMode = CrossMode.SINGLE;
        CrossSearch search = new CrossSearch(mMode);

        Segment minX = new Segment(
                new Point(iBox.getPMin().x, iBox.getPMax().y),
                new Point(iBox.getPMin().x, iBox.getPMin().y));
        Segment maxX = new Segment(
                new Point(iBox.getPMax().x, iBox.getPMax().y),
                new Point(iBox.getPMax().x, iBox.getPMin().y));
        Segment maxY = new Segment(
                new Point(iBox.getPMin().x, iBox.getPMax().y),
                new Point(iBox.getPMax().x, iBox.getPMax().y));
        Segment minY = new Segment(
                new Point(iBox.getPMin().x, iBox.getPMin().y),
                new Point(iBox.getPMax().x, iBox.getPMin().y));
        Cross crossMaxX = search.crossSearch(iShortest, maxX);
        Cross crossMinX = search.crossSearch(iShortest, minX);
        Cross crossMaxY = search.crossSearch(iShortest, maxY);
        Cross crossMinY = search.crossSearch(iShortest, minY);

        if (crossMaxX.ok) {
            crossPoint = crossMaxX.p;
        } else if (crossMinX.ok) {
            crossPoint = crossMinX.p;
        } else if (crossMaxY.ok) {
            crossPoint = crossMaxY.p;
        } else if (crossMinY.ok) {
            crossPoint = crossMinY.p;
        }
        return crossPoint;
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
        float x = r.nextFloat() * (iBox.getPMax().x - iBox.getPMin().x) + iBox.getPMin().x;
        float y = r.nextFloat() * (iBox.getPMax().y - iBox.getPMin().y) + iBox.getPMin().y;
        return new Point(x, y);
    }
}
