package seedu.address.ui.panel.calendar;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.*;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class CalendarPanel extends UiPart<Region> implements EventListListener {

    private static final String FXML = "CalendarPanel.fxml";

    private UiParser uiParser;
    private Calendar calendar;
    private TimelineDayView timeline;
    private Details details;

    @FXML
    private StackPane timelinePlaceholder;

    @FXML
    private VBox calendarPlaceholder;

    @FXML
    private VBox detailsPlaceholder;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public CalendarPanel(UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.calendar = new Calendar(uiParser);
        this.timeline = new TimelineDayView(15, 10, 2019, uiParser);
        this.details = new Details(uiParser);

        timelinePlaceholder.getChildren().add(this.timeline.getRoot()); // Left
        calendarPlaceholder.getChildren().add(this.calendar.getRoot()); // Top Right
        detailsPlaceholder.getChildren().add(this.details.getRoot()); // Bottom Right

    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        this.timeline.eventChange(events);
    }
}
