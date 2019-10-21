package seedu.algobase.model.gui;

/**
 * Tracks the current state of the GUI.
 */
public class GuiState {

    private final TabManager tabManager;

    /**
     * Creates a {@code GuiState} with default values.
     */
    public GuiState() {
        this.tabManager = new TabManager();
    }

    public TabManager getTabManager() {
        return this.tabManager;
    }
}
