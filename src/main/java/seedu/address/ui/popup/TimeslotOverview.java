package seedu.address.ui.popup;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.ui.UiPart;

/**
 * UI component to show the timeslot overview of a particular popup.
 */
public class TimeslotOverview extends UiPart<Region> {

    private static final String FXML = "TimeslotOverview.fxml";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/uuuu");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    private VBox timeslotOverviewContainer;

    @FXML
    private Label eventName;

    @FXML
    private Label dateString;

    @FXML
    private Label timeString;

    @FXML
    private Label venueString;


    public TimeslotOverview(PersonTimeslot data) {
        super(FXML);

        timeslotOverviewContainer.setAlignment(Pos.CENTER);

        this.eventName.setText(data.getEventName());

        this.dateString.setText(
                data.getDate().format(DATE_FORMATTER) + " "
                        + "(" + data.getDate().getDayOfWeek().toString() + ")"
        );

        this.timeString.setText(
                data.getStartTime().format(TIME_FORMATTER) + " - "
                        + data.getEndTime().format(TIME_FORMATTER)
        );

        if (!data.getVenue().toString().trim().equals("")) {
            this.venueString.setText(
                    data.getVenue().toString()
            );
        }


    }

}
