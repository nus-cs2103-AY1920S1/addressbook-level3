package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.note.Note;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * An UI component that displays information of a {@code Note}.
 */
public class TaskListCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
//    @FXML
//    private Label content;
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
        title.setText(task.getNote().getTitle().title);
//        content.setText(note.getContent().content);
        status.setText(task.getStatusIcon());
        date.setText(task.getDate().toString());
        time.setText(task.getTime().toString());
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

