package seedu.address.ui.panel.calendar;

import java.time.YearMonth;
import java.util.List;

import javafx.geometry.VPos;
import javafx.scene.layout.Priority;
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
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     */
    public TimelineMonthView(Integer month,
                             Integer year,
                             List<EventSource> eventList,
                             UiParser uiParser) {
        super(uiParser, FXML);
        this.month = month;
        this.year = year;
        this.totalRows = YearMonth.of(year, month).lengthOfMonth();

        this.timelineTitle.setText("Timeline: " + uiParser.getEnglishDate(month, year));
        addGrids();
        addLabels(getLabels());
        addEventCardHolders();
        eventChange(eventList);
    }

    /**
     * Returns true if the event is the same day as the current timeline.
     * @param event The given event.
     * @return True if the event is the same day as the current timeline.
     */
    private boolean sameDate(EventSource event) {
        Integer[] dayMonthYear = uiParser.getDateToNumbers(event.getStartDateTime().toInstant());
        return dayMonthYear[1].equals(month) && dayMonthYear[2].equals(year);
    }

    /**
     * Adds an event to the timeline.
     * @param event Represents the given event.
     */
    private void addEventCard(EventSource event) {
        // Gets the event Hour
        Integer eventDay = uiParser.getDay(event.getStartDateTime().toInstant());

        EventCard eventCard = new EventCard(event, uiParser);
        EventCardHolder eventCardHolder = eventCardHolders.get(eventDay - 1);
        eventCardHolder.addEvent(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(eventDay - 1);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    private String[] getLabels() {
        String[] labels = new String[totalRows];
        for(Integer day = 0; day < totalRows; day++) {
            labels[day] = String.valueOf(day + 1);
        }
        return labels;
    }

    @Override
    public void eventChange(List<EventSource> eventList) {
        resetTimeline();
        for (EventSource event : eventList) {
            if (sameDate(event)) {
                addEventCard(event);
            }
        }
    }
}
