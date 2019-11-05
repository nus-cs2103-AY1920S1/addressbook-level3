package seedu.address.ui.panels;

import java.net.URL;

import seedu.address.ui.UiPart;

/**
 * Represents panels of the UI.
 * It contains a scene graph with a root node of type {@code T}.
 */
public abstract class PanelComponent<T> extends UiPart<T> {
    /**
     * Constructs a PanelComponent using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     *
     * @param fxmlFileName The name of the FXML file to use.
     * @see UiPart(URL)
     */
    public PanelComponent(String fxmlFileName) {
        super(fxmlFileName);
    }
}
