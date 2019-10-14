package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class CurrentModeFooter extends UiPart<Region> {

    private static final String FXML = "CurrentModeFooter.fxml";

    @FXML
    private Label currentMode;

    public CurrentModeFooter() {
        super(FXML);
        currentMode.setText("Current mode: " + "load");
    }

    public void changeMode(String mode) {
        currentMode.setText("Current mode: " + mode);
    }

}
