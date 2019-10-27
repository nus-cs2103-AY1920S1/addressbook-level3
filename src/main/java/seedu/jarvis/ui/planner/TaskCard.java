package seedu.jarvis.ui.planner;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.ui.UiPart;

/**
 * A UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskDes;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label frequency;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        taskDes.setText(task.getTaskDes());
        priority.setText(task.getPriority().toString());
        frequency.setText(task.getFrequency().toString());
        status.setText(task.getStatus().toString());
        task.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        //state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
