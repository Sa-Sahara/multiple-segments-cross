import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicPanel extends JPanel {
ArrayList<Segment> mSegments;

    GraphicPanel(ArrayList<Segment> iSegm) {
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(Collisions.windowWidth,Collisions.windowHeight));
        this.setLayout(new BorderLayout());
        int l = iSegm.size();
        mSegments = iSegm;

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.red);
        /*g.drawLine(0, 0, 5000, 5000);
        g.drawLine(50, 0, 0, 5000);*/
        for (Segment s : mSegments) {
            if (s.getP0().mX == s.getP1().mX) {
                g2D.setPaint(Color.red);
            } else if (s.getP0().mY == s.getP1().mY) {
                g2D.setPaint(Color.green);
            } else {
                g2D.setPaint(Color.orange);
            }
            new GUISegment(s).paint(g2D);
        }
    }
}
