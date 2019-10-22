package seedu.address.ui.panel.calendar;

import java.time.YearMonth;
import java.util.List;

import javafx.scene.layout.RowConstraints;

import seedu.address.model.events.EventSource;
import seedu.address.ui.EventCard;
import seedu.address.ui.EventCardHolder;
import seedu.address.ui.UiParser;

/**
 * An UI component that displays the feedback to the user.
 */
public class TimelineMonthView extends TimelineView {

    private static final String FXML = "TimelineMonthView.fxml";

    private Integer month;
    private Integer year;

    /**
     * Constructor for TimelineMonthView for a particular month.
     *
     * @param month Represents the month of the timeline.
     * @param year Represents the year of the timeline.
     * @param eventList Represents the current list of events.
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     */
    public TimelineMonthView(Integer month,
                             Integer year,
                             List<EventSource> eventList,
                             UiParser uiParser) {
        super(uiParser, FXML);
        this.month = month;
        this.year = year;
        setTotalRows(YearMonth.of(year, month).lengthOfMonth());

        setTimelineTitle("Timeline: " + uiParser.getEnglishDate(month, year));
        addGrids();
        addLabels(getLabels());
        addEventCardHolders();
        eventChange(eventList);
    }

    /**
     * Returns an array of String containing values of the Month.
     *
     * @return Returns an array of String containing values of the Month.
     */
    private String[] getLabels() {
        String[] labels = new String[getTotalRows()];
        for (Integer day = 0; day < getTotalRows(); day++) {
            labels[day] = String.valueOf(day + 1);
        }
        return labels;
    }

    @Override
    void addEventCard(EventSource event) {
        // Gets the event Hour
        Integer eventDay = getUiParser().getDay(event.getStartDateTime().toInstant());

        EventCard eventCard = new EventCard(event, getUiParser());
        EventCardHolder eventCardHolder = getEventCardHolder().get(eventDay - 1);
        eventCardHolder.addEvent(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(eventDay - 1);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    @Override
    boolean isWithinTimeline(EventSource event) {
        Integer[] dayMonthYear = getUiParser().getDateToNumbers(event.getStartDateTime().toInstant());
        return dayMonthYear[1].equals(month) && dayMonthYear[2].equals(year);
    }
}
