import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicPanel extends JPanel {
    ArrayList<Segment> mSegments;

    GraphicPanel(ArrayList<Segment> iSegments) {
        mSegments = iSegments;
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(Collisions.windowWidth,Collisions.windowHeight));
        this.setLayout(new BorderLayout());
        //int l = iSegm.size(); TODO: графики длины отрезков

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        for (Segment s : mSegments) {
            System.out.println("paintComponent " + s.getP0().mX + " " + s.getP0().mY + " " + s.getP1().mX + " " + s.getP1().mY);
        }

        /*for (Segment s : mSegments) {
            if (s.getP0().mX == s.getP1().mX) {
                g2D.setPaint(Color.red);
            } else if (s.getP0().mY == s.getP1().mY) {
                g2D.setPaint(Color.green);
            } else {
                g2D.setPaint(Color.orange);
            }
            System.out.println("paintComponent " + s.getP0().mX + " " + s.getP0().mY + " " + s.getP1().mX + " " + s.getP1().mY);
            g2D.drawLine((int) s.getP0().mX, (int) s.getP0().mY, (int) s.getP1().mX, (int) s.getP1().mY);
            //new GUISegment(s).paint(g2D);
        }*/
    }
}
