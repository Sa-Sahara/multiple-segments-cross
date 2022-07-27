import java.util.ArrayList;

public class TransformedSegments {

    public ArrayList<Segment> transformForPainting (ArrayList<Segment> iSegments) {

        ArrayList<Segment> transSegm = new ArrayList<>();
        Box factSegmentsBorders = calculateBoarders(iSegments);


        for (Segment s : iSegments) {
            Segment trS = fitSegment(s, factSegmentsBorders);
            transSegm.add(trS);
        }
        return transSegm;
    }

    private Box calculateBoarders (ArrayList<Segment> iSegments) { //корректно
        float minX = iSegments.get(0).getP0().x;
        float maxX = iSegments.get(0).getP0().x;
        float minY = iSegments.get(0).getP0().y;
        float maxY = iSegments.get(0).getP0().y;

        for (Segment s : iSegments) {
            for(int i = 0; i < 2; i++) {
                if (s.getP(i).x < minX) {minX = s.getP(i).x;}
                if (s.getP(i).x > maxX) {maxX = s.getP(i).x;}
                if (s.getP(i).y < minY) {minY = s.getP(i).y;}
                if (s.getP(i).y > maxY) {maxY = s.getP(i).y;}
            }
        }
        return new Box(minX, minY, maxX, maxY);
    }

    private Segment fitSegment(Segment iSegm, Box iBox) {
        //float scaleWidth = findRateWidth(iBox);
        //float scaleHeight = rateHeight(iBox);
        float shiftWidth = shiftWidth(iBox);
        float shiftHeight = shiftHeight(iBox);

        Segment s = new Segment(
                new Point(iSegm.getP0().x + shiftWidth,
                        iSegm.getP0().y + shiftHeight),
                new Point(iSegm.getP1().x + shiftWidth,
                        iSegm.getP1().y + shiftHeight));

        return s;
    }

    private float shiftWidth (Box iBox) {
        return 0f - iBox.getPMin().x;
    }

    private float shiftHeight (Box iBox) {
        //System.out.println("shiftHeight" + (0f - iBox.getPMin().mY)); //тут ок
        return 0f - iBox.getPMin().y;
    }

    /*
    private float findRateWidth (Box iBox) { //
        float rateWidth = Collisions.windowWidth / Math.abs(iBox.getPMax().mX - iBox.getPMin().mX);
        System.out.println("rateWidth " +rateWidth);
        return rateWidth;
    }

    private float rateHeight (Box iBox) {
        float rateHeight = Collisions.windowHeight / Math.abs(iBox.getPMax().mY - iBox.getPMin().mY);
        System.out.println("rateHeight "+rateHeight);
        return rateHeight;
    }*/
}
