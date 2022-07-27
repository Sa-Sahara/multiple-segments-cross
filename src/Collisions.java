import java.util.ArrayList;
import java.util.Random;

public class Collisions {
    static float mMinX = -50f;
    static float mMinY = -50f;
    static float mMaxX = 900f;
    static float mMaxY = 900f;
    private static Box mInputLimitBox = new Box(mMinX, mMinY, mMaxX, mMaxY);
    private static Random random;

    static int windowWidth = 500;
    static int windowHeight = 500;

    static int numOfSegments = 2;

    public static void main(String[] args) {
        random = new Random();
        long seed = 100; //TODO System.currentTimeMillis();

        random.setSeed(seed);
        ArrayList<Segment> mSegments = segmentsInBox(numOfSegments, mInputLimitBox);
        CrossSearch alg = new CrossSearch();
        int numOfCrosses = alg.numOfCrosses(mSegments);
        System.out.println("Общее количество пересечений: " + numOfCrosses);

        TransformedSegments algFitting = new TransformedSegments();
        ArrayList<Segment> fitSegments = algFitting.transformForPainting(mSegments);

        new GraphicFrame (fitSegments);
    }

    private static ArrayList<Segment> segmentsInBox(int iNumOfSegments, Box iBox) {
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 0; i < iNumOfSegments; i++) {
            Segment s = new Segment(pointInBox(iBox), pointInBox(iBox));
            System.out.println(""+ s.getP0().mX+" "+s.getP0().mY+" "+s.getP1().mX+ " "+ s.getP1().mY);
            segments.add(s);
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
