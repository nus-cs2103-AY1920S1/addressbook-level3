package seedu.mark.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;

/**
 * The Dashboard panel of Mark.
 */
public class DashboardPanel extends UiPart<Region> {

    private static final String FXML = "DashboardPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TextField dummyField;

    public DashboardPanel() {
        //TODO: change params for using (logic.?) folder hierarchy, favorites, reminders, help
        super(FXML);

        dummyField.setText("This is a dummy dashboard. Please change me /. .\\|||");
    }
}
