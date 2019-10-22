package budgetbuddy.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;


    public StatusBarFooter() {
        super(FXML);
        // TODO: Put something useful in the status bar
        saveLocationStatus.setText("");
    }

}
