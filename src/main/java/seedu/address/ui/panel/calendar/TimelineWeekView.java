package seedu.address.ui.panel.calendar;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import javafx.scene.layout.RowConstraints;

import seedu.address.model.CalendarDate;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UiParser;
import seedu.address.ui.card.CardHolder;
import seedu.address.ui.card.EventCard;
/**
 * An UI component that displays the feedback to the user.
 */
public class TimelineWeekView extends TimelineView {

    private static final String FXML = "TimelineWeekView.fxml";

    private CalendarDate[] daysInWeek;
    private CalendarDate calendarDate;
    private Integer month;
    private Integer year;

    /**
     * Constructor for TimelineWeekView for a particular week.
     *
     * @param calendarDate Represents the calendarDate given by the user.
     * @param eventList Represents the current list of events.
     */
    public TimelineWeekView(CalendarDate calendarDate,
                            List<EventSource> eventList) {
        super(FXML);
        this.month = calendarDate.getMonth();
        this.year = calendarDate.getYear();
        this.calendarDate = calendarDate;
        setTotalRows(7);
        setTimelineTitle("Timeline: " + getEnglishWeekDate(getWeek(calendarDate), month, year));
        this.daysInWeek = addWeek();

        addGrids();
        addLabels(getLabels());
        addEventCardHolders();
        eventChange(eventList);
    }

    /**
     * Returns an array of String containing values of the week.
     *
     * @return Returns an array of String containing values of the week.
     */
    private String[] getLabels() {
        return new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }

    private CalendarDate[] addWeek() {
        CalendarDate[] calendarDates = new CalendarDate[7];
        int weekIndex = calendarDate.getWeekIndex();
        while(weekIndex > 1) {
            calendarDate =  calendarDate.previousDay();
            weekIndex --;
        }
        for(int i = 0; i < calendarDates.length; i++) {
            calendarDates[i] = calendarDate;
            calendarDate = calendarDate.nextDay();
        }
        return calendarDates;
    }

    private Integer getWeek(CalendarDate calendarDate) {
        Calendar ca1 = Calendar.getInstance();
        ca1.set(calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDay());
        ca1.setMinimalDaysInFirstWeek(1);
        return ca1.get(Calendar.WEEK_OF_MONTH);
    }

    private String getEnglishWeekDate(Integer week, Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return "Week " + week + " of " + monthStr + " " + year;
    }

    @Override
    void addEventCard(EventSource event) {
        // Creates and add the event card
        int eventIndex = event.getStartDateTime().getWeek();

        EventCard eventCard = new EventCard(event);
        CardHolder eventCardHolder = getEventCardHolder().get(eventIndex - 1);
        eventCardHolder.addEvent(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(eventIndex - 1);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    @Override
    boolean isWithinTimeline(EventSource event) {
        DateTime eventDate = event.getStartDateTime();
        for (CalendarDate calendarDate : daysInWeek) {
            // Same day
            if(calendarDate.sameDate(
                    eventDate.getDay(),
                    eventDate.getHour(),
                    eventDate.getYear())) {
                return true;
            }
        }
        return false;
    }

}
