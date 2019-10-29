package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.exercise.commons.core.LogsCenter;

/**
 * Placeholder Panel for Specific Information displayed in center panel.
 */
public class InfoDisplayPanel extends UiPart<Region> {
    private static final String FXML = "InfoDisplayPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InfoDisplayPanel.class);


    public InfoDisplayPanel() {
        super(FXML);
    }
}
