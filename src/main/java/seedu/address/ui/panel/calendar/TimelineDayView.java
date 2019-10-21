package seedu.address.ui.panel.calendar;

import java.util.ArrayList;
import java.util.HashMap;
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
import seedu.address.ui.UiParser;

/**
 * An UI component that displays the feedback to the user.
 */
public class TimelineDayView extends TimelineView {

    private static final String FXML = "TimelineDayView.fxml";

    private UiParser uiParser;
    private Integer day;
    private Integer month;
    private Integer year;

    private ArrayList<VBox> timeBoxes;

    @FXML
    private Label timelineTitle;

    @FXML
    private GridPane timelineGrid;

    @FXML
    private VBox timeBox0;
    @FXML
    private VBox timeBox1;
    @FXML
    private VBox timeBox2;
    @FXML
    private VBox timeBox3;
    @FXML
    private VBox timeBox4;
    @FXML
    private VBox timeBox5;
    @FXML
    private VBox timeBox6;
    @FXML
    private VBox timeBox7;
    @FXML
    private VBox timeBox8;
    @FXML
    private VBox timeBox9;
    @FXML
    private VBox timeBox10;
    @FXML
    private VBox timeBox11;
    @FXML
    private VBox timeBox12;
    @FXML
    private VBox timeBox13;
    @FXML
    private VBox timeBox14;
    @FXML
    private VBox timeBox15;
    @FXML
    private VBox timeBox16;
    @FXML
    private VBox timeBox17;
    @FXML
    private VBox timeBox18;
    @FXML
    private VBox timeBox19;
    @FXML
    private VBox timeBox20;
    @FXML
    private VBox timeBox21;
    @FXML
    private VBox timeBox22;
    @FXML
    private VBox timeBox23;

    /**
     * Constructor for TimelineDayView for a particular day.
     *
     * @param day Represents the day of the timeline.
     * @param month Represents the month of the timeline.
     * @param year Represents the year of the timeline.
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     */
    public TimelineDayView(Integer day, Integer month, Integer year, UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.day = day;
        this.month = month;
        this.year = year;
        this.timeBoxes = new ArrayList<>();

        this.timelineTitle.setText("Timeline: " + uiParser.getEnglishDate(day, month, year));
        addTimeBoxes();
    }

    /**
     * Returns true if the event is the same day as the current timeline.
     * @param event The given event.
     * @return True if the event is the same day as the current timeline.
     */
    private boolean sameDate(EventSource event) {
        Integer[] dayMonthYear = uiParser.getDateToNumbers(event.getStartDateTime().toInstant());
        return dayMonthYear[0].equals(day) && dayMonthYear[1].equals(month) && dayMonthYear[2].equals(year);
    }

    /**
     * Adds an event to the timeline.
     * @param event Represents the given event.
     */
    private void addEventCard(EventSource event) {
        // Gets the event Hour
        Integer eventHour = uiParser.getHour(event.getStartDateTime().toInstant());

        // Creates and add the event card
        EventCard eventCard = new EventCard(event, uiParser);
        VBox timeBox = timeBoxes.get(eventHour);
        timeBox.getChildren().add(eventCard.getRoot());

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(eventHour);

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
                rowConstraints.setPrefHeight(timeBox.getHeight() + SPACING);
            }
        });
        new Thread(sleeper).start();
    }

    /**
     * Resets the timeline by removing all of the eventCards and reset the size.
     */
    private void resetTimeline() {
        for (int eventHour = 0; eventHour < 24; eventHour++) {
            // Reset RowConstraints
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(eventHour);
            rowConstraints.setPrefHeight(0);

            // Reset EventCards for each hour
            timeBoxes.get(eventHour).getChildren().clear();
        }
    }

    /**
     * Adds all the timeboxes to the list for easier time reading and writing
     */
    private void addTimeBoxes() {
        this.timeBoxes.add(timeBox0);
        this.timeBoxes.add(timeBox1);
        this.timeBoxes.add(timeBox2);
        this.timeBoxes.add(timeBox3);
        this.timeBoxes.add(timeBox4);
        this.timeBoxes.add(timeBox5);
        this.timeBoxes.add(timeBox6);
        this.timeBoxes.add(timeBox7);
        this.timeBoxes.add(timeBox8);
        this.timeBoxes.add(timeBox9);
        this.timeBoxes.add(timeBox10);
        this.timeBoxes.add(timeBox11);
        this.timeBoxes.add(timeBox12);
        this.timeBoxes.add(timeBox13);
        this.timeBoxes.add(timeBox14);
        this.timeBoxes.add(timeBox15);
        this.timeBoxes.add(timeBox16);
        this.timeBoxes.add(timeBox17);
        this.timeBoxes.add(timeBox18);
        this.timeBoxes.add(timeBox19);
        this.timeBoxes.add(timeBox20);
        this.timeBoxes.add(timeBox21);
        this.timeBoxes.add(timeBox22);
        this.timeBoxes.add(timeBox23);
    }

    @Override
    public void resizeTimelineView() {
        for(int eventHour = 0; eventHour < timeBoxes.size(); eventHour++) {
            VBox timeBox = timeBoxes.get(eventHour);
            RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(eventHour);
            rowConstraints.setPrefHeight(timeBox.getHeight() + SPACING);
        }
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
