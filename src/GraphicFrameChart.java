import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GraphicFrameChart extends JFrame {
    JPanel mContentPanel;
    GraphicPanel mGraphicPanel;
    ArrayList<GUISegment> mGUISegments;

    GraphicFrameChart(ArrayList<GUISegment> iSegments, int windowWidth, int windowHeight){
        mGUISegments = iSegments;
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

        /*mContentPanel.addComponentListener(new ComponentAdapter() { //try to make mGraphicPanel scalable when GFrame is less than windowWidth windowHeight
            @Override
            public void componentResized(ComponentEvent e) {
                JPanel panel = (JPanel) e.getComponent();
                Dimension size = panel.getSize();
                if (size.width < windowWidth || size.height < windowHeight) {
                    mContentPanel.setLayout(new BorderLayout());
                    //mGraphicPanel.setPreferredSize(size);
                    mContentPanel.add(new GraphicPanel(iSegments, size.width, size.height));
                    //mContentPanel.add(mGraphicPanel);
                }
            }

        });*/
        this.setMinimumSize(new Dimension(mGraphicPanel.getPreferredSize().width+50,
                                        mGraphicPanel.getPreferredSize().height+50));

        this.setContentPane(mContentPanel);
        this.pack();
        this.setVisible(true);
    }
}

