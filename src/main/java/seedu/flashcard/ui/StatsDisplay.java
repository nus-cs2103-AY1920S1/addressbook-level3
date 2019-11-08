package seedu.flashcard.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.model.Statistics;

/**
 * Creates a scrollabe display for a graphical summary of the stats
 */
public class StatsDisplay extends UiPart<Stage> {

    private static final String FXML = "StatsDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(StatsDisplay.class);

    private Statistics statistics;

    @FXML
    private PieChart accuracy;
    @FXML
    private PieChart completion;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private StackedBarChart<String, Number> individualCards;


    public StatsDisplay (Statistics statistics, Stage root) {
        super(FXML, root);
        this.statistics = statistics;
        updateStats();
    }

    /**
     * Creates a new Stats Window.
     */
    public StatsDisplay(Statistics statistics) {
        this(statistics, new Stage());
    }

    /**
     * repopulates the graphs with updated stats
     */
    public void updateStats() {
        ObservableList<PieChart.Data> accuracyPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total Correct", statistics.getTotalCorrect()),
                new PieChart.Data("Total Wrong", statistics.getTotalWrong()));

        accuracy.getData().clear();
        accuracy.getData().addAll(accuracyPieChartData);
        accuracy.setTitle("Overall Accuracy");

        ObservableList<PieChart.Data> completionPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total Attempted", statistics.getTotalAttempted()),
                new PieChart.Data("Total Unattempted", statistics.getTotalUnattempted()));

        completion.getData().clear();
        completion.getData().addAll(completionPieChartData);
        completion.setTitle("Overall Completion");

        individualCards.getData().clear();
        individualCards.getData().addAll(statistics.getCorrectSeries(), statistics.getWrongSeries());
        individualCards.setTitle("Statistics by card");


        scrollPane.setFitToHeight(true);

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
        logger.fine("Showing stats about the tasks.");
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
