package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 *  An UI component that displays spending in a line graph.
 */
public class GraphChart extends UiPart<Region> {

    private static final String FXML = "GraphChart.fxml";

    @FXML
    private Pane graphPaneView;

    /**
     * Construct graph chart
     *
     * @param data to populate graph
     */
    public GraphChart(LinkedHashMap<String, Double> graphData) {
        super(FXML);
        loadData(graphData);
    }

    /**
     * Loads graph data into a line chart for display.
     * @param graphData
     */
    public void loadData(LinkedHashMap<String, Double> graphData) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount spent ($)");
        LineChart<String, Number> spendingChart = new LineChart<>(xAxis, yAxis);
        spendingChart.setTitle("Graph");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("spending");
        for (Map.Entry<String, Double> i : graphData.entrySet()) {
            XYChart.Data<String, Number> dataToAdd = new XYChart.Data<>(i.getKey().toString(),
                    Math.round(i.getValue()));
            series.getData().add(dataToAdd);
        }
        spendingChart.getData().add(series);
        spendingChart.setMaxWidth(470);
        spendingChart.setMaxHeight(360);

        graphPaneView.getChildren().add(spendingChart);
        graphPaneView.setStyle("-fx-background-color: white");
    }
}
