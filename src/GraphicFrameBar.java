import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicFrameBar extends JFrame {
    JPanel mContentPanel;


    public GraphicFrameBar(ArrayList<Segment> iSegments) {
        //System.out.println(iSegments.size());
        ArrayList<Segment> mSegments = new ArrayList<>();
        mSegments.addAll(iSegments);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(screenSize));
        this.setTitle("Segments");

        mContentPanel = new JPanel();
        BarChart barChart = new BarChart(mSegments);
        mContentPanel.add(barChart);

        this.setContentPane(mContentPanel);
        this.pack();
        this.setVisible(true);
    }
}
