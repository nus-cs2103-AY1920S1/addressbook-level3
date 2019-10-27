package seedu.address.ui;

import javafx.scene.layout.Region;
import seedu.address.model.Model;

/**
 * UI component that is displayed when the command to view trainings and performance on a
 * specified date is issued.
 */
public class CalendarDetailPanel extends UiPart<Region> {

    private static final String FXML = "CalendarDetailPanel.fxml";

    public CalendarDetailPanel(Model model) {
        super(FXML);
    }
}
