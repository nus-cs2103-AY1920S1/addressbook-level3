package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * The UI component that displays the title of a training record.
 */

public class AttendanceTableHeader extends UiPart<Region> {

    private static final String FXML = "AttendanceTableHeader.fxml";

    public AttendanceTableHeader() {
        super(FXML);
    }
}
