package seedu.address.ui.panel.calendar;

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
public class TimelineDayView extends TimelineView {

    private static final String FXML = "TimelineDayView.fxml";

    private CalendarDate calendarDate;

    /**
     * Constructor for TimelineDayView for a particular day.
     *
     * @param calendarDate Represents the given or current date.
     * @param eventTaskList Represents the current list of events and task.
     */
    public TimelineDayView(CalendarDate calendarDate,
                           List<Object> eventTaskList,
                           HashMap<EventSource, Integer> eventHash,
                           HashMap<TaskSource, Integer> taskHash) {
        super(FXML);
        this.calendarDate = calendarDate;

        setTotalRows(24);
        setTimelineTitle("Timeline: "
                + calendarDate.getDay() + " "
                + calendarDate.getEnglishMonth() + " "
                + calendarDate.getYear());
        addGrids();
        addLabels(getLabels());
        addEventCardHolders();
        onChange(eventTaskList, eventHash, taskHash);
    }

    /**
     * Returns an array of String containing the times of a Day.
     *
     * @return Returns an array of String containing the times of a Day.
     */
    private String[] getLabels() {
        String[] labels = new String[24];
        for (int time = 0; time < 10; time++) {
            labels[time] = "0" + time + ":00";
        }
        for (int time = 10; time < 24; time++) {
            labels[time] = time + ":00";
        }
        return labels;
    }

    @Override
    void addEventCard(EventSource event, Integer eventIndex) {
        Integer eventHour = event.getStartDateTime().getHour();

        EventCard eventCard = new EventCard(event, eventIndex);
        CardHolder eventCardHolder = getCardHolder().get(eventHour);
        eventCardHolder.addCard(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(eventHour);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    @Override
    void addTaskCard(TaskSource task, Integer taskIndex) {
        Integer taskHour = task.getDueDate().getHour();

        TaskCard taskCard = new TaskCard(task, taskIndex);
        CardHolder cardHolder = getCardHolder().get(taskHour);
        cardHolder.addCard(taskCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(taskHour);
        updateSizeDelay(rowConstraints, cardHolder);
    }

    @Override
    boolean isWithinTimeline(EventSource event) {
        DateTime eventDateTime = event.getStartDateTime();
        return calendarDate.sameDate(eventDateTime.getDay(), eventDateTime.getMonth(), eventDateTime.getYear());
    }

    @Override
    boolean isWithinTimeline(TaskSource task) {
        DateTime taskDueDate = task.getDueDate();
        return calendarDate.sameDate(taskDueDate.getDay(), taskDueDate.getMonth(), taskDueDate.getYear());
    }

}
