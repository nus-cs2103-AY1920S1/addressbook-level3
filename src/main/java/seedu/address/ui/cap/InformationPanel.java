package seedu.address.ui.cap;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class InformationPanel extends UiPart<Region> {
    private static final String FXML = "InformationPanel.fxml";

    @FXML
    private Label capDisplay;

    @FXML
    private Label mcDisplay;

    public InformationPanel(double cap, double mc) {
        super(FXML);
        requireNonNull(cap);
        requireNonNull(mc);
        capDisplay.setText("CAP: " + String.valueOf(cap));
        mcDisplay.setText("MC: " + String.valueOf(mc));
        capDisplay.setStyle("-fx-box-border: transparent;");
    }

    public void setCapToUser(double capToUser) {
        requireNonNull(capToUser);
        capDisplay.setText("CAP: " + String.valueOf(capToUser));
    }

    public void setMcToUser(double mcToUser) {
        requireNonNull(mcToUser);
        mcDisplay.setText("MC: " + String.valueOf(mcToUser));
    }
}
