package seedu.address.ui.panel.calendar;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.CardHolder;
import seedu.address.ui.card.EventCard;

/**
 * Represents an abstract class for the different timelines placed in the CalendarPanel.
 * Currently there are 3 types of timeline - day, week and month.
 *
 * @see TimelineDayView
 * @see TimelineWeekView
 * @see TimelineMonthView
 */
public abstract class TimelineView extends UiPart<Region> {

    private static final Integer SPACING = 62;
    private static final Integer TIMING = 10;

    private ArrayList<CardHolder> eventCardHolders;
    private Integer totalRows;

    @FXML
    private Label timelineTitle;

    @FXML
    private GridPane timelineGrid;

    TimelineView(String fxml) {
        super(fxml);
    }

    /**
     * Initializes the timeline by adding cardholders to hold the EventCards.
     *
     * @see CardHolder ;
     */
    void addEventCardHolders() {
        this.eventCardHolders = new ArrayList<>();
        for (int row = 0; row < totalRows; row++) {
            CardHolder eventCardHolder = new CardHolder();
            eventCardHolders.add(eventCardHolder);
            timelineGrid.add(eventCardHolder.getRoot(), 1, row);
        }
    }

    /**
     * Re-sizes the timeline view panel by setting all the RowConstraints sizes to be the same
     * height as the it's EventCardHolders, with an additional spacing.
     *
     * @see RowConstraints
     */
    void resizeTimelineView() {
        for (int row = 0; row < totalRows; row++) {
            CardHolder eventCardHolder = eventCardHolders.get(row);
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(row);
            rowConstraints.setPrefHeight(eventCardHolder.getHeight() + SPACING);
        }
    }

    /**
     * Updates the current row's constraints height after a certain delay so to obtain the
     * updated height of the EventCardHolder.
     *
     * @param rowConstraints The current row constraints.
     * @param eventCardHolder The current EventCardHolder to obtain the height.
     */
    void updateSizeDelay(RowConstraints rowConstraints, CardHolder eventCardHolder) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                    throw new Exception(e.getMessage());
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
     * Adds an array of labels into the Grid Pane.
     *
     * @param labels The labels to be added into the Grid Pane.
     */
    void addLabels(String ...labels) {
        int row = 0;
        for (String label : labels) {
            TimelineLabel timelineLabel = new TimelineLabel(label);
            timelineGrid.add(timelineLabel.getRoot(), 0, row);
            row++;
        }
    }

    /**
     * Adds a set of RowConstraints into the Grid Pane.
     *
     * @see RowConstraints
     */
    void addGrids() {
        for (int row = 0; row < totalRows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.TOP);
            rowConstraints.setPrefHeight(100);
            rowConstraints.setMinHeight(100);
            timelineGrid.getRowConstraints().add(rowConstraints);
        }
    }

    /**
     * Changes the timeline by resetting it with the new list of events.
     *
     * @param eventList The given event list containing all the events.
     */
    void eventChange(List<EventSource> eventList) {
        resetTimeline();
        for (EventSource event : eventList) {
            if (isWithinTimeline(event)) {
                addEventCard(event);
            }
        }
    }

    /**
     * Resets the timeline by removing all of the EventCardHolders' EventCard.
     */
    private void resetTimeline() {
        for (int row = 0; row < totalRows; row++) {
            // Reset RowConstraints
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(row);
            rowConstraints.setPrefHeight(0);

            // Reset EventCards for each hour
            eventCardHolders.get(row).removeEventCards();
        }
    }

    /**
     * Creates an EventCard and adds it to the current timeline.
     *
     * @param event The given event.
     * @see EventCard
     */
    abstract void addEventCard(EventSource event);

    /**
     * Returns a boolean that checks if the given date of the event is within the particular timeline.
     *
     * @param event The given event.
     * @return a boolean that checks if the given date of the event is within the particular timeline.
     */
    abstract boolean isWithinTimeline(EventSource event);

    // Getters
    ArrayList<CardHolder> getEventCardHolder() {
        return this.eventCardHolders;
    }

    GridPane getTimelineGrid() {
        return this.timelineGrid;
    }

    Integer getTotalRows() {
        return this.totalRows;
    }

    // Setters
    void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    void setTimelineTitle(String timelineTitle) {
        this.timelineTitle.setText(timelineTitle);
    }

}
