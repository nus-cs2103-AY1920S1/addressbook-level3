package seedu.address.ui.popup;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.ui.UiPart;

/**
 * A class to generate timeslot displays.
 */
public class TimeslotView extends UiPart<Region> {

    private static final String FXML = "TimeslotView.fxml";

    @FXML
    private VBox timeslotContainer;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Label locationLabel;

    public TimeslotView(PersonTimeslot data) {

        super(FXML);

        eventNameLabel.setText(data.getEventName());
        startTimeLabel.setText(data.getStartTime().toString());
        endTimeLabel.setText(data.getEndTime().toString());
        locationLabel.setText(data.getVenue().toString());

    }

}
