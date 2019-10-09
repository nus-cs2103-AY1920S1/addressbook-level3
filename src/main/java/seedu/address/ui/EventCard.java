package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.events.EventSource;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    public final EventSource eventSource;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label dateTime;

    /**
     * Constructor for EventCard. Creates an event card that will be placed in EventListPanel.
     * @param eventSource The instance of the event itself.
     * @param displayedIndex The number representing the event.
     */
    public EventCard(EventSource eventSource, int displayedIndex) {
        super(FXML);
        this.eventSource = eventSource;
        id.setText(displayedIndex + ". ");
        name.setText(eventSource.getDescription().toString());
        dateTime.setText(eventSource.getStartDateTime().toString());
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
                && eventSource.equals(card.eventSource);
    }
}
