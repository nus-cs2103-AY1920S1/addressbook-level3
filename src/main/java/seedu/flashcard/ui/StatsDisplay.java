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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.model.Statistics;

/**
 * Creates a scrollabe display for a graphical summary of the stats
 */
public class StatsDisplay extends UiPart<ScrollPane> {

    private static final String FXML = "StatsDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(StatsDisplay.class);

    @FXML
    private PieChart accuracy;
    @FXML
    private PieChart completion;
    @FXML
    private HBox pieCharts;
    @FXML
    private VBox statsBox;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private StackedBarChart<String, Number> individualCards;


    public StatsDisplay (Statistics statistics) {
        super(FXML);
        ObservableList<PieChart.Data> accuracyPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total Correct", statistics.getTotalCorrect()),
                new PieChart.Data("Total Wrong", statistics.getTotalWrong()));

        accuracy = new PieChart(accuracyPieChartData);
        accuracy.setTitle("Overall Accuracy");

        ObservableList<PieChart.Data> completionPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total Attempted", statistics.getTotalAttempted()),
                new PieChart.Data("Total Unattempted", statistics.getTotalUnattempted()));

        completion = new PieChart(completionPieChartData);
        completion.setTitle("Overall Completion");

        individualCards.getData().addAll(statistics.getCorrectSeries(), statistics.getWrongSeries());

        pieCharts.getChildren().addAll(completion, accuracy);




    }



}
