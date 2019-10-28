package seedu.address.ui.panel.list;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.*;

import jdk.jfr.Event;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.EventCard;


/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class ListPanel extends UiPart<Region> {

    private static final String FXML = "ListPanel.fxml";

    private EventListPanel eventListPanel;
    private TaskListPanel taskListPanel;

    @FXML
    private GridPane listPanelGrid;

    @FXML
    private StackPane eventListPlaceholder;

    @FXML
    private StackPane taskListPlaceholder;

    /**
     * Constructor for ListPanel
     */
    public ListPanel() {
        super(FXML);
        eventListPanel = new EventListPanel();
        taskListPanel = new TaskListPanel();

        eventListPlaceholder.getChildren().add(eventListPanel.getRoot());
        taskListPlaceholder.getChildren().add(taskListPanel.getRoot());
    }

    public void onEventListChange(List<EventSource> events) {
        eventListPanel.onEventListChange(events);
    }
}
