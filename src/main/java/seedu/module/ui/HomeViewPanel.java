package seedu.module.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.module.commons.core.LogsCenter;

/**
 * Panel containing the home page (when there is no active module being displayed).
 */
public class HomeViewPanel extends UiPart<Region> {
    private static final String FXML = "HomeViewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    public HomeViewPanel() {
        super(FXML);
    }
}
