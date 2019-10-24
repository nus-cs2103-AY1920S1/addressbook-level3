package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

/**
 * A ui for the split window that is displayed at the center of the application.
 */
public class CentralDisplay extends UiPart<Region> {

    private static final String FXML = "CentralDisplay.fxml";

    @FXML
    private Accordion sideDisplay;

    @FXML
    private TabPane tabDisplay;

    public CentralDisplay() {
        super(FXML);
    }
}
