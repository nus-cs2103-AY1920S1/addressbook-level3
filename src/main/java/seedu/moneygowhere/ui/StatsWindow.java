package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.moneygowhere.commons.core.LogsCenter;

/**
 * Controller for a stats page
 */
public class StatsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);
    private static final String FXML = "StatsWindow.fxml";

    @FXML
    private Pane paneView;

    /**
     * Creates a new StatsWindow.
     *
     * @param root Stage to use as the root of the StatsWindow.
     */
    public StatsWindow(Stage root) {
        super(FXML, root);
        root.initStyle(StageStyle.UTILITY);
    }

    /**
     * Creates a new StatsWindow.
     */
    public StatsWindow() {
        this(new Stage());
    }

    /**
     * Loads stats data into a pie chart for display.
     * @param statsData
     */
    public void loadData(LinkedHashMap<String, Double> statsData) {
        paneView.getChildren().clear();

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

        paneView.getChildren().add(pieChart);
    }

    /**
     * Shows the Stats window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing stats page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the stats window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the stats window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the stats window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
