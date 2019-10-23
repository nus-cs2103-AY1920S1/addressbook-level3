package seedu.address.ui.panel.calendar;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.model.events.EventSource;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * An Ui that represents a Panel that contains all the components to make a calendar view. This includes
 * a Timeline, Calendar, and the Details of an event.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private UiParser uiParser;
    private CalendarScreen calendarScreen;
    private TimelineView timelineView;
    private Details details;
    private Instant date;
    private List<EventSource> eventList;

    @FXML
    private StackPane timelinePlaceholder;

    @FXML
    private VBox calendarScreenPlaceholder;

    @FXML
    private VBox detailsPlaceholder;

    /**
     * Constructor for CalendarPanel. Contains the 3 main viewing points for the calendar panel.
     * The points are calendar screen view, timeline view and details view.
     *
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     * @see TimelineView
     * @see CalendarScreen
     * @see Details
     */
    public CalendarPanel(UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.date = Instant.now();
        this.eventList = new ArrayList<>();

        Integer[] dayMonthYear = uiParser.getDateToNumbers(this.date);
        this.calendarScreen = new CalendarScreen(dayMonthYear[1], dayMonthYear[2], uiParser);
        this.timelineView = new TimelineDayView(
                dayMonthYear[0], dayMonthYear[1], dayMonthYear[2], eventList, uiParser);
        this.details = new Details(uiParser);

        timelinePlaceholder.getChildren().add(this.timelineView.getRoot()); // Left
        calendarScreenPlaceholder.getChildren().add(this.calendarScreen.getRoot()); // Top Right
        detailsPlaceholder.getChildren().add(this.details.getRoot()); // Bottom Right

    }

    /**
     * Changes the CalendarScreen date only.
     *
     * @param month The given month.
     * @param year The given year.
     */
    public void changeCalendarScreenDate(int month, int year) {
        calendarScreenPlaceholder.getChildren().clear();
        calendarScreen = new CalendarScreen(month, year, uiParser);
        calendarScreenPlaceholder.getChildren().add(calendarScreen.getRoot());
        calendarScreen.eventChange(eventList);
    }

    /**
     * Re-sizes the TimelineView.
     */
    public void resizeTimelineView() {
        timelineView.resizeTimelineView();
    }

    /**
     * Changes to TimelineDayView with a given day, month and year.
     *
     * @param day The given day.
     * @param month The given month.
     * @param year The given year.
     */
    public void changeToDayView(int day, int month, int year) {
        changeCalendarScreenDate(month, year);
        timelinePlaceholder.getChildren().clear();
        timelineView = new TimelineDayView(day, month, year, eventList, uiParser);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    /**
     * Changes to TimelineDayView with a given week, month and year.
     *
     * @param week The given week.
     * @param month The given month.
     * @param year The given year.
     */
    public void changeToWeekView(int week, int month, int year) {
        changeCalendarScreenDate(month, year);
        timelinePlaceholder.getChildren().clear();
        this.timelineView = new TimelineWeekView(
                week, month, year, calendarScreen.getStartingDay(week), eventList, uiParser);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    /**
     * Changes to TimelineDayView with a given month and year.
     *
     * @param month The given month.
     * @param year The given year.
     */
    public void changeToMonthView(int month, int year) {
        changeCalendarScreenDate(month, year);
        timelinePlaceholder.getChildren().clear();
        this.timelineView = new TimelineMonthView(month, year, eventList, uiParser);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    public void onEventListChange(List<EventSource> events) {
        this.timelineView.eventChange(events);
        this.calendarScreen.eventChange(events);
        this.eventList = events;
    }
}
