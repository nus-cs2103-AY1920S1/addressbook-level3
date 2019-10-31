package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class UnassignedTaskCard extends UiPart<Region> {

    private static final String FXML = "UnassignedTaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label customerId;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label address;
    @FXML
    private Label deliverTo;
    @FXML
    private Label taskId;

    public UnassignedTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().value);
        taskId.setText("Task ID: #" + task.getId());
        deliverTo.setText("Deliver To: " + task.getCustomer().getName().fullName
                + " (#" + task.getCustomer().getId() + ") - " + task.getCustomer().getPhone());
        address.setText("Address: " + task.getCustomer().getAddress().value);
        date.setText("Date: " + task.getDatePrint());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignedTaskCard)) {
            return false;
        }

        // state check
        UnassignedTaskCard card = (UnassignedTaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
