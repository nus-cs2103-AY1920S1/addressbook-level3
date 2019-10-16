package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * UI component that is displayed when the command to view calendar is issued.
 */
public class Calendar extends UiPart<Region> {

    private static final String FXML = "Calendar.fxml";

    public Calendar() {
        super(FXML);
    }
}
