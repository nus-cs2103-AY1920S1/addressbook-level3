package seedu.algobase.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import seedu.algobase.commons.core.index.Index;

/**
 * Tracks the current state of the GUI.
 */
public class GuiState {

    private IntegerProperty displayTabPaneIndex =
        new SimpleIntegerProperty(ModelEnum.PROBLEM.getDisplayTabPaneIndex());

    /**
     * Creates a {@code GuiState} with default values.
     */
    public GuiState() {
    }

    public ObservableIntegerValue getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < ModelEnum.values().length;
    }

    public void setDisplayTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        int indexValue = index.getZeroBased();
        if (!isValidIndex(indexValue)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        displayTabPaneIndex.setValue(indexValue);
    }
}
