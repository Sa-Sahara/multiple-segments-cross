import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class BarChart extends JFXPanel {
    ArrayList <Float> mLength = new ArrayList<>();
    ArrayList <String> mNames = new ArrayList<>();

    public BarChart(ArrayList<Segment> iSegments) {
        int counter = 1;
        for (Segment s : iSegments) {
            mLength.add(s.length());
            mNames.add("Segment" + counter);
            counter++;
        }

        //Defining the axes
        CategoryAxis xAxisInvisible = new CategoryAxis();
        xAxisInvisible.setLabel("length, pc");
        xAxisInvisible.setTickLabelRotation(90);
        xAxisInvisible.setVisible(true);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("quantity");
        yAxis.setMinorTickLength(0);

        //Creating the Bar chart
        javafx.scene.chart.BarChart<String, Number> barChart = new javafx.scene.chart.BarChart<>(xAxisInvisible, yAxis);
        barChart.setTitle("Segments length");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Segments");

        int numOfBars = 20;
        int[] frequency = countFrequency(numOfBars);
        int step = (int) Math.ceil(getLast(mLength) / numOfBars);
        int maxBorder = step;
        int minBorder = 0;
        for (int i : frequency) {
            if (i % 5 == 0) {
                series1.getData().add(new XYChart.Data<>("" + (minBorder + maxBorder)/2, i)); //series1.getData().add(new XYChart.Data<>("Speed", 1.0));
                minBorder += step;
                maxBorder += step;
            } else {
                series1.getData().add(new XYChart.Data<>("" + (minBorder + maxBorder)/2, i)); //series1.getData().add(new XYChart.Data<>("Speed", 1.0));
                minBorder += step;
                maxBorder += step;
            }

        }

        barChart.setLegendVisible(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        //Setting the data to bar chart
        barChart.getData().addAll(series1);

        //Creating a Group object
        Group root = new Group(barChart);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        //Adding scene to the stage
        this.setScene(scene);
    }

    private int[] countFrequency (int iNumOfBars) {
        int[] frequency = new int[iNumOfBars];
        int step = (int) Math.ceil(getLast(mLength) / iNumOfBars);
        int maxBorder = step;
        int minBorder = 0;
        for (int i = 0; i < iNumOfBars; i++) {
            for (float f : mLength) {
                if (i == 0 && f >= minBorder && f <= maxBorder) {
                    frequency[i]++;
                } else if (i != 0 && f > minBorder && f <= maxBorder) {
                    frequency[i]++;
                }
            }
            minBorder += step;
            maxBorder += step;
        }
        return frequency;
    }

    private float getLast (ArrayList <Float> list) {
        return list.get(list.size() - 1);
    }
}