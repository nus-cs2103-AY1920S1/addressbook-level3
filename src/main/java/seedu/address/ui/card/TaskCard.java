package seedu.address.ui.card;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.tasks.TaskSource;

//@@author Kyzure
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
    private VBox taskTagList;

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
     * Removes the non-existant options from the task card of the given task.
     *
     * @param task The given task.
     */
    private void addOptions(TaskSource task) {
        // Due Date
        if (task.getDueDate() != null) {
            taskDueDate.setText(task.getDueDate().toString());
        } else {
            taskDetails.getChildren().remove(taskDueDateBase);
        }

        // Tags
        if (task.getTags() != null) {
            addTags(task);
        } else {
            taskDetails.getChildren().remove(taskTagsBase);
        }

        // Color
        if (task.isDone()) {
            taskCardBase.setStyle("-fx-background-color: -taskDoneCardColor;");
        }

        taskName.setMinHeight(Region.USE_PREF_SIZE);

    }

    /**
     * Adds CardTagline to the TaskCard with the given tags.
     *
     * @param task The given task.
     * @see CardTagline
     */
    private void addTags(TaskSource task) {
        Set<String> tags = task.getTags();
        CardTagline cardTagline = new CardTagline();
        for (String tag : tags) {
            CardTag cardTag = new CardTag(tag);
            if (task.isDone()) {
                cardTag.changeColor("-taskDoneTagColor");
            } else {
                cardTag.changeColor("-taskTagColor");
            }
            if (cardTag.getWidth() >= CardTagline.MAX_WIDTH) {
                CardTagline cardTagline1 = new CardTagline();
                cardTagline1.addSingleTag(cardTag);
                taskTagList.getChildren().add(cardTagline1.getRoot());
                continue;
            }
            boolean isAdded = cardTagline.isTagAdded(cardTag);
            if (!isAdded) {
                taskTagList.getChildren().add(cardTagline.getRoot());
                cardTagline = new CardTagline();
                cardTagline.isTagAdded(cardTag);
            }
        }
        taskTagList.getChildren().add(cardTagline.getRoot());
    }
}
