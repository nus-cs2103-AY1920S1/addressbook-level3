package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Tab containing the spending graph.
 */
public class StatsPanel extends UiPart<Region> {
    private static final String FXML = "PlaceholderPanel.fxml";

    @FXML
    private StackPane panePlaceholder;

    public StatsPanel(LinkedHashMap<String, Double> statsData, String commandResult) {
        super(FXML);
        loadData(statsData, commandResult);
    }

    private void loadData(LinkedHashMap<String, Double> statsData, String commandResult) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> i : statsData.entrySet()) {
            pieChartData.add(new PieChart.Data(i.getKey() + String.format(" ($%.2f)", i.getValue()),
                    Math.round(i.getValue())));
        }
        PieChart pieChart = new PieChart(pieChartData);
        Text text = new Text();
        if (pieChartData.size() == 0) {
            text.setText("No data to show");
        }
        pieChart.setTitle(commandResult);
        pieChart.setLegendVisible(false);
        panePlaceholder.getChildren().addAll(pieChart, text);
    }

}
