package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.deadline.Deadline;

//@@author dalsontws
/**
 * An UI component that displays information of a {@code FlashCard}.
 */
public class DeadlinePanel extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    public final Deadline deadline;

    @FXML
    private HBox deadlineCardPane;
    @FXML
    private Label task;
    @FXML
    private Label id;
    @FXML
    private Label dueDate;

    public DeadlinePanel(Deadline deadline, int displayedIndex) {
        super(FXML);
        this.deadline = deadline;
        id.setText(displayedIndex + ". ");
        task.setText(deadline.getTask().toString());
        dueDate.setText("Due Date: " + deadline.getDueDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeadlinePanel)) {
            return false;
        }

        // state check
        DeadlinePanel card = (DeadlinePanel) other;
        return id.getText().equals(card.id.getText())
                && deadline.equals(card.deadline);
    }
}
