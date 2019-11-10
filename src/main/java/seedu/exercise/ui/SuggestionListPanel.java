package seedu.exercise.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.exercise.model.resource.Exercise;

/**
 * Panel containing the list of suggested exercises.
 */
public class SuggestionListPanel extends ResourceListPanel {
    private static final String FXML = "SuggestionListPanel.fxml";

    @FXML
    private ListView<Exercise> suggestionListView;

    public SuggestionListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML, exerciseList);
        suggestionListView.setItems(exerciseList);
        suggestionListView.setCellFactory(listView -> new SuggestionListViewCell());
        suggestionListView.getFocusModel().focusedItemProperty().addListener(getDefaultListViewListener());
    }

    public ListView<Exercise> getSuggestionListView() {
        return suggestionListView;
    }

    @Override
    public void resetListSelection() {
        suggestionListView.getSelectionModel().clearSelection();
    }

    @Override
    public ListView<Exercise> getResourceListView() {
        return suggestionListView;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseInfoPanel}.
     */
    class SuggestionListViewCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseListCard(exercise, getIndex() + 1).getRoot());
            }
        }
    }
}
