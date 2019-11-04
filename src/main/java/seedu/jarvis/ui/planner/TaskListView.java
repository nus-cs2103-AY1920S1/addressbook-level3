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
 * Represents the wrapper panel for the task list.
 */
public class TaskListView extends UiPart<Region> {
    private static final String FXML = "TaskListView.fxml";

    @FXML
    private ListView<Task> taskListView;
    @FXML
    private Label header;

    public TaskListView(ObservableList<Task> taskList) {
        super(FXML);
        header.setText("    All Tasks:");
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a
     * {@code TaskCard}
     */
    class TaskListViewCell extends ListCell<Task> {

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
