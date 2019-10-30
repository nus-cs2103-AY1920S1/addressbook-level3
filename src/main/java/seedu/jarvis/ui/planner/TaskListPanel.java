package seedu.jarvis.ui.planner;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.jarvis.commons.core.LogsCenter;

import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.ui.UiPart;

/**
 * A panel containing a list of tasks
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListView.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<Task> taskListView;

    public TaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a
     * {@code TaskCard}w
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
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
