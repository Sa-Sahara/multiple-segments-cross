import java.util.ArrayList;

public class TransformedSegments {
    //ArrayList<Segment> mSegments = new ArrayList<>();

    public ArrayList<Segment> transformForPainting (ArrayList<Segment> iSegments) {

        //mSegments.addAll(iSegments);

        ArrayList<Segment> transSegm = new ArrayList<>();
        Box factSegmentsBorders = calculateBoarders(iSegments);


        for (Segment s : iSegments) {
            Segment trS = fitSegment(s, factSegmentsBorders);
            //System.out.println("fitted "+ trS.getP0().mX+" "+trS.getP0().mY+" "+trS.getP1().mX+ " "+ trS.getP1().mY); //тут корректно
            transSegm.add(trS);
        }
        return transSegm;
    }

    private Box calculateBoarders (ArrayList<Segment> iSegments) { //корректно
        float minX = iSegments.get(0).getP0().mX;
        float maxX = iSegments.get(0).getP0().mX;
        float minY = iSegments.get(0).getP0().mY;
        float maxY = iSegments.get(0).getP0().mY;

        for (Segment s : iSegments) {
            for(int i = 0; i < 2; i++) {
                if (s.getP(i).mX < minX) {minX = s.getP(i).mX;}
                if (s.getP(i).mX > maxX) {maxX = s.getP(i).mX;}
                if (s.getP(i).mY < minY) {minY = s.getP(i).mY;}
                if (s.getP(i).mY > maxY) {maxY = s.getP(i).mY;}
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
                new Point(iSegm.getP0().mX  + shiftWidth,//ok
                        iSegm.getP0().mY  + shiftHeight), //ok
                new Point(iSegm.getP1().mX  + shiftWidth, //ok
                        iSegm.getP1().mY  + shiftHeight)); //ok

        //System.out.println("fitSegment " + s.getP1().mX + " " + s.getP1().mY);
        return s;
    }

    private float shiftWidth (Box iBox) {
        //System.out.println("shiftWidth "+ (0f - iBox.getPMin().mX)); // тут ок
        return 0f - iBox.getPMin().mX;
    }

    private float shiftHeight (Box iBox) {
        //System.out.println("shiftHeight" + (0f - iBox.getPMin().mY)); //тут ок
        return 0f - iBox.getPMin().mY;
    }

    /*
    private float findRateWidth (Box iBox) { // todo раст€гивание-сжатие
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
