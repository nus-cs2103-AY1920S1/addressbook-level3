package seedu.address.ui.panel;

import java.net.URL;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Represents a viewable Panel in the GUI.
 */
public abstract class Panel extends UiPart<Region> {

    public Panel(String fxmlFileName) {
        super(fxmlFileName);
    }

    public Panel(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }

    public Panel(URL fxmlFileUrl, Region root) {
        super(fxmlFileUrl, root);
    }

    public Panel(String fxmlFileName, Region root) {
        super(fxmlFileName, root);
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
