import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicFrame extends JFrame {
    JPanel mContentPanel;
    GraphicPanel mGraphicPanel;
    ArrayList<Segment> mSegments;

    GraphicFrame(ArrayList<Segment> iSegments, int windowWidth, int windowHeight){
        mSegments = iSegments;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(screenSize));
        this.setTitle("Segments");
        mContentPanel = new JPanel(new GridBagLayout());
        mContentPanel.setBackground(Color.black);

        mGraphicPanel = new GraphicPanel(iSegments, windowWidth, windowHeight);
        mContentPanel.add(mGraphicPanel, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        this.setContentPane(mContentPanel);
        this.pack();
        this.setVisible(true);
    }
}

