package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.EventTime;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AssignedTaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    private Label assigned;
    @FXML
    private Label taskId;

    public AssignedTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().value);
        taskId.setText("Task ID: #" + task.getId());
        assigned.setText("Assigned: " + task.getEventTime().map(EventTime::toString).orElse("NOT ALLOCATED") + " @ "
                + task.getDriver().get().getName().fullName + " (#" + task.getDriver().get().getId()
                + ") - " + task.getDriver().get().getPhone());
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
        if (!(other instanceof AssignedTaskCard)) {
            return false;
        }

        // state check
        AssignedTaskCard card = (AssignedTaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
