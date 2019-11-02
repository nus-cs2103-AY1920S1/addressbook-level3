package seedu.jarvis.ui.planner;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.template.View;

/**
 * A View representing the list of {@code Task}.
 */
public class TaskListView extends View<AnchorPane> {
    private static final String FXML = "TaskListView.fxml";

    @FXML
    private ListView<Task> taskListView;

    public TaskListView(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    @Override
    public void fillPage() {
        taskListView.setItems(model.getFilteredTaskList());
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
