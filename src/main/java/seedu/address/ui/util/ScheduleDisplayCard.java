package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.ui.UiPart;

public class ScheduleDisplayCard extends UiPart<Region> {

    private static final String FXML = "ScheduleDisplayCard.fxml";

    @FXML
    private GridPane scheduleDisplayContainer;

    @FXML
    private Label eventName;

    @FXML
    private Label venue;

    @FXML
    private Label timing;

    public ScheduleDisplayCard(String eventName, Timeslot timeslot) {
        super(FXML);
        this.eventName.setText(eventName);
        this.venue.setText(timeslot.getVenue().toString());
        this.timing.setText(TimeFormatter.formatTimeToString(timeslot.getStartTime())
                + " - " + TimeFormatter.formatTimeToString(timeslot.getEndTime()));
    }
}
