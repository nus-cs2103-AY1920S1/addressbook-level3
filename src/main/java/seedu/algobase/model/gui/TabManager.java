package seedu.algobase.model.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The main TabManager of the GUI.
 */
public class TabManager {
    private ObservableList<AlgoBaseTab> tabs;

    public TabManager() {
        this.tabs = FXCollections.observableArrayList();
    }

    public void addTab(AlgoBaseTab... tabs) {
        this.tabs.addAll(tabs);
    }

    public ObservableList<AlgoBaseTab> getTabs() {
        return tabs;
    }
}
