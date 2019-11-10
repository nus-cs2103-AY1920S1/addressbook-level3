package seedu.address.ui.panel.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An UI that represents the details for an event.
 */
public class TimelineLabel extends UiPart<Region> {

    private static final String FXML = "TimelineLabel.fxml";

    @FXML
    private Label timelineLabel;

    public TimelineLabel(String label) {
        super(FXML);
        this.timelineLabel.setText(label);
    }
}
