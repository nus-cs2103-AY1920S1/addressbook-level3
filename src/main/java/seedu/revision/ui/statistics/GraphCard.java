package seedu.revision.ui.statistics;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.revision.ui.UiPart;

/**
 * An UI component that displays line graph of a {@code scoreList}.
 */
public class GraphCard extends UiPart<Region> {

    private static final String FXML = "GraphListCard.fxml";
    private final ObservableList<Double> scoreList;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RevisionTool level 4</a>
     */
    @FXML
    private HBox cardPane;

    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private XYChart.Series series = new XYChart.Series();

    public GraphCard(ObservableList<Double> scoreList, int displayedIndex) {
        super(FXML);
        this.scoreList = scoreList;
        xAxis.setLabel("Number of quiz attempts");
        yAxis.setLabel("score (%)");
        series.setName("scores");

        switch (displayedIndex) {
        case 1:
            lineChart.setTitle("Overall results");
            break;
        case 2:
            lineChart.setTitle("Results for Difficulty 1 Questions");
            break;
        case 3:
            lineChart.setTitle("Results for Difficulty 2 Questions");
            break;
        case 4:
            lineChart.setTitle("Results for Difficulty 3 Questions");
            break;
        default:
            assert false : displayedIndex;
        }

        for (int i = 0; i < scoreList.size(); i++) {
            series.getData().add(new XYChart.Data(i + 1, scoreList.get(i)));
        }

        lineChart.getData().add(series);

        cardPane = new HBox();
        cardPane.getChildren().addAll(lineChart);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GraphCard)) {
            return false;
        }

        // state check
        GraphCard card = (GraphCard) other;
        return scoreList.equals(card.scoreList);
    }
}

