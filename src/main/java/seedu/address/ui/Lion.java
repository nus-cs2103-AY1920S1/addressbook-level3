package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Defines the display for the Lion in the user interface.
 */
public class Lion extends UiPart<Region> {

    private static final String FXML = "Lion.fxml";

    @FXML
    private Label response;

    public Lion () {
        super(FXML);
    }

    public void setResponse(String response) {
        this.response.setText(response);
    }
}
