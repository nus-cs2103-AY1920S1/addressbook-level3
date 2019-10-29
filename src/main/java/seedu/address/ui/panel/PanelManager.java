package seedu.address.ui.panel;

import seedu.address.ui.panel.exceptions.UnmappedPanelException;

/**
 * Represents the Panel Map used by the GUI to navigate between Panels.
 */
public interface PanelManager {

    /**
     * Assigns a Panel Name to a Panel for later access.
     * @param panelName The name assigned to the specified Panel.
     * @param panel The Panel which is stored for later access.
     * @return True if the Panel was added, false otherwise.
     */
    boolean setPanel(PanelName panelName, Panel panel);

    /**
     * Returns True if there is a Panel mapped to the specified Panel Name, False otherwise.
     * @param panelName The Panel Name which is used to query the Panel Manager.
     * @return True if there is a Panel mapped to the specified Panel Name, False otherwise.
     */
    boolean hasPanel(PanelName panelName);

    /**
     * Returns the Panel mapped to the specified Panel Name.
     * @param panelName The Panel Name used in the request.
     * @return the Panel which is mapped to the specified Panel Name.
     */
    Panel getPanel(PanelName panelName) throws UnmappedPanelException;

    /**
     * Removes the Panel mapped to the specified Panel Name and returns it.
     * @param panelName The Panel Name used in the request.
     * @return the Panel which is mapped to the specified Panel Name.
     */
    Panel removePanel(PanelName panelName) throws UnmappedPanelException;
}
