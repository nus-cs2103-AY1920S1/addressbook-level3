package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * The UI component that displays the table headers for attendance rate.
 */
public class AttendanceRateTableHeader extends UiPart<Region> {

    private static final String FXML = "AttendanceRateTableHeader.fxml";

    public AttendanceRateTableHeader() {
        super(FXML);
    }
}
