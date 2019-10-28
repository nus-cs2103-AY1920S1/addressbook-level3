package seedu.address.ui.panel.list;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.EventCard;

import java.util.List;

public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";

    @FXML
    private VBox eventList;

    public EventListPanel() {
        super(FXML);
    }

    public void onEventListChange(List<EventSource> events) {
        this.eventList.getChildren().clear();
        for(EventSource event : events) {
            EventCard eventCard = new EventCard(event);
            eventList.getChildren().add(eventCard.getRoot());
        }
    }
}
