package seedu.address.ui.panel.calendar;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import jdk.jfr.Event;
import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiPart;

/**
 * An Ui that represents a Panel that contains all the components to make a calendar view. This includes
 * a Timeline, Calendar, and the Details of an event.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private CalendarScreen calendarScreen;
    private TimelineView timelineView;
    private UpcomingView upcomingView;
    private CalendarDate calendarDate;

    private List<EventSource> eventList;
    private List<TaskSource> taskList;
    private List<Object> eventTaskList;

    @FXML
    private StackPane timelinePlaceholder;

    @FXML
    private VBox calendarScreenPlaceholder;

    @FXML
    private StackPane upcomingViewPlaceholder;

    /**
     * Constructor for CalendarPanel. Contains the 3 main viewing points for the calendar panel.
     * The points are calendar screen view, timeline view and details view.
     *
     * @see TimelineView
     * @see CalendarScreen
     * @see UpcomingView
     */
    public CalendarPanel() {
        super(FXML);
        this.calendarDate = CalendarDate.now();
        this.eventList = new ArrayList<>();
        this.taskList = new ArrayList<>();
        this.eventTaskList = new ArrayList<>();

        this.calendarScreen = new CalendarScreen(this.calendarDate);
        this.timelineView = new TimelineDayView(this.calendarDate, eventTaskList);
        this.upcomingView = new UpcomingView();

        timelinePlaceholder.getChildren().add(this.timelineView.getRoot()); // Left
        calendarScreenPlaceholder.getChildren().add(this.calendarScreen.getRoot()); // Top Right
        upcomingViewPlaceholder.getChildren().add(this.upcomingView.getRoot()); // Bottom Right

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
        calendarScreen.onChange(eventTaskList);
    }

    /**
     * Re-sizes the TimelineView.
     */
    public void resizeCalendarPanel() {
        timelineView.resizeTimelineView();
        upcomingView.resizeUpcomingView(eventTaskList);
    }

    /**
     * Changes to TimelineDayView with a given CalendarDate.
     *
     * @param calendarDate The given date.
     */
    public void changeToDayView(CalendarDate calendarDate) {
        changeCalendarScreenDate(calendarDate);
        timelinePlaceholder.getChildren().clear();
        timelineView = new TimelineDayView(calendarDate, eventTaskList);
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
        this.timelineView = new TimelineWeekView(calendarDate, eventTaskList);
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
        this.timelineView = new TimelineMonthView(calendarDate, eventTaskList);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    /**
     * Changes the sub-components of the CalendarPanel with the updated events.
     * @param events The given list of events.
     */
    public void onEventListChange(List<EventSource> events) {
        this.eventList = events;
        eventTaskList  = combineList(eventList, taskList);
        onChange();
    }

    public void onTaskListChange(List<TaskSource> tasks) {
        this.taskList = tasks;
        eventTaskList  = combineList(eventList, taskList);
        onChange();
    }

    private void onChange() {
        this.timelineView.onChange(eventTaskList);
        this.calendarScreen.onChange(eventTaskList);
        this.upcomingView.onChange(eventTaskList);
    }

    private List<Object> combineList(List<EventSource> events, List<TaskSource> tasks) {
        List<Object> eventTaskList = new ArrayList<>();
        Queue<EventSource> tempEvents = new LinkedList<>();
        tempEvents.addAll(events);
        Queue<TaskSource> tempTasks = new LinkedList<>();
        tempTasks.addAll(tasks);

        // Events and Tasks are already sorted, so we need to zip them.
        while(!tempEvents.isEmpty() || !tempTasks.isEmpty()) {
            if(tempEvents.isEmpty()) {
                eventTaskList.addAll(tempTasks);
                break;
            }

            if(tempTasks.isEmpty()) {
                eventTaskList.addAll(tempEvents);
                break;
            }

            EventSource event = tempEvents.peek();
            TaskSource task = tempTasks.peek();
            if(task.isCompleted()) {
                tempTasks.poll();
                continue;
            }
            int dateCompare = event.getStartDateTime().compareTo(task.getDueDate());
            if(dateCompare == 0) {
                if(event.getDescription().compareTo(task.getDescription()) <= 0) {
                    eventTaskList.add(tempEvents.poll());
                } else {
                    eventTaskList.add(tempTasks.poll());
                }
            } else if(dateCompare < 0) {
                System.out.println(dateCompare);
                eventTaskList.add(tempEvents.poll());
            } else {
                eventTaskList.add(tempTasks.poll());
            }
        }

        return eventTaskList;
    }
}
