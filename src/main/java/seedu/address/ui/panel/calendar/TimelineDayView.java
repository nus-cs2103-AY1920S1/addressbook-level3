package seedu.address.ui.panel.calendar;

import java.util.List;

import javafx.scene.layout.RowConstraints;

import seedu.address.model.CalendarDate;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.ui.card.CardHolder;
import seedu.address.ui.card.EventCard;

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
     * @param eventList Represents the current list of events.
     */
    public TimelineDayView(CalendarDate calendarDate, List<EventSource> eventList) {
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
        eventChange(eventList);
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
    void addEventCard(EventSource event) {
        Integer eventHour = event.getStartDateTime().getHour();

        EventCard eventCard = new EventCard(event);
        CardHolder eventCardHolder = getEventCardHolder().get(eventHour);
        eventCardHolder.addEvent(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(eventHour);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    @Override
    boolean isWithinTimeline(EventSource event) {
        DateTime eventDateTime = event.getStartDateTime();
        return calendarDate.sameDate(eventDateTime.getDay(), eventDateTime.getMonth(), eventDateTime.getYear());
    }
}
