package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.events.EventSource;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends Card {

    private static final String FXML = "EventCard.fxml";

    @FXML
    private VBox eventDetails;

    @FXML
    private Label eventName;

    @FXML
    private Label eventStartDate;

    @FXML
    private Label eventEndDate;

    @FXML
    private StackPane eventEndDateBase;

    /**
     * Constructor for the EventCard, which displays the information of a particular event.
     *
     * @param event The given event.
     */
    public EventCard(EventSource event) {
        super(FXML);
        eventName.setText(event.getDescription());
        eventStartDate.setText(event.getStartDateTime().toEnglishDateTime());
        if(event.getEnd() != null) {
            eventEndDate.setText(event.getEnd().toEnglishDateTime());
        } else {
            eventDetails.getChildren().remove(eventEndDateBase);
        }
        eventName.setMinHeight(Region.USE_PREF_SIZE);
    }
}
