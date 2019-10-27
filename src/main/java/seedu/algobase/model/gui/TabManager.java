package seedu.algobase.model.gui;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.ModelType;


/**
 * The main TabManager of the GUI.
 */
public class TabManager implements ReadOnlyTabManager {

    private static final int STARTING_INDEX = 0;

    private IntegerProperty displayTabPaneIndex =
        new SimpleIntegerProperty(STARTING_INDEX);

    private IntegerProperty detailsTabPaneIndex =
        new SimpleIntegerProperty(STARTING_INDEX);

    private UniqueTabDataList tabsData;

    public TabManager() {
        this.tabsData = new UniqueTabDataList();
    }

    /**
     * Resets the TabManager.
     */
    public void resetData(ReadOnlyTabManager tabManager) {
        this.detailsTabPaneIndex.setValue(tabManager.getDetailsTabPaneIndex().getValue());
        this.displayTabPaneIndex.setValue(tabManager.getDisplayTabPaneIndex().getValue());
        this.tabsData.setTabsData(tabManager.getTabsDataList());
    }

    // Display Tab
    public boolean isValidDisplayTabPaneIndex(int index) {
        return index >= 0 && index < ModelType.values().length;
    }

    @Override
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
    public boolean isValidDetailsTabPaneIndex(int index) {
        return index >= 0 && index < tabsData.size();
    }

    @Override
    public ObservableIntegerValue getDetailsTabPaneIndex() {
        return detailsTabPaneIndex;
    }

    public void setDetailsTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        int indexValue = index.getZeroBased();
        if (!isValidDetailsTabPaneIndex(indexValue)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        detailsTabPaneIndex.setValue(indexValue);
    }

    public void addTab(TabData... tabs) {
        this.tabsData.addAll(tabs);
    }

    public void removeTab(Index index) {
        this.tabsData.remove(getTabs().get(index.getZeroBased()));
    }

    public ObservableList<TabData> getTabs() {
        return tabsData.asUnmodifiableObservableList();
    }

    public Index getTabIndex(TabData tabData) {
        return tabsData.indexOf(tabData);
    }

    //========== Tab ====================================================================

    /**
     * Replaces the contents of the Plan list with {@code tabsData}.
     * {@code tabsData} must not contain duplicate tabsData.
     */
    public void setTabsData(List<TabData> tabsData) {
        this.tabsData.setTabsData(tabsData);
    }

    /**
     * Returns true if a Plan with the same identity as {@code TabData} exists in the algobase.
     */
    public boolean hasTabData(TabData tabData) {
        requireNonNull(tabData);
        return tabsData.contains(tabData);
    }

    /**
     Adds a TabData to the algobase.
     The Plan must not already exist in the algobase.
     */
    public void addTabData(TabData tabData) {
        tabsData.add(tabData);
    }

    /**
     * Replaces the given Plan {@code target} in the list with {@code editedPlan}.
     * {@code target} must exist in the algobase.
     * The Plan identity of {@code editedPlan} must not be the same as another existing Plan in the algobase.
     */
    public void setTabData(TabData target, TabData editedTabData) {
        requireNonNull(editedTabData);

        tabsData.setTabData(target, editedTabData);
    }

    public void removeTabData(TabData key) {
        tabsData.remove(key);
    }

    @Override
    public ObservableList<TabData> getTabsDataList() {
        return tabsData.asUnmodifiableObservableList();
    }
}
