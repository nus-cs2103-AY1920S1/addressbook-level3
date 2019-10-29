package seedu.address.ui;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import seedu.address.model.statistics.Statistics;

/**
 * Creates a custom, layered graph to display the total and average costs graphs.
 */
public class CustomGraph extends UiPart<Node> {

    private static final String FXML = "CustomGraph.fxml";
    private final int prefTickSpace = 20;

    private final TreeMap<? extends Object, Double> totalData;
    private final TreeMap<? extends Object, Double> avgData;

    @FXML
    private HBox container;
    @FXML
    private StackPane layeredGraph;
    @FXML
    private Label title;

    private BarChart<String, Number> totalChart;
    private LineChart<String, Number> avgChart;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    public CustomGraph(String title, TreeMap<? extends Object, Double> totalData, TreeMap<? extends Object,
            Double> avgData) {
        super(FXML);
        this.totalData = totalData;
        this.avgData = avgData;
        this.title.setText(title);

        initAxis();
        createTotalChart();
        createAvgChart();

        layeredGraph.getChildren().addAll(totalChart, avgChart);
    }

    /**
     * Initialises the x-axis and y-axis for the custom graph.
     */
    private void initAxis() {
        this.xAxis = new CategoryAxis(FXCollections.observableList(totalData.keySet()
                .stream()
                .map(c -> ((Object) c).toString())
                .collect(Collectors.toList())));

        Map<String, Double> pair = Statistics.getMaxMinValue(totalData);
        int lowerBound = (int) Math.ceil(pair.get(Statistics.MIN_VARIABLE));
        int upperBound = (int) Math.ceil(pair.get(Statistics.MAX_VARIABLE));

        this.yAxis = new NumberAxis(lowerBound, upperBound, Math.ceil((upperBound - lowerBound) / prefTickSpace));
    }

    /**
     * Creates a bar chart that displays the total costs.
     */
    private void createTotalChart() {
        totalChart = new BarChart<String, Number>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<? extends Object, Double> entry : totalData.entrySet()) {
            String category = entry.getKey().toString();
            Double cost = entry.getValue();

            XYChart.Data<String, Number> d = new XYChart.Data<>(category, cost);
            series.getData().add(d);
        }
    }

    /**
     * Creates a line chart that displays the average cost.
     */
    private void createAvgChart() {
        avgChart = new LineChart<String, Number>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<? extends Object, Double> entry : avgData.entrySet()) {
            String category = entry.getKey().toString();
            Double cost = entry.getValue();

            XYChart.Data<String, Number> d = new XYChart.Data<>(category, cost);
            series.getData().add(d);
        }
    }
}
