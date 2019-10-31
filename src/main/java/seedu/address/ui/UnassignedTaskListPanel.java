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
public class UnassignedTaskListPanel extends UiPart<Region> {
    private static final String FXML = "UnassignedTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(UnassignedTaskListPanel.class);

    @FXML
    private ListView<Task> unassignedTaskListView;

    public UnassignedTaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        unassignedTaskListView.setItems(taskList);
        unassignedTaskListView.setCellFactory(listView -> new UnassignedTaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class UnassignedTaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new UnassignedTaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }
}
