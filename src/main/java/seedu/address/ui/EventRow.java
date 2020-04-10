package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * The UI component that displays the name of an event when "view performance" command is called.
 */
public class EventRow extends UiPart<Region> {

    private static final String FXML = "EventRow.fxml";

    @FXML
    private Label eventName;

    public EventRow(String eventName) {
        super(FXML);
        this.eventName.setText(eventName);
    }
}
