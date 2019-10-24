package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.events.EventSource;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventCard.fxml";

    private UiParser uiParser;
    private EventSource event;
    private String eventName;
    private String eventDate;

    @FXML
    private StackPane eventCardBase;

    @FXML
    private Label eventCardName;

    @FXML
    private Label eventCardDate;

    /**
     * Constructor for the EventCard, which displays the information of a particular event.
     *
     * @param event The given event.
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     */

    public EventCard(EventSource event, UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.event = event;
        this.eventName = event.getDescription();
        this.eventDate = uiParser.getTime(event.getStartDateTime().toInstant());

        eventCardName.setText(this.eventName);
        eventCardDate.setText(this.eventDate);
    }

}
