package seedu.address.ui.panel.calendar;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;

/**
 * An Ui that represents a Panel that contains all the components to make a calendar view. This includes
 * a Timeline, Calendar, and the Details of an event.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private CalendarScreen calendarScreen;
    private TimelineView timelineView;
    private Details details;
    private CalendarDate calendarDate;
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
     * @see TimelineView
     * @see CalendarScreen
     * @see Details
     */
    public CalendarPanel() {
        super(FXML);
        this.calendarDate = CalendarDate.now();
        this.eventList = new ArrayList<>();

        this.calendarScreen = new CalendarScreen(this.calendarDate);
        this.timelineView = new TimelineDayView(this.calendarDate, eventList);
        this.details = new Details();

        timelinePlaceholder.getChildren().add(this.timelineView.getRoot()); // Left
        calendarScreenPlaceholder.getChildren().add(this.calendarScreen.getRoot()); // Top Right
        detailsPlaceholder.getChildren().add(this.details.getRoot()); // Bottom Right

    }

    /**
     * Changes the CalendarScreen date only with a given CalendarDate.
     *
     * @param calendarDate The given date.
     */
    public void changeCalendarScreenDate(CalendarDate calendarDate) {
        calendarScreenPlaceholder.getChildren().clear();
        calendarScreen = new CalendarScreen(calendarDate);
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
     * Changes to TimelineDayView with a given CalendarDate.
     *
     * @param calendarDate The given date.
     */
    public void changeToDayView(CalendarDate calendarDate) {
        changeCalendarScreenDate(calendarDate);
        timelinePlaceholder.getChildren().clear();
        timelineView = new TimelineDayView(calendarDate, eventList);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    /**
     * Changes to TimelineWeekView with a given CalendarDate.
     *
     * @param calendarDate The given date.
     */
    public void changeToWeekView(CalendarDate calendarDate) {
        changeCalendarScreenDate(calendarDate);
        timelinePlaceholder.getChildren().clear();
        this.timelineView = new TimelineWeekView(calendarDate, eventList);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    /**
     * Changes to TimelineDayView with a given CalendarDate.
     *
     * @param calendarDate The given date.
     */
    public void changeToMonthView(CalendarDate calendarDate) {
        changeCalendarScreenDate(calendarDate);
        timelinePlaceholder.getChildren().clear();
        this.timelineView = new TimelineMonthView(calendarDate, eventList);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    /**
     * Changes the sub-components of the CalendarPanel with the updated events.
     * @param events The given list of events.
     */
    public void onEventListChange(List<EventSource> events) {
        this.timelineView.eventChange(events);
        this.calendarScreen.eventChange(events);
        this.eventList = events;
    }
}
