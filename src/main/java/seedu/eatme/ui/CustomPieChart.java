package seedu.eatme.ui;

import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.eatme.model.eatery.Category;

/**
 * Creates a custom pie chart.
 */
public class CustomPieChart extends UiPart<Node> {
    private static final String FXML = "CustomPieChart.fxml";

    private final TreeMap<Category, Integer> chartData;

    @FXML
    private Label title;
    @FXML
    private VBox container;

    public CustomPieChart(String title, TreeMap<Category, Integer> chartData) {
        super(FXML);
        this.chartData = chartData;
        this.title.setText(title);
        this.title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 0 0 15 0;");

        this.container.getChildren().add(createChart());
    }

    /**
     * Creates the pie chart object.
     */
    private PieChart createChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        TreeMap<Category, Integer> duplicateMap = chartData;

        for (Map.Entry<Category, Integer> entry : duplicateMap.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey().getName(), entry.getValue()));
        }

        PieChart pieChart = new PieChart();
        pieChart.setData(pieChartData);

        return pieChart;
    }
}
