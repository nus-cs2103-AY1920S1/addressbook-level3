package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

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


    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        setStatusBarPath(saveLocation);
    }

    /**
     * Sets the path to be displayed on the status bar.
     * @param saveLocation the path to be displayed.
     */
    public void setStatusBarPath(Path saveLocation) {
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

}
