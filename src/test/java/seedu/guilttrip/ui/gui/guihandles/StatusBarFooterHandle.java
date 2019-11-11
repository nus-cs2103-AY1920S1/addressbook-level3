package seedu.guilttrip.ui.gui.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";

    private final Labeled saveLocationNode;

    private String lastRememberedSaveLocation;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    /**
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveLocation = getSaveLocation();
    }

    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by the most
     * recent {@code rememberSaveLocation()} call.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveLocation.equals(getSaveLocation());
    }
}
