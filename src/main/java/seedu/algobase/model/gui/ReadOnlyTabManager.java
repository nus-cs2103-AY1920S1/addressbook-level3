package seedu.algobase.model.gui;

import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a tabmanager
 */
public interface ReadOnlyTabManager {

    ObservableIntegerValue getDisplayTabPaneIndex();

    ObservableIntegerValue getDetailsTabPaneIndex();

    ObservableList<TabData> getTabsDataList();
}
