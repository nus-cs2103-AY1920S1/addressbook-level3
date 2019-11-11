//@@author dalsontws

package seedu.address.ui;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.model.deadline.Deadline;

/**
 * An UI component that displays information of a {@code FlashCard}.
 */
public class DeadlinePanel extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    public final Deadline deadline;

    @FXML
    private HBox deadlineCardPane;
    @FXML
    private TextFlow task;
    @FXML
    private Label id;
    @FXML
    private Label dueDate;

    public DeadlinePanel(Deadline deadline, int displayedIndex) {
        super(FXML);
        this.deadline = deadline;
        id.setText(displayedIndex + ". ");
        Text deadlineText = new Text (deadline.getTask().toString());
        deadlineText.setId("deadlinetext");
        task.getChildren().add(deadlineText);
        LocalDate today = LocalDate.now();
        LocalDate due = deadline.getDueDate().getLocalDate();
        if (true) {
            deadlineText.setFill(Paint.valueOf("red"));
        }

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
