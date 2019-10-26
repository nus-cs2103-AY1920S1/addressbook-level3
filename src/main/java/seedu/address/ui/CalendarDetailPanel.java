package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * UI component that is displayed when the command to view trainings and performance on a
 * specified date is issued.
 */
public class CalendarDetailPanel extends UiPart<Region> {

    private static final String FXML = "CalendarDetailPanel.fxml";

    public CalendarDetailPanel() {
        super(FXML);
    }
}
