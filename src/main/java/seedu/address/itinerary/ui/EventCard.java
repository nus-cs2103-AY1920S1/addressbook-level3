package seedu.address.itinerary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.itinerary.model.Event.Event;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class EventCard extends UiPart<Region> {

    private static final String fxmlCard = "EventCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label title;

    @FXML
    private Label loc;

    @FXML
    private Label desc;

    @FXML
    private Label time;

    @FXML
    private ImageView checkBox;

    public EventCard(Event event, int displayedIndex) {
        super(fxmlCard);
        this.event = event;
        id.setText(displayedIndex + ". ");
        title.setText(event.getTitle().title);
        desc.setText(event.getDesc().desc);
        loc.setText(event.getLocation().location);
        time.setText(event.getTime().time);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}