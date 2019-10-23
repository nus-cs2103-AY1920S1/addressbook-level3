package seedu.address.ui.panels;

import static seedu.address.model.task.Task.FORMAT_FILE_DATE_STRING;
import static seedu.address.model.task.Task.FORMAT_FILE_TIME_STRING;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskListCard extends PanelComponent<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label heading;
    @FXML
    private Label id;
    @FXML
    private Label status;
    @FXML
    private Label date;
    @FXML
    private Label time;

    public TaskListCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        heading.setText(task.getHeading().toString());
        status.setText(task.getStatusIcon());
        date.setText(task.getDate().format(FORMAT_FILE_DATE_STRING));
        time.setText(task.getTime().format(FORMAT_FILE_TIME_STRING));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskListCard)) {
            return false;
        }

        // state check
        TaskListCard card = (TaskListCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}

