package seedu.address.ui.queue;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.queue.Room;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person} in consultation with a {@code Person}.
 */
public class RoomCard extends UiPart<Region> {

    private static final String FXML = "queue/RoomListCard.fxml";

    public final Room room;

    @FXML
    private HBox cardPane;
    @FXML
    private Label doctorName;
    @FXML
    private Label name;
    @FXML
    private Label refId;

    public RoomCard(Room room, int displayedIndex) {
        super(FXML);
        this.room = room;
        doctorName.setText(room.getDoctor().getName().fullName);
        name.setText(room.getPatientCurrentlyBeingServed().getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoomCard)) {
            return false;
        }

        // state check
        RoomCard card = (RoomCard) other;
        return room.equals(card.room);
    }
}
