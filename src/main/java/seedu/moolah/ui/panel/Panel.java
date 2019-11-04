package seedu.moolah.ui.panel;

import javafx.scene.layout.Region;
import seedu.moolah.ui.UiPart;

/**
 * Represents a viewable Panel in the GUI.
 */
public abstract class Panel extends UiPart<Region> {

    public Panel(String fxmlFileName) {
        super(fxmlFileName);
    }

    /**
     * Brings the panel to view.
     */
    public abstract void view();

    /**
     * Hides the panel from view.
     */
    public abstract void hide();
}
