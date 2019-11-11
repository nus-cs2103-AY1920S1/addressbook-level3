package seedu.jarvis.ui.planner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.ui.UiPart;

/**
 * Represents the output of any sorting of tasks by task description
 */
public class SortedTasksDisplay extends UiPart<Region> {
    public static final String FXML = "SortedTaskDisplay.fxml";

    @FXML
    private ListView<Task> sortedTaskDisplay;
    @FXML
    private Label header;

    public SortedTasksDisplay(ObservableList<Task> tasks) {
        super(FXML);
        header.setText("  The items matching your keywords are:");
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
