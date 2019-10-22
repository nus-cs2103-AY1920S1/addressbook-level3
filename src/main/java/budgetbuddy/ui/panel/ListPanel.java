package budgetbuddy.ui.panel;

import budgetbuddy.ui.UiPart;
import javafx.scene.layout.Region;

/**
 * Represents an abstract panel containing a list of items.
 */
public abstract class ListPanel extends UiPart<Region> {
    public ListPanel(String fxmlFileName) {
        super(fxmlFileName);
    }
}
