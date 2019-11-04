package seedu.jarvis.ui.planner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.layout.Region;
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
    @FXML
    private Label header;

    public SortedTasksDisplay(ObservableList<Task> tasks, PlannerUiType type) {
        super(FXML);
        if (type == PlannerUiType.SCHEDULE) {
            header.setText("  Your tasks for today:");
        } else {
            header.setText("  The items matching your keywords are:");
        }
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
