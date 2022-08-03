import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BarChart extends JFXPanel {
    ArrayList <Float> mLength = new ArrayList<>();
    ArrayList <String> mNames = new ArrayList<>();

    public BarChart(ArrayList<Segment> iSegments) {
        int counter = 1;
        for (Segment s : iSegments) {
            mLength.add(Segment.length(s));
            mNames.add("Segment" + counter);
            counter++;
        }

        int[] frequency = countFrequency();

        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("length, pc");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("quantity");

        //Creating the Bar chart
        javafx.scene.chart.BarChart<String, Number> barChart = new javafx.scene.chart.BarChart<>(xAxis, yAxis);
        barChart.setTitle("Segments length");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Segments");

        int step = countStep();
        int maxBorder = step;
        int minBorder = 0;
        for (int i : frequency) {
            series1.getData().add(new XYChart.Data<>("" + minBorder + " - " + maxBorder, i)); //series1.getData().add(new XYChart.Data<>("Speed", 1.0));
            minBorder += step;
            maxBorder += step;
        }

        //Setting the data to bar chart
        barChart.getData().addAll(series1);

        //Creating a Group object
        Group root = new Group(barChart);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        //Adding scene to the stage
        this.setScene(scene);



    }

    private int[] countFrequency () {
        int numOfBars = (int) Math.floor(Math.log(mLength.size()) / Math.log(2));
        int[] frequency = new int[numOfBars];
        int step = countStep();
        int maxBorder = step;
        int minBorder = 0;
        for (int i = 0; i < numOfBars; i++) {
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
    private int countStep() {
        return (int) Math.ceil(getLast(mLength));
    }
    private float getLast (ArrayList <Float> list) {
        return list.get(list.size() - 1);
    }
}