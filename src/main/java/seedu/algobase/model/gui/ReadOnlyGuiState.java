package seedu.algobase.model.gui;

/**
 * Unmodifiable view of a guistate
 */
public interface ReadOnlyGuiState {
    ReadOnlyTabManager getReadOnlyTabManager();
}
