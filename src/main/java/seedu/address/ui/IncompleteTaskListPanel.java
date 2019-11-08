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
 * Panel containing the list of persons.
 */
public class IncompleteTaskListPanel extends UiPart<Region> {
    private static final String FXML = "IncompleteTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IncompleteTaskListPanel.class);

    @FXML
    private ListView<Task> incompleteTaskListView;

    public IncompleteTaskListPanel(ObservableList<Task> incompleteTaskList) {
        super(FXML);
        incompleteTaskListView.setItems(incompleteTaskList);
        incompleteTaskListView.setCellFactory(listView -> new IncompleteTaskListPanel.IncompleteTaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class IncompleteTaskListViewCell extends ListCell<Task> {
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
