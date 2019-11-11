package seedu.address.ui.home;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.display.timeslots.PersonTimeslot;
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

        if (timeslot.getEndTime().isBefore(LocalTime.now())) {
            //This event has passed.
            eventName.setStyle("-fx-opacity: 0.3;");
            venue.setStyle("-fx-opacity: 0.3;");
            timing.setStyle("-fx-opacity: 0.3;");
        }
        Tooltip eventNameTooltip = new Tooltip(timeslot.getEventName());
        Tooltip venueTooltip = new Tooltip(timeslot.getVenue().toString());
        Tooltip.install(eventName, eventNameTooltip);
        Tooltip.install(venue, venueTooltip);
    }
}
