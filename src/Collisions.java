import java.util.ArrayList;
import java.util.Random;

public class Collisions {
    static float minX = 50f;
    static float maxX = 400f;
    static float minY = 50f;
    static float maxY = 400f;
    static Box mBox = new Box(minX, maxX, minY, maxY);
    static Random random = new Random();

    
    static int windowWidth = 500;
    static int windowHeight = 500;

    static int numOfSegments = 3;

    public static void main(String[] args) {
        long seed = 100; //System.currentTimeMillis();
        random.setSeed(seed);
        ArrayList<Segment> mSegments = segmentsInBox(numOfSegments, mBox);
        CrossSearch alg = new CrossSearch();
        int numOfCrosses = alg.numOfCrosses(mSegments);
        System.out.println("Общее количество пересечений: " + numOfCrosses);
        new DrawSegments(mSegments);
    }

    private static ArrayList<Segment> segmentsInBox(int iNumOfSegments, Box iBox) {
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 0; i < iNumOfSegments; i++) {
            Segment s = new Segment(pointInBox(iBox), pointInBox(iBox));
            segments.add(s);
            //System.out.println("" + s.getP0().mX + s.getP0().mY + s.getP1().mX + s.getP1().mY);
        }
        return segments;

    }
    private static Point pointInBox(Box iBox) {
        float x = random.nextFloat()*
                (iBox.getPMax().mX - iBox.getPMin().mX) + iBox.getPMin().mX;
        float y = new Random().nextFloat()*
                (iBox.getPMax().mY - iBox.getPMin().mY) + iBox.getPMin().mY;
        return new Point(x, y);
    }
}
