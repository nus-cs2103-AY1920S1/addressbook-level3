package seedu.algobase.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;

/**
 * Tracks the current state of the GUI.
 */
public class GuiState {


    private IntegerProperty displayTabPaneIndex
        = new SimpleIntegerProperty(ModelEnum.PROBLEM.getDisplayTabPaneIndex());

    /**
     * Creates a {@code GuiState} with default values.
     */
    public GuiState() {
    }

    public ObservableIntegerValue getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    public void setDisplayTabPaneIndex(int index) {
        displayTabPaneIndex.setValue(index);
    }
}
