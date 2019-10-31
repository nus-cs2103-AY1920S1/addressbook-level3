package seedu.address.ui.card;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.tasks.TaskSource;


/**
 * An UI component that displays information of a {@code Event}.
 */
public class TaskCard extends Card {

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private StackPane taskCardBase;

    @FXML
    private VBox taskDetails;

    @FXML
    private Label taskName;

    @FXML
    private StackPane taskDueDateBase;

    @FXML
    private Label taskDueDate;

    @FXML
    private StackPane taskTagsBase;

    @FXML
    private HBox taskTags;

    @FXML
    private Label taskIndex;

    @FXML
    private StackPane taskIndexBase;

    /**
     * Constructor for the TaskCard, which displays the information of a particular task.
     * This is used for ListPanel.
     *
     * @param task The given task.
     */
    public TaskCard(TaskSource task, Integer index) {
        super(FXML);
        taskName.setText(task.getDescription());
        taskIndex.setText("[" + index + "]");
        addOptions(task);
    }

    /**
     * Constructor for the TaskCard, which displays the information of a particular task.
     * This is used for CalendarPanel.
     *
     * @param task The given task.
     */
    public TaskCard(TaskSource task) {
        super(FXML);
        taskName.setText(task.getDescription());
        taskIndexBase.getChildren().remove(taskIndex);
        addOptions(task);
    }

    /**
     * Removes the non-existant options from the task card of the given task.
     *
     * @param task The given task.
     */
    private void addOptions(TaskSource task) {
        // Due Date
        if (task.getDueDate() != null) {
            taskDueDate.setText(task.getDueDate().toEnglishDateTime());
        } else {
            taskDetails.getChildren().remove(taskDueDateBase);
        }

        // Tags
        if (task.getTags() != null) {
            Set<String> tags = task.getTags();
            for (String tag : tags) {
                CardTag cardTag = new CardTag(tag);
                if (task.isDone()) {
                    cardTag.changeColor("-taskDoneTagColor");
                } else {
                    cardTag.changeColor("-taskTagColor");
                }
                taskTags.getChildren().add(cardTag.getRoot());
            }
        } else {
            taskDetails.getChildren().remove(taskTagsBase);
        }

        // Color
        if (task.isDone()) {
            taskCardBase.setStyle("-fx-background-color: -taskDoneCardColor;");
        }

        taskName.setMinHeight(Region.USE_PREF_SIZE);

    }
}
