package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.EventSource;

/**
 * Panel containing the list of persons.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventSource> eventListView;

    /**
     * Constructor for EventListPanel. Creates an instance of a panel to store the Event Cards.
     * @param eventList The list containing all the events.
     */
    public EventListPanel(ObservableList<EventSource> eventList) {
        super(FXML);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<EventSource> {
        @Override
        protected void updateItem(EventSource eventSource, boolean empty) {
            super.updateItem(eventSource, empty);

            if (empty || eventSource == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(eventSource, getIndex() + 1).getRoot());
            }
        }
    }

}
