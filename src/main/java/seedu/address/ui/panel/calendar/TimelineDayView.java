package seedu.address.ui.panel.calendar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import seedu.address.model.events.EventSource;
import seedu.address.ui.EventCard;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An UI component that displays the feedback to the user.
 */
public class TimelineDayView extends UiPart<Region> {

    private static final String FXML = "TimelineDayView.fxml";
    private static final Integer SPACING = 62;

    private UiParser uiParser;
    private HashMap<Integer, EventSource> eventMap;    // The Key is the Hour of the day
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

    public TimelineDayView(Integer day, Integer month, Integer year, UiParser uiParser) {
        super(FXML);
        this.uiParser = uiParser;
        this.day = day;
        this.month = month;
        this.year = year;
        this.eventMap = new HashMap<Integer, EventSource>();
        this.timeBoxes = new ArrayList<>();

        this.timelineTitle.setText("Timeline: " + uiParser.getEnglishDate(day, month, year));
        addTimeBoxes();
    }

    public void eventChange(List<EventSource> eventList) {
        resetTimeline();
        for (EventSource event : eventList) {
            if(sameDay(event)) {
                addEventCard(event);
            }
        }
    }

    private boolean sameDay(EventSource event) {
        return uiParser.getDay(event.getStartDateTime().getDateTime()).equals(this.day);
    }

    private void addEventCard(EventSource event) {
        // Gets the event Hour
        Integer eventHour = uiParser.getHour(event.getStartDateTime().getDateTime());
        this.eventMap.put(eventHour, event);

        // Creates and add the event card
        EventCard eventCard = new EventCard(event, uiParser);
        VBox timeBox = timeBoxes.get(eventHour);
        timeBox.getChildren().add(eventCard.getRoot());

        // Set Constraints for the grid pane
        RowConstraints rowConstraints = timelineGrid.getRowConstraints().get(eventHour);
        rowConstraints.setPrefHeight(timeBox.getHeight() + SPACING);
        // For the last row
        if(eventHour.equals(23)) {
            rowConstraints.setPrefHeight(rowConstraints.getPrefHeight() + SPACING);
        }

    }

    private void resetTimeline() {
        eventMap.clear();
        for(int eventHour = 0; eventHour < 24; eventHour++) {
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
}
