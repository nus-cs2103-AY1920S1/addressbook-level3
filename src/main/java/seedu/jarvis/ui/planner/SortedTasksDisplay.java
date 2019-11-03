package seedu.jarvis.ui.planner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.ui.UiPart;

/**
 * Represents a text box for the output of any sorting of tasks - by date
 * or tags.
 */
public class SortedTasksDisplay extends UiPart<Region> {
    public static final String FXML = "SortedTaskDisplay.fxml";

    @FXML
    private ListView<Task> sortedTaskDisplay;

    public SortedTasksDisplay(ObservableList<Task> tasks) {
        super(FXML);
        sortedTaskDisplay.setItems(tasks);
        sortedTaskDisplay.setCellFactory(listView -> new SortedTaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a
     * {@code TaskCard}
     */
    class SortedTaskListViewCell extends ListCell<Task> {

        @Override
        public void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }

    }
}
