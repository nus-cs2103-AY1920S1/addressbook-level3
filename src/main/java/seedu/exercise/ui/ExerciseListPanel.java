package seedu.exercise.ui;

import static java.util.Objects.requireNonNull;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.exercise.model.resource.Exercise;

/**
 * Panel containing the list of exercises.
 */
public class ExerciseListPanel extends ResourceListPanel {

    private static final String FXML = "ExerciseListPanel.fxml";

    @FXML
    private ListView<Exercise> exerciseListView;

    @FXML
    private Label exerciseTitle;

    public ExerciseListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML, exerciseList);
        exerciseListView.setItems(exerciseList);
        exerciseListView.setCellFactory(listView -> new ExerciseListViewCell());
        exerciseListView.getFocusModel().focusedItemProperty().addListener(getDefaultListViewListener());
    }

    public ListView<Exercise> getResourceListView() {
        return exerciseListView;
    }

    public void setPanelTitleText(String title) {
        requireNonNull(title);
        exerciseTitle.setText(title);
    }

    @Override
    protected void selectGivenIndex(int index) {
        if (index >= 0) {
            /*
                An extremely hacky way to get the list to select, focus and scroll to the newly changed item.
                Without this method, when any add/edit commands are supplied, the ListChangeListener attached to
                ObservableList is called first without the list actually changing its structure. So when the index
                is provided, the listview is not updated and thus cannot be focused on.
                So the solution is to make this focusing operation be done at a slightly later time when the
                list view has been updated to reflect the commands changes
             */
            Platform.runLater(() -> selectFocusAndScrollTo(exerciseListView, index));
        }
    }

    @Override
    protected void resetListSelection() {
        exerciseListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseInfoPanel}.
     */
    class ExerciseListViewCell extends ListCell<Exercise> {
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
