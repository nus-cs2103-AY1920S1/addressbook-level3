package seedu.eatme.ui;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.eatme.model.statistics.Statistics;


/**
 * Creates a custom, layered graph to display the total and average costs graphs.
 */
public class CustomLayeredGraph extends UiPart<Node> {

    private static final String FXML = "CustomLayeredGraph.fxml";
    private final int prefTickSpace = 20;

    private final TreeMap<? extends Object, Double> totalData;
    private final TreeMap<? extends Object, Double> avgData;

    @FXML
    private HBox customLegend;
    @FXML
    private StackPane layeredGraph;
    @FXML
    private Label title;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    public CustomLayeredGraph(String title, TreeMap<? extends Object, Double> totalData, TreeMap<? extends Object,
            Double> avgData) {
        super(FXML);
        this.totalData = totalData;
        this.avgData = avgData;
        this.title.setText(title);
        this.title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 10 0 15 0;");

        initAxis();
        layeredGraph.getChildren().addAll(createTotalChart(), createAvgChart());
        initCustomLegend();
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

        this.yAxis = new NumberAxis(lowerBound, upperBound + 1, 1);

        this.xAxis.setLabel("Category");
        this.yAxis.setLabel("Cost");
    }

    /**
     * Creates a bar chart that displays the total costs.
     */
    private BarChart<String, Number> createTotalChart() {
        BarChart<String, Number> totalChart = new BarChart<String, Number>(xAxis, yAxis);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();

        for (Map.Entry<? extends Object, Double> entry : totalData.entrySet()) {
            String category = entry.getKey().toString();
            Number cost = entry.getValue();

            series1.getData().add(new XYChart.Data<String, Number>(category, cost));
        }

        totalChart.getData().addAll(series1);
        totalChart.setLegendVisible(false);
        return totalChart;
    }

    /**
     * Creates a line chart that displays the average cost.
     */
    private LineChart<String, Number> createAvgChart() {
        LineChart<String, Number> avgChart = new LineChart<String, Number>(xAxis, yAxis);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();

        for (Map.Entry<? extends Object, Double> entry : avgData.entrySet()) {
            String category = entry.getKey().toString();
            Number cost = entry.getValue();

            series2.getData().add(new XYChart.Data<String, Number>(category, cost));
        }

        avgChart.setAnimated(false);
        avgChart.setCreateSymbols(true);
        avgChart.setAlternativeRowFillVisible(false);
        avgChart.setAlternativeColumnFillVisible(false);
        avgChart.setHorizontalGridLinesVisible(false);
        avgChart.setVerticalGridLinesVisible(false);
        avgChart.getXAxis().setVisible(false);
        avgChart.getYAxis().setVisible(false);
        avgChart.getData().addAll(series2);
        avgChart.getStylesheets().add("view/Chart.css");
        avgChart.setLegendVisible(false);

        return avgChart;
    }

    /**
     * Creates the custom legend for the layered graph.
     */
    private void initCustomLegend() {
        Circle circle1 = new Circle(15.0f, 15.0f, 8.f);
        Label label1 = new Label();
        label1.setText("Total cost");
        circle1.setFill(Color.ORANGERED);
        label1.setStyle("-fx-padding: 0 15 0 2;");

        Circle circle2 = new Circle(15.0f, 15.0f, 8.f);
        Label label2 = new Label();
        label2.setText("Average cost");
        circle2.setFill(Color.BLACK);
        label2.setStyle("-fx-padding: 0 0 0 2;");

        this.customLegend.getChildren().addAll(circle1, label1, circle2, label2);
        customLegend.setStyle("-fx-padding: 0 0 10 0");
    }
}
