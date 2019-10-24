package seedu.address.ui.panel.list;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;


/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class ListPanel extends UiPart<Region> implements EventListListener {

    private static final String FXML = "ListPanel.fxml";
    private UiParser uiParser;
    private EventListPanel eventListPanel;

    @FXML
    private VBox list;

    @FXML
    private AnchorPane eventList;

    @FXML
    private AnchorPane taskListl;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public ListPanel(UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.eventListPanel = new EventListPanel(uiParser);
        // eventList.getChildren().add(eventListPanel.getRoot());
    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        this.eventListPanel.setItems(events);
    }
}
