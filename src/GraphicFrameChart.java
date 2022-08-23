import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GraphicFrameChart extends JFrame {
    JPanel mContentPanel;
    GraphicPanel mGraphicPanel;
    ArrayList<GUISegment> mGUISegments;

    GraphicFrameChart(ArrayList<Segment> iSegments, int windowWidth, int windowHeight, Box iBox){

        coordsTransform xFitting = new coordsTransform(
                new float[]{iBox.getPMin().x, iBox.getPMax().x}, new float[]{0, windowWidth});
        coordsTransform yFitting = new coordsTransform(
                new float[]{iBox.getPMin().y, iBox.getPMax().y}, new float[]{0, windowHeight});
        mGUISegments = fitSegments(iSegments, xFitting, yFitting);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(screenSize));
        this.setTitle("Segments");

        mContentPanel = new JPanel(new GridBagLayout());
        mContentPanel.setBackground(Color.black);


        mGraphicPanel = new GraphicPanel(mGUISegments, windowWidth, windowHeight);
        mContentPanel.add(mGraphicPanel, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        this.setMinimumSize(new Dimension(mGraphicPanel.getPreferredSize().width+50,
                                        mGraphicPanel.getPreferredSize().height+50));

        this.setContentPane(mContentPanel);
        this.pack();
        //this.setVisible(true);


    }
    private static ArrayList<GUISegment> fitSegments(ArrayList<Segment> iSegments,
                                                     coordsTransform transformX,
                                                     coordsTransform transformY) {
        ArrayList<GUISegment> fitted = new ArrayList<>();
        for (Segment s : iSegments) {
            fitted.add(fitSegment(s, transformX, transformY));
        }
        return fitted;
    }

    private static GUISegment fitSegment(Segment iSegment, coordsTransform transX, coordsTransform transY) {
        return new GUISegment(fitPoint(iSegment.getP0(), transX, transY),
                fitPoint(iSegment.getP1(), transX, transY));
    }

    private static Point fitPoint(Point p, coordsTransform transformX, coordsTransform transformY) {
        return new Point(transformX.fit(p.x), transformY.fit(p.y));
    }
}

