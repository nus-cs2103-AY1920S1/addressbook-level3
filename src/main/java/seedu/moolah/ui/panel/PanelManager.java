package seedu.moolah.ui.panel;

import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;

/**
 * Represents the Panel Map used by the GUI to navigate between Panels.
 */
public interface PanelManager {

    /**
     * Switches the in-view Panel to the Panel mapped to the specified Panel Name, if there is a Panel
     * mapped to that name.
     * @param panelName The Panel Name used to request a change of the in-view Panel.
     * @throws UnmappedPanelException if there is no Panel mapped to that name
     */
    void viewPanel(PanelName panelName) throws UnmappedPanelException;

    /**
     * Returns the current in-view Panel
     */
    Panel getCurrentPanel();

    /**
     * Returns the PanelName assigned to the current in-view panel.
     * @return PanelName assigned to the current in-view panel.
     */
    PanelName getCurrentPanelName();

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
