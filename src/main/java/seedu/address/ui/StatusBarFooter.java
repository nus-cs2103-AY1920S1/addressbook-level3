package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI class for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label footerMessage;

    public StatusBarFooter(Path saveLocation) {
        super(FXML);

        String filePath = Paths.get(".").resolve(saveLocation).toString();
        footerMessage.setText("Location of data files: " + filePath);
    }

    /**
     * Sets the display message of the status bar footer.
     * @param message {@code String} message to display in the status bar footer.
     */
    public void setMessage(String message) {
        footerMessage.setText(message);
    }
}
