package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.EventSource;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventSource> eventListView;

    public EventListPanel(ObservableList<EventSource> eventList) {
        super(FXML);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
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
