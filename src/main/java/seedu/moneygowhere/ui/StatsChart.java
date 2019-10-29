package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.moneygowhere.commons.core.LogsCenter;

/**
 *  An UI component that displays statistics in a pie chart.
 */
public class StatsChart extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(StatsChart.class);
    private static final String FXML = "StatsChart.fxml";

    @FXML
    private Pane statsPaneView;

    /**
     * Construct stats chart
     *
     * @param data to populate stats
     */
    public StatsChart(LinkedHashMap<String, Double> statsData) {
        super(FXML);
        loadData(statsData);
    }

    /**
     * Loads stats data into a pie chart for display.
     * @param statsData
     */
    public void loadData(LinkedHashMap<String, Double> statsData) {
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

        pieChart.setMaxWidth(470);
        pieChart.setMaxHeight(360);

        statsPaneView.getChildren().add(pieChart);
        statsPaneView.setStyle("-fx-background-color: white");
    }
}
