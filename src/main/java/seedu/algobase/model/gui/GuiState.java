package seedu.algobase.model.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.ModelEnum;

/**
 * Tracks the current state of the GUI.
 */
public class GuiState {

    private static final int STARTING_INDEX = 0;

    private IntegerProperty displayTabPaneIndex =
        new SimpleIntegerProperty(STARTING_INDEX);

    private IntegerProperty detailsTabPaneIndex =
        new SimpleIntegerProperty(STARTING_INDEX);

    private final TabManager tabManager;

    /**
     * Creates a {@code GuiState} with default values.
     */
    public GuiState() {
        this.tabManager = new TabManager();
    }

    public boolean isValidDisplayTabPaneIndex(int index) {
        return index >= 0 && index < ModelEnum.values().length;
    }

    public ObservableIntegerValue getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    public void setDisplayTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        int indexValue = index.getZeroBased();
        if (!isValidDisplayTabPaneIndex(indexValue)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        displayTabPaneIndex.setValue(indexValue);
    }

    public TabManager getTabManager() {
        return this.tabManager;
    }
}
