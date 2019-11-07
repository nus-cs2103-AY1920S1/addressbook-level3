package seedu.pluswork.ui.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.ui.UiPart;

public class TaskCardForStats extends UiPart<Region> {
    private static final String FXML = "TaskCardForStats.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label timeTaken;

    public TaskCardForStats(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        timeTaken.setText(task.getTimeSpent());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCardForStats card = (TaskCardForStats) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
