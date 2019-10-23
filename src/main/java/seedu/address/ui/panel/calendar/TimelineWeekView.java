package seedu.address.ui.panel.calendar;

import java.util.List;

import javafx.scene.layout.RowConstraints;

import seedu.address.model.events.EventSource;
import seedu.address.ui.card.EventCard;
import seedu.address.ui.card.CardHolder;
import seedu.address.ui.UiParser;
/**
 * An UI component that displays the feedback to the user.
 */
public class TimelineWeekView extends TimelineView {

    private static final String FXML = "TimelineWeekView.fxml";

    private Integer[][] days;
    private Integer week;
    private Integer month;
    private Integer year;

    /**
     * Constructor for TimelineWeekView for a particular week.
     *
     * @param week Represents the week of the timeline.
     * @param month Represents the month of the timeline.
     * @param year Represents the year of the timeline.
     * @param days Represents a list of dates, where each date contains a day, month and year.
     * @param eventList Represents the current list of events.
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     */
    public TimelineWeekView(Integer week,
                            Integer month,
                            Integer year,
                            Integer[][] days,
                            List<EventSource> eventList,
                            UiParser uiParser) {
        super(uiParser, FXML);
        this.week = week;
        this.month = month;
        this.year = year;
        this.days = days;
        setTotalRows(7);
        setTimelineTitle("Timeline: " + uiParser.getEnglishWeekDate(week, month, year));

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
        String[] labels = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return labels;
    }

    @Override
    void addEventCard(EventSource event) {
        // Creates and add the event card
        int eventIndex = getUiParser().getWeekIndex(event.getStartDateTime().toInstant());

        EventCard eventCard = new EventCard(event, getUiParser());
        CardHolder eventCardHolder = getEventCardHolder().get(eventIndex - 1);
        eventCardHolder.addEvent(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = getTimelineGrid().getRowConstraints().get(eventIndex - 1);
        updateSizeDelay(rowConstraints, eventCardHolder);
    }

    @Override
    boolean isWithinTimeline(EventSource event) {
        Integer eventDay = getUiParser().getDay(event.getStartDateTime().toInstant());
        for (Integer[] dayMonthYear : days) {
            // Same day
            if (dayMonthYear[0].equals(eventDay)) {
                // Checks if the event is within the same week as the list of days.
                if ((dayMonthYear[1].equals(this.month) && dayMonthYear[2].equals(this.year))) {
                    // In the same week, month, and year
                    return true;
                }
                if (dayMonthYear[1].equals(getUiParser().getPreviousMonth(this.month))
                        && dayMonthYear[2].equals((getUiParser().getPreviousYear (this.month, this.year)))) {
                    // In the same week, previous month and may be previous year
                    return true;
                }
                if (dayMonthYear[1].equals(getUiParser().getNextMonth(this.month))
                        && dayMonthYear[2].equals((getUiParser().getNextYear(this.month, this.year)))) {
                    // In the same week, next month and may be next year
                    return true;
                }
            }
        }
        return false;
    }

}
