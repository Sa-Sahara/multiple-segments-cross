import java.util.ArrayList;

public class CreateAndShowGUI {


    public static void createAndShowGUI(ArrayList<Segment> iSegments, int windowWidth, int windowHeight, Box iBox){
        new GraphicFrameChart(iSegments, windowWidth, windowHeight, iBox).setVisible(true);

        //new GraphicFrameBar(iSegments);
    }

}
