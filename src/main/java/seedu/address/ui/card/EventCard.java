package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.model.events.EventSource;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends Card {

    private static final String FXML = "EventCard.fxml";

    @FXML
    private Label eventCardName;

    @FXML
    private Label eventCardDate;

    /**
     * Constructor for the EventCard, which displays the information of a particular event.
     *
     * @param event The given event.
     */
    public EventCard(EventSource event) {
        super(FXML);
        eventCardName.setText(event.getDescription());
        eventCardDate.setText(event.getStartDateTime().toEnglishDateTime());
        eventCardName.setMinHeight(Region.USE_PREF_SIZE);
    }
}
