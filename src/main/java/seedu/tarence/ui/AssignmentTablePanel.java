package seedu.tarence.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.student.Student;

/**
 * Panel containing table display for assignments.
 */
public class AssignmentTablePanel extends UiPart<Region> {
    private static final String FXML = "AssignmentTablePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignmentTablePanel.class);
    private long lowerPercentile;
    private long upperPercentile;
    private boolean isDefault;

    @FXML
    private Pane pane;

    @FXML
    private ListView<ScoreCard> assignmentScoreListView;

    public AssignmentTablePanel() {
        super(FXML);
        this.pane = new StackPane();
        setDefaultPlaceHolderLabel();
    }

    /**
     * Generates the table based on the given hashmap of results.
     * @param scores - hashmap of students and their scores)
     */
    public void generateTable(Map<Student, Integer> scores) {
        requireNonNull(scores);
        if (!isEmpty(scores)) {
            assignmentScoreListView = new ListView<>();
            setStatistics(scores);
            ObservableList<ScoreCard> scoreList = getScoreList(scores);
            assignmentScoreListView.setItems(scoreList);
            assignmentScoreListView.setCellFactory(listView -> new ScoreListViewCell());
            this.isDefault = false;
            pane.getChildren().clear();
            pane.getChildren().add(assignmentScoreListView);
        } else {
            setDefaultPlaceHolderLabel();
        }
    }

    private ObservableList<ScoreCard> getScoreList(Map<Student, Integer> scores) {
        ObservableList<ScoreCard> scoreList = FXCollections.observableArrayList();
        Iterator<Map.Entry<Student, Integer>> iterator = scores.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Student, Integer> entry = iterator.next();
            scoreList.add(new ScoreCard(entry.getKey(), entry.getValue(), lowerPercentile, upperPercentile));
        }
        return scoreList;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScoreCard}.
     */
    class ScoreListViewCell extends ListCell<ScoreCard> {

        @Override
        protected void updateItem(ScoreCard scoreCard, boolean empty) {
            super.updateItem(scoreCard, empty);

            if (empty || scoreCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(scoreCard.getRoot());
            }
        }
    }

    /**
     * @return Pane with attendance table to display.
     */
    public Pane getPane() {
        return this.pane;
    }

    /**
     * sets default placeholder. To be used only during exceptions.
     */
    private void setDefaultPlaceHolderLabel() {
        this.isDefault = true;
        String defaultMessage = "Sorry :( there are no scores to display";

        Label placeholder = new Label(defaultMessage);
        pane.getChildren().add(placeholder);
    }

    /**
     * sets the 75th and 25th percentile scores.
     * @param namesAndScores - hashmap containing the names and scores of the students.
     */
    private void setStatistics(Map<Student, Integer> namesAndScores) {
        this.upperPercentile = calcPercentile(namesAndScores, 75);
        this.lowerPercentile = calcPercentile(namesAndScores, 25);
        logger.info("Upper percentile: " + upperPercentile);
        logger.info("Lower percentile: " + lowerPercentile);
    }

    /**
     * calculates the given percentile from the hashmap values.
     * @param map - hashmap containing the names and scores of the students.
     * @param percentile - percentile to calculate.
     */
    private long calcPercentile(Map<Student, Integer> map, double percentile) {
        ArrayList<Integer> scores = (ArrayList<Integer>) map.values()
                .stream().filter(i -> i != -1).collect(Collectors.toList());
        Collections.sort(scores);
        int index = (int) Math.ceil((percentile / 100) * scores.size());
        return scores.get(index - 1);
    }

    /**
     * Checks if the given hashmap is empty.
     * scores of -1 are considered as empty scores.
     */
    private boolean isEmpty(Map<Student, Integer> scores) {
        long numScores = scores.values().stream().filter(i -> i != -1).count();
        logger.info("Number of students in assignment: " + numScores);
        return (numScores == 0);
    }

    /**
     * Returns true is the default view is being displayed.
     */
    public boolean isDefaultView() {
        return this.isDefault;
    }
}
