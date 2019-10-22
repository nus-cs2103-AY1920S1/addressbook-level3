package seedu.algobase.model.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.ModelType;

/**
 * The main TabManager of the GUI.
 */
public class TabManager {

    private static final int STARTING_INDEX = 0;

    private IntegerProperty displayTabPaneIndex =
        new SimpleIntegerProperty(STARTING_INDEX);

    private IntegerProperty detailsTabPaneIndex =
        new SimpleIntegerProperty(STARTING_INDEX);

    private ObservableList<TabData> tabs;

    public TabManager() {
        this.tabs = FXCollections.observableArrayList();
    }

    // Display Tab
    public boolean isValidDisplayTabPaneIndex(int index) {
        return index >= 0 && index < ModelType.values().length;
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

    // Details tab
    public ObservableIntegerValue getDetailsTabPaneIndex() {
        return detailsTabPaneIndex;
    }

    public void setDetailsTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        int indexValue = index.getZeroBased();
        if (!isValidDisplayTabPaneIndex(indexValue)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        detailsTabPaneIndex.setValue(indexValue);
    }

    public void addTab(TabData... tabs) {
        this.tabs.addAll(tabs);
    }

    public ObservableList<TabData> getTabs() {
        return tabs;
    }
}
