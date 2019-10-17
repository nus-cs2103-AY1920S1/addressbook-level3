package seedu.address.itinerary.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.model.Event.Event;
import seedu.address.ui.UiPart;

import java.util.logging.Logger;

/**
 * Panel containing the list of expenses.
 */
public class EventPanel extends UiPart<Region> {
    private static final String fxmlPanel = "EventPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventPanel.class);

    @FXML
    private ListView<Event> eventListView;

    public EventPanel(ObservableList<Event> eventList) {
        super(fxmlPanel);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }
}
