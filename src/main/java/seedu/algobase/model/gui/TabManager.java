package seedu.algobase.model.gui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;

/**
 * The main TabManager of the GUI.
 */
public class TabManager implements ReadOnlyTabManager, WriteOnlyTabManager {

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
        return index >= 0 && index < ModelType.numberOfTabs;
    }

    @Override
    public ObservableIntegerValue getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    @Override
    public void setDisplayTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        int indexValue = index.getZeroBased();
        if (!isValidDisplayTabPaneIndex(indexValue)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        displayTabPaneIndex.setValue(indexValue);
    }

    // Details tab
    public boolean isValidDetailsTabPaneIndex(int index) {
        return (index >= 0 && index < tabsData.size()) || (index == 0 && tabsData.size() == 0);
    }

    @Override
    public ObservableIntegerValue getDetailsTabPaneIndex() {
        return detailsTabPaneIndex;
    }

    @Override
    public void setDetailsTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        int indexValue = index.getZeroBased();
        if (!isValidDetailsTabPaneIndex(indexValue)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        detailsTabPaneIndex.setValue(indexValue);
    }

    public void setDetailsTabPaneIndex(ModelType modelType, Id modelId) throws NoSuchElementException {
        setDetailsTabPaneIndex(tabsData.indexOf(new TabData(modelType, modelId)));
    }

    /**
     * Adds TabData to algobase.
     * The TabData must not already exist in the algobase.
     */
    public void addDetailsTabData(TabData tab) {
        this.tabsData.add(tab);
    }

    public void removeDetailsTabData(TabData tabData) {
        this.tabsData.remove(tabData);
    }

    public void removeDetailsTabData(Index index) {
        removeDetailsTabData(getTabsDataList().get(index.getZeroBased()));
    }

    public Index getDetailsTabIndex(TabData tabData) {
        return tabsData.indexOf(tabData);
    }

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
    public boolean hasDetailsTabData(TabData tabData) {
        requireNonNull(tabData);
        return tabsData.contains(tabData);
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

    @Override
    public ObservableList<TabData> getTabsDataList() {
        return tabsData.asUnmodifiableObservableList();
    }

    @Override
    public Consumer<Id> addDetailsTabConsumer(ModelType modelType) {
        return (Id id) -> addDetailsTabData(new TabData(modelType, id));
    }

    @Override
    public Consumer<Id> switchDetailsTabConsumer(ModelType modelType) {
        return (Id id) -> setDetailsTabPaneIndex(modelType, id);
    }

    @Override
    public Consumer<Id> removeDetailsTabConsumer(ModelType modelType) {
        return (id) -> removeDetailsTabData(new TabData(modelType, id));
    }

}
