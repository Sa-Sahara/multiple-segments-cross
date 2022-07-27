import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicPanel extends JPanel {
    ArrayList<Segment> mSegments = new ArrayList<>();

    GraphicPanel(ArrayList<Segment> iSegments) {
        mSegments.addAll(iSegments); //тут число отрезков верное

        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(Collisions.windowWidth,Collisions.windowHeight));
        this.setLayout(new BorderLayout());
        //int l = iSegm.size(); TODO: segments length diadram

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        for (Segment s : mSegments) {
            if (s.getP0().x == s.getP1().x) {
                g2D.setPaint(Color.red);
            } else if (s.getP0().y == s.getP1().y) {
                g2D.setPaint(Color.green);
            } else {
                g2D.setPaint(Color.orange);
            }

            g2D.drawLine((int) s.getP0().x, (int) s.getP0().y, (int) s.getP1().x, (int) s.getP1().y);
        }
    }
}
