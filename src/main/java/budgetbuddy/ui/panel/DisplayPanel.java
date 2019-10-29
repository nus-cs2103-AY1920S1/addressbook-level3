package budgetbuddy.ui.panel;

import budgetbuddy.ui.UiPart;
import javafx.scene.layout.Region;

/**
 * Represents an abstract output display panel.
 */
public abstract class DisplayPanel extends UiPart<Region> {
    public DisplayPanel(String fxmlFileName) {
        super(fxmlFileName);
    }
}
