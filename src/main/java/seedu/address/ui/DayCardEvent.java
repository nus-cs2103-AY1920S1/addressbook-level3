package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import jdk.jfr.Event;
import seedu.address.model.events.EventSource;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class DayCardEvent extends UiPart<Region> {

    private static final String FXML = "DayCardEvent.fxml";
    private EventSource event;

    @FXML
    private Label eventDescription;

    public DayCardEvent(EventSource event) {
        super(FXML);
        this.event = event;
        this.eventDescription.setText(event.getDescription());
        eventDescription.setPadding(new Insets(0, 0, 0, 5));
    }

    public EventSource getEvent() {
        return this.event;
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
        DayCardEvent card = (DayCardEvent) other;
        return eventDescription.equals(card.eventDescription);
    }
}
