import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Collisions {

    public static void main(String[] args) {

        float minX = -500f;
        float minY = -50f;
        float maxX = 300f;
        float maxY = 900f;
        Box mInputLimitBox = new Box(minX, minY, maxX, maxY);

        Random random = new Random();
        /*long seed = 100;
        random.setSeed(seed);*/

        int windowWidth = 500;
        int windowHeight = 500;

        int numOfSegments = 100;

        ArrayList<Segment> mSegments = segmentsInBox(numOfSegments, mInputLimitBox, random);

        CrossMode mMode = CrossMode.MULTIPLE;
        CrossSearch alg = new CrossSearch(mMode);
        int numOfCrosses = alg.numOfCrosses(mSegments);
        System.out.println("Total amount of crosses: " + numOfCrosses);

        coordsTransform xFitting = new coordsTransform(new float[]{minX, maxX}, new float[]{0, windowWidth});
        coordsTransform yFitting = new coordsTransform(new float[]{minY, maxY}, new float[]{0, windowHeight});
        ArrayList<GUISegment> GUISegments = fitSegments(mSegments, xFitting, yFitting);

        //new GraphicFrameChart(GUISegments, windowWidth, windowHeight);
        new GraphicFrameBar(mSegments);
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

    private static ArrayList<Segment> segmentsInBox(int iNumOfSegments, Box iBox, Random r) {
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 0; i < iNumOfSegments; i++) {
            Segment s = new Segment(pointInBox(r, iBox), pointInBox(r, iBox));
            segments.add(s);
        }
        return segments;
    }

    private static Point pointInBox(Random r, Box iBox) {
        float x = r.nextFloat()*
                (iBox.getPMax().x - iBox.getPMin().x) + iBox.getPMin().x;
        float y = r.nextFloat()*
                (iBox.getPMax().y - iBox.getPMin().y) + iBox.getPMin().y;
        return new Point(x, y);
    }
}
