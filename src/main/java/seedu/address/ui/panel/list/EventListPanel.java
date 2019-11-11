package seedu.address.ui.panel.list;

import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.EventCard;

//@@author Kyzure
/**
 * Represents the Event List to display the events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";

    @FXML
    private VBox eventList;

    public EventListPanel() {
        super(FXML);
    }

    /**
     * Changes the Event List Panel according to the given List of Events.
     *
     * @param events The given List of Events.
     */
    public void onEventListChange(List<EventSource> events, HashMap<EventSource, Integer> eventHash) {
        this.eventList.getChildren().clear();
        for (EventSource event : events) {
            EventCard eventCard = new EventCard(event, eventHash.get(event));
            eventList.getChildren().add(eventCard.getRoot());
        }
    }
}
