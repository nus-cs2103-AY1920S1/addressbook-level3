package seedu.algobase.model.gui;

/**
 * Tracks the current state of the GUI.
 */
public class GuiState implements ReadOnlyGuiState {

    private final TabManager tabManager;

    /**
     * Creates a {@code GuiState} with default values.
     */
    public GuiState() {
        this.tabManager = new TabManager();
    }

    /**
     * Creates a {@code GuiState} with default values.
     */
    public GuiState(GuiState guiState) {
        this.tabManager = new TabManager();
        resetData(guiState);
    }

    /**
     * Creates a {@code GuiState} with a given TabManager
     */
    public GuiState(TabManager tabManager) {
        this.tabManager = new TabManager();
        this.tabManager.resetData(tabManager);
    }

    public TabManager getTabManager() {
        return this.tabManager;
    }

    public ReadOnlyTabManager getReadOnlyTabManager() {
        return this.tabManager;
    }

    public void resetData(ReadOnlyGuiState guiState) {
        this.tabManager.resetData(guiState.getReadOnlyTabManager());
    }
}
