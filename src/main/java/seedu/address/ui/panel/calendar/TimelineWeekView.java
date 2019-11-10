package seedu.address.ui.panel.calendar;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.List;

import javafx.scene.layout.RowConstraints;

import seedu.address.model.CalendarDate;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.card.CardHolder;
import seedu.address.ui.card.EventCard;
import seedu.address.ui.card.TaskCard;

//@@author Kyzure
/**
 * An UI component that displays the feedback to the user.
 */
public class TimelineWeekView extends TimelineView {

    private static final String FXML = "TimelineWeekView.fxml";

    private CalendarDate[] daysInWeek;
    private CalendarDate calendarDate;

    /**
     * Constructor for TimelineWeekView for a particular week.
     *
     * @param calendarDate Represents the calendarDate given by the user.
     * @param eventTaskList Represents the current list of events and tasks.
     */
    public TimelineWeekView(CalendarDate calendarDate,
                            List<Object> eventTaskList,
                            HashMap<EventSource, Integer> eventHash,
                            HashMap<TaskSource, Integer> taskHash) {
        super(FXML);
        this.calendarDate = calendarDate;
        setTotalRows(7);
        setTimelineTitle("Timeline: "
                + getEnglishWeekDate(getWeek(calendarDate), calendarDate.getMonth(), calendarDate.getYear()));
        this.daysInWeek = addWeek();

        addGrids();
        addLabels(getLabels());
        addEventCardHolders();
        onChange(eventTaskList, eventHash, taskHash);
    }

    /**
     * Returns an array of String containing values of the week.
     *
     * @return Returns an array of String containing values of the week.
     */
    private String[] getLabels() {
        return new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }

    /**
     * Returns an array of CalendarDate representing the list of days in the same week as the given day.
     *
     * @return An array of CalendarDate representing the list of days in the same week as the given day.
     */
    private CalendarDate[] addWeek() {
        CalendarDate[] calendarDates = new CalendarDate[7];
        int weekIndex = calendarDate.getWeekIndex();
        while (weekIndex > 1) {
            calendarDate = calendarDate.previousDay();
            weekIndex--;
        }
        for (int i = 0; i < calendarDates.length; i++) {
            calendarDates[i] = calendarDate;
            calendarDate = calendarDate.nextDay();
        }
        return calendarDates;
    }

    private Integer getWeek(CalendarDate calendarDate) {
        CalendarDate currentDate = calendarDate.firstDayOfTheMonth();
        Integer weekIndex = currentDate.getWeekIndex();
        currentDate = currentDate.previousDays(weekIndex - 1);
        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                if (calendarDate.equals(currentDate)) {
                    return week + 1;
                }
                currentDate = currentDate.nextDay();
            }
        }
        // Not suppose to reach here.
        return null;
    }

    private String getEnglishWeekDate(Integer week, Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return "Week " + week + " of " + monthStr + " " + year;
    }

    @Override
    void addEventCard(EventSource event, Integer eventIndex) {
        // Creates and add the event card
        int eventWeekIndex = event.getStartDateTime().getWeek();

        EventCard eventCard = new EventCard(event, eventIndex);
        CardHolder eventCardHolder = getCardHolder().get(eventWeekIndex - 1);
        eventCardHolder.addCard(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(eventWeekIndex - 1);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    @Override
    void addTaskCard(TaskSource task, Integer taskIndex) {
        // Creates and add the event card
        int taskWeekIndex = task.getDueDate().getWeek();

        TaskCard taskCard = new TaskCard(task, taskIndex);
        CardHolder eventCardHolder = getCardHolder().get(taskWeekIndex - 1);
        eventCardHolder.addCard(taskCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(taskWeekIndex - 1);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    @Override
    boolean isWithinTimeline(EventSource event) {
        DateTime eventDate = event.getStartDateTime();
        for (CalendarDate calendarDate : daysInWeek) {
            // Same day
            if (calendarDate.sameDate(
                    eventDate.getDay(),
                    eventDate.getMonth(),
                    eventDate.getYear())) {
                return true;
            }
        }
        return false;
    }


    @Override
    boolean isWithinTimeline(TaskSource task) {
        DateTime taskDueDate = task.getDueDate();
        for (CalendarDate calendarDate : daysInWeek) {
            // Same day
            if (calendarDate.sameDate(
                    taskDueDate.getDay(),
                    taskDueDate.getMonth(),
                    taskDueDate.getYear())) {
                return true;
            }
        }
        return false;
    }

}
