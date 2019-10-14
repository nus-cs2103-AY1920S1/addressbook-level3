package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.Session;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;
    @FXML
    private Label sessionDetails;


    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        saveLocationStatus.setText("Data File: " + Paths.get(".").resolve(saveLocation).toString());
        sessionDetails.setText("Logged in as: Not Logged In");
    }

    public void setLoginDetails(Session session) {
        sessionDetails.setText("Logged in as: " + session.getLoggedInPerson().getUsername()
                + " on " + session.getLoginTime());
    }

}
