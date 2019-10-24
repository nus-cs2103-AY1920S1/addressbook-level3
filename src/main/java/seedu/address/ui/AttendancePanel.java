package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * UI component that is displayed when the command to view attendance is issued.
 */
public class AttendancePanel extends UiPart<Region> {

    private static final String FXML = "AttendancePanel.fxml";

    public AttendancePanel() {
        super(FXML);
    }
}
