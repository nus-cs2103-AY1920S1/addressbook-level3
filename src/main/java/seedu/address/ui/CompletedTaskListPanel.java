package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.Task;

/**
 * Panel containing the list of tasks.
 */
public class CompletedTaskListPanel extends UiPart<Region> {
    private static final String FXML = "CompletedTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignedTaskListPanel.class);

    @FXML
    private ListView<Task> completedTaskListView;

    public CompletedTaskListPanel(ObservableList<Task> completedTaskList) {
        super(FXML);
        completedTaskListView.setItems(completedTaskList);
        completedTaskListView.setCellFactory(listView -> new CompletedTaskListPanel.CompletedTaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code UnassignedTaskCard}.
     */
    class CompletedTaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssignedTaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }
}
