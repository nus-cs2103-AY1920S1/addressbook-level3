package seedu.address.ui.panel.calendar;

import java.time.Instant;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * An Ui that represents a Panel that contains all the components to make a calendar view. This includes
 * a Timeline, Calendar, and the Details of an event.
 */
public class CalendarPanel extends UiPart<Region> implements EventListListener {

    private static final String FXML = "CalendarPanel.fxml";

    private UiParser uiParser;
    private CalendarScreen calendarScreen;
    private TimelineDayView timelineDayView;
    private Details details;
    private Instant date;

    @FXML
    private StackPane timelinePlaceholder;

    @FXML
    private VBox calendarPlaceholder;

    @FXML
    private VBox detailsPlaceholder;

    /**
     * Constructor for CalendarPanel. Contains the 3 main viewing points for the calendar panel.
     * The points are calendar screen view, timeline view and details view.
     *
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     */
    public CalendarPanel(UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.date = Instant.now();

        Integer[] dayMonthYear = uiParser.getDateToNumbers(this.date);
        this.calendarScreen = new CalendarScreen(dayMonthYear[1], dayMonthYear[2], uiParser);
        this.timelineDayView = new TimelineDayView(dayMonthYear[0], dayMonthYear[1], dayMonthYear[2], uiParser);
        this.details = new Details(uiParser);

        timelinePlaceholder.getChildren().add(this.timelineDayView.getRoot()); // Left
        calendarPlaceholder.getChildren().add(this.calendarScreen.getRoot()); // Top Right
        detailsPlaceholder.getChildren().add(this.details.getRoot()); // Bottom Right

    }

    /**
     * Changes the timeline of the timeline to the given date. This will automatically change the Calendar Screen
     * as well to fit the given date.
     *
     * @param dateTime The given date by the user.
     */
    public void changeTimelineDate(Instant dateTime) {
        Integer[] dayMonthYear = uiParser.getDateToNumbers(dateTime);
        // Change Timeline Date
        timelinePlaceholder.getChildren().clear();
        timelineDayView = new TimelineDayView(dayMonthYear[0], dayMonthYear[1], dayMonthYear[2], uiParser);
        timelinePlaceholder.getChildren().add(timelineDayView.getRoot());

        // Change CalendarScreen Date
        calendarPlaceholder.getChildren().clear();
        calendarScreen = new CalendarScreen(dayMonthYear[1], dayMonthYear[2], uiParser);
        calendarPlaceholder.getChildren().add(calendarScreen.getRoot());
    }

    /**
     * Changes the Calendar Screen only to the given date.
     *
     * @param dateTime The given date by the user.
     */
    public void changeCalendarScreenDate(Instant dateTime) {
        Integer[] dayMonthYear = uiParser.getDateToNumbers(dateTime);
        // Change CalendarScreen Date
        calendarPlaceholder.getChildren().clear();
        calendarScreen = new CalendarScreen(dayMonthYear[1], dayMonthYear[2], uiParser);
        calendarPlaceholder.getChildren().add(calendarScreen.getRoot());
    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        this.timelineDayView.onEventListChange(events);
    }
}
