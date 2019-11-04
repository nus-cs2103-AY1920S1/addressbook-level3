package seedu.jarvis.ui.planner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
//            setGraphic(new Header("These are the tasks due today:").getRoot());
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new Header("These are the tasks due today:").getRoot());
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }

    }
}
