package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class FooterBar extends UiPart<Region> {

    private static final String FXML = "FooterBar.fxml";

    @FXML
    private Label footerBarLabel;


    public FooterBar() {
        super(FXML);
        footerBarLabel.setText("Tip: Use the help window to view the full list of commands.");
    }

}
