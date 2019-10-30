package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Tab containing the spending graph.
 */
public class StatsPanel extends UiPart<Region> {
    private static final String FXML = "PlaceholderPanel.fxml";

    @FXML
    private StackPane panePlaceholder;

    public StatsPanel(LinkedHashMap<String, Double> statsData) {
        super(FXML);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> i : statsData.entrySet()) {
            pieChartData.add(new PieChart.Data(i.getKey() + String.format(" ($%.2f)", i.getValue()),
                    Math.round(i.getValue())));
        }
        PieChart pieChart = new PieChart(pieChartData);
        if (pieChartData.size() == 0) {
            pieChart.setTitle("No data to show");
        } else {
            pieChart.setTitle("Statistics");
        }
        pieChart.setLegendVisible(false);

        panePlaceholder.getChildren().add(pieChart);
    }

}
