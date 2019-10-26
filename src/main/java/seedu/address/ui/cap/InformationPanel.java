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
    private Label infoDisplay;

    public InformationPanel() {
        super(FXML);
        infoDisplay.setStyle("-fx-box-border: transparent;");
    }

    public void setCapToUser(double capToUser) {
        requireNonNull(capToUser);
        infoDisplay.setText(String.valueOf(capToUser));
    }
}
