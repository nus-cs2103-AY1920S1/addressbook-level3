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
public class AssignedTaskListPanel extends UiPart<Region> {
    private static final String FXML = "AssignedTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignedTaskListPanel.class);

    @FXML
    private ListView<Task> assignedTaskListView;

    public AssignedTaskListPanel(ObservableList<Task> assignedTaskList) {
        super(FXML);
        assignedTaskListView.setItems(assignedTaskList);
        assignedTaskListView.setCellFactory(listView -> new AssignedTaskListPanel.AssignedTaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class AssignedTaskListViewCell extends ListCell<Task> {
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
