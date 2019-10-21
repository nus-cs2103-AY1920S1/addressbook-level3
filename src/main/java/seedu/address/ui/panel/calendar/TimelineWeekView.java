package seedu.address.ui.panel.calendar;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import seedu.address.model.events.EventSource;
import seedu.address.ui.EventCard;
import seedu.address.ui.EventCardHolder;
import seedu.address.ui.UiParser;
/**
 * An UI component that displays the feedback to the user.
 */
public class TimelineWeekView extends TimelineView {

    private static final String FXML = "TimelineWeekView.fxml";

    private UiParser uiParser;
    private Integer[][] days;
    private Integer week;
    private Integer month;
    private Integer year;

    private ArrayList<EventCardHolder> eventCardHolders;

    @FXML
    private Label timelineTitle;

    @FXML
    private GridPane timelineGrid;

    /**
     * Constructor for TimelineWeekView for a particular week.
     *
     * @param week Represents the week of the timeline.
     * @param month Represents the month of the timeline.
     * @param year Represents the year of the timeline.
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     */
    public TimelineWeekView(Integer week, Integer month, Integer year, Integer[][] days, UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.week = week;
        this.month = month;
        this.year = year;
        this.days = days;
        this.eventCardHolders = new ArrayList<>();

        this.timelineTitle.setText("Timeline: " + uiParser.getEnglishWeekDate(week, month, year));
        addEventCardHolders();
    }

    private boolean sameWeek(EventSource event) {
        Integer eventDay = uiParser.getDay(event.getStartDateTime().toInstant());
        for(Integer[] dayMonthYear : days) {
            // Same day
            if(dayMonthYear[0].equals(eventDay)) {
                // Checks if the event is within the same week as the list of days.
                if ((dayMonthYear[1].equals(this.month) && dayMonthYear[2].equals(this.year))) {
                    // In the same week, month, and year
                    return true;
                }
                if (dayMonthYear[1].equals(uiParser.getPreviousMonth(this.month)) &&
                        dayMonthYear[2].equals((uiParser.getPreviousYear (this.month, this.year)))) {
                    // In the same week, previous month and may be previous year
                    return true;
                }
                if (dayMonthYear[1].equals(uiParser.getNextMonth(this.month)) &&
                        dayMonthYear[2].equals((uiParser.getNextYear(this.month, this.year)))) {
                    // In the same week, next month and may be next year
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds an event to the timeline.
     * @param event Represents the given event.
     */
    private void addEventCard(EventSource event) {
        // Creates and add the event card
        int eventIndex = uiParser.getEventIndex(event.getStartDateTime().toInstant());

        EventCard eventCard = new EventCard(event, uiParser);
        EventCardHolder eventCardHolder = eventCardHolders.get(eventIndex - 1);
        eventCardHolder.addEvent(eventCard);

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(eventIndex);

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                rowConstraints.setPrefHeight(eventCardHolder.getHeight() + SPACING);
            }
        });
        new Thread(sleeper).start();
    }


    /**
     * Resets the timeline by removing all of the eventCards and reset the size.
     */
    private void resetTimeline() {
        for (int eventWeek = 0; eventWeek < eventCardHolders.size(); eventWeek++) {
            // Reset RowConstraints
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(eventWeek);
            rowConstraints.setPrefHeight(0);

            // Reset EventCards for each hour
            eventCardHolders.get(eventWeek).removeEventCards();
        }
    }

    private void addEventCardHolders() {
        for(int i = 0; i < 7; i++) {
            EventCardHolder eventCardHolder = new EventCardHolder();
            eventCardHolders.add(eventCardHolder);
            timelineGrid.add(eventCardHolder.getRoot(), 1, i);
        }
    }

    @Override
    public void resizeTimelineView() {
        for(int weekDay = 0; weekDay < eventCardHolders.size(); weekDay++) {
            EventCardHolder eventCardHolder = eventCardHolders.get(weekDay);
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(weekDay);
            rowConstraints.setPrefHeight(eventCardHolder.getHeight() + SPACING);
        }
    }

    @Override
    public void eventChange(List<EventSource> eventList) {
        resetTimeline();
        for (EventSource event : eventList) {
            if (sameWeek(event)) {
                addEventCard(event);
            }
        }
    }
}
