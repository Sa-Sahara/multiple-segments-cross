import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicFrame extends JFrame {
    JPanel mContentPanel;
    GraphicPanel mGraphicPanel;

    GraphicFrame(ArrayList<Segment> iSegments){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(screenSize));
        this.setTitle("Графическое изображение отрезков");
        mContentPanel = new JPanel(new GridBagLayout());
        mContentPanel.setBackground(Color.black);

        mGraphicPanel = new GraphicPanel(iSegments);
        //mGraphicPanel.paintSegments(Graphics2D g, iSegments);

        mContentPanel.add(mGraphicPanel, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        this.setContentPane(mContentPanel);
        this.pack();
        this.setVisible(true);
    }
}
/*вариант расширения окна:
this.setExtendedState(Frame.MAXIMIZED_BOTH);
 */
