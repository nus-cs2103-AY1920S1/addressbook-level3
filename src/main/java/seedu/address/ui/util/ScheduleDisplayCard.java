package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.ui.UiPart;

/**
 * Ui component to show upcoming schedules for today.
 */
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

    public ScheduleDisplayCard(PersonTimeslot timeslot) {
        super(FXML);
        this.eventName.setText(timeslot.getEventName());
        this.venue.setText(timeslot.getVenue().toString());
        this.timing.setText(timeslot.getStartTime().toString()
                + " - " + timeslot.getEndTime().toString());
    }
}
