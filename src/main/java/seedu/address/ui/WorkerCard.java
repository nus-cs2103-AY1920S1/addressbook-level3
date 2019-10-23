package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.entity.worker.Worker;

/**
 * An UI component that displays information of a {@code Worker}.
 */
public class WorkerCard extends UiPart<Region> {

    private static final String FXML = "WorkerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Worker worker;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label workerId;
    @FXML
    private Label dateJoined;

    public WorkerCard(Worker worker, int displayedIndex) {
        super(FXML);
        this.worker = worker;
        id.setText(displayedIndex + ". ");
        name.setText(worker.getName().fullName);
        workerId.setText(worker.getIdNum().toString());
        dateJoined.setText(worker.getDateJoined().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkerCard)) {
            return false;
        }

        // state check
        WorkerCard card = (WorkerCard) other;
        return id.getText().equals(card.id.getText())
                && worker.equals(card.worker);
    }
}
