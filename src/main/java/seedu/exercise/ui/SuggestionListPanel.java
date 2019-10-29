package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.resource.Exercise;

/**
 * Panel containing the list of suggested exercises.
 */
public class SuggestionListPanel extends UiPart<Region> {
    private static final String FXML = "SuggestionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SuggestionListPanel.class);

    @FXML
    private ListView<Exercise> suggestionListView;

    public SuggestionListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML);
        suggestionListView.setItems(exerciseList);
        suggestionListView.setCellFactory(listView -> new SuggestionListViewCell());
    }

    public ListView<Exercise> getSuggestionListView() {
        return suggestionListView;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class SuggestionListViewCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseCard(exercise, getIndex() + 1).getRoot());
            }
        }
    }
}
