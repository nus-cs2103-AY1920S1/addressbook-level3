package seedu.address.ui.panel.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiPart;

//@@author Kyzure
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

    private List<Object> eventTaskList;
    private HashMap<EventSource, Integer> eventHash;
    private HashMap<TaskSource, Integer> taskHash;

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
        this.eventTaskList = new ArrayList<>();

        this.calendarScreen = new CalendarScreen(this.calendarDate);
        this.timelineView = new TimelineDayView(this.calendarDate, eventTaskList, eventHash, taskHash);
        this.upcomingView = new UpcomingView(eventTaskList);

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
    }

    /**
     * Changes to TimelineDayView with a given CalendarDate.
     *
     * @param calendarDate The given date.
     */
    public void changeToDayView(CalendarDate calendarDate) {
        changeCalendarScreenDate(calendarDate);
        timelinePlaceholder.getChildren().clear();
        timelineView = new TimelineDayView(calendarDate, eventTaskList, eventHash, taskHash);
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
        this.timelineView = new TimelineWeekView(calendarDate, eventTaskList, eventHash, taskHash);
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
        this.timelineView = new TimelineMonthView(calendarDate, eventTaskList, eventHash, taskHash);
        timelinePlaceholder.getChildren().add(timelineView.getRoot());
    }

    /**
     * Changes the UI according to the given Lists.
     *
     * @param events The given event lists.
     * @param tasks The given task list.
     */
    public void onModelListChange(List<EventSource> events,
                                  List<TaskSource> tasks,
                                  HashMap<EventSource, Integer> eventHash,
                                  HashMap<TaskSource, Integer> taskHash) {
        eventTaskList = combineList(events, tasks);
        this.eventHash = eventHash;
        this.taskHash = taskHash;
        this.timelineView.onChange(eventTaskList, eventHash, taskHash);
        this.calendarScreen.onChange(eventTaskList);
        this.upcomingView.onChange(eventTaskList);
    }

    /**
     * Returns a combined copy of list for event list and task list into an Object list to be used.
     *
     * @param events Represents the event list.
     * @param tasks Represents the task list.
     * @return A combined copy of list for event list and task list into an Object list to be used.
     */
    private List<Object> combineList(List<EventSource> events, List<TaskSource> tasks) {
        List<Object> eventTaskList = new ArrayList<>();
        Queue<EventSource> tempEvents = new LinkedList<>();
        tempEvents.addAll(events);
        Queue<TaskSource> tempTasks = new LinkedList<>();
        tempTasks.addAll(tasks);

        // Events and Tasks are already sorted, so we need to zip them.
        while (!tempEvents.isEmpty() || !tempTasks.isEmpty()) {
            if (tempEvents.isEmpty()) {
                eventTaskList.addAll(tempTasks);
                break;
            }

            if (tempTasks.isEmpty()) {
                eventTaskList.addAll(tempEvents);
                break;
            }

            EventSource event = tempEvents.peek();
            TaskSource task = tempTasks.peek();
            if (task.isDone() || task.getDueDate() == null) {
                tempTasks.poll();
                continue;
            }

            int dateCompare = event.getStartDateTime().compareTo(task.getDueDate());
            if (dateCompare == 0) {
                if (event.getDescription().compareTo(task.getDescription()) <= 0) {
                    eventTaskList.add(tempEvents.poll());
                } else {
                    eventTaskList.add(tempTasks.poll());
                }
            } else if (dateCompare < 0) {
                eventTaskList.add(tempEvents.poll());
            } else {
                eventTaskList.add(tempTasks.poll());
            }
        }

        return eventTaskList;
    }
}
