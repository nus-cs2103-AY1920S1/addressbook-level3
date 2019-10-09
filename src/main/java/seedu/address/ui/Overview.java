package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * Defines the display for the overview tab in the user interface.
 */
public class Overview extends UiPart<Region> {

    private static final String FXML = "Overview.fxml";

    public Overview () {
        super(FXML);
    }
}
