package seedu.address.ui.panel.list;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    private UiParser uiParser;

    @FXML
    private ListView<EventSource> eventListView;

    /* =======================================To be implemented========================================== */
    @FXML
    private ListView<TaskSource> taskListView;

    /**
     * Constructor for EventListPanel. Creates an instance of a panel to store the Event Cards.
     */
    public EventListPanel(UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    public void setEventItems(List<EventSource> events) {
        this.eventListView.setItems(FXCollections.observableList(events));
    }

    public void setTaskItems(List<TaskSource> tasks) {
        this.taskListView.setItems(FXCollections.observableList(tasks));
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
                setGraphic(new EventCardStub(eventSource, getIndex() + 1, uiParser).getRoot());
            }
        }
    }

}
