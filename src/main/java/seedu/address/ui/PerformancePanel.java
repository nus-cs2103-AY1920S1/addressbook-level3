package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * UI component that is displayed when the command to view performance is issued.
 */
public class PerformancePanel extends UiPart<Region> {

    private static final String FXML = "PerformancePanel.fxml";

    public PerformancePanel() {
        super(FXML);
    }
}
