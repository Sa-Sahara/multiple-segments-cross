import java.util.ArrayList;
import java.util.Random;

public class Collisions {

    static float mMinX = -500f;
    static float mMinY = -50f;
    static float mMaxX = 300f;
    static float mMaxY = 900f;
    private static Box mInputLimitBox = new Box(mMinX, mMinY, mMaxX, mMaxY);
    private static Random random;

    static int windowWidth = 500;
    static int windowHeight = 500;

    static int numOfSegments = 10;

    public static void main(String[] args) {
        random = new Random();
        long seed = 100;
        ArrayList<Segment> mSegments = segmentsInBox(numOfSegments, mInputLimitBox);

        CrossMode mMode = CrossMode.MULTIPLE;
        CrossSearch alg = new CrossSearch(mMode);
        int numOfCrosses = alg.numOfCrosses(mSegments);
        System.out.println("Total amount of crosses: " + numOfCrosses);

        TransformedSegments algFitting = new TransformedSegments();
        ArrayList<Segment> fitSegments = algFitting.transformForPainting(mSegments);

        new GraphicFrame (fitSegments);
    }

    private static ArrayList<Segment> segmentsInBox(int iNumOfSegments, Box iBox) {
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 0; i < iNumOfSegments; i++) {
            Segment s = new Segment(pointInBox(iBox), pointInBox(iBox));
            System.out.println(""+ s.getP0().x +" "+s.getP0().y +" "+s.getP1().x + " "+ s.getP1().y);
            segments.add(s);
        }
        return segments;
    }

    private static Point pointInBox(Box iBox) {
        float x = random.nextFloat()*
                (iBox.getPMax().x - iBox.getPMin().x) + iBox.getPMin().x;
        float y = new Random().nextFloat()*
                (iBox.getPMax().y - iBox.getPMin().y) + iBox.getPMin().y;
        return new Point(x, y);
    }
}
