package seedu.algobase.model.gui;

import java.util.NoSuchElementException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.exceptions.DuplicateTabDataException;

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
     * Resets the {@code TabManager} with the given tabManager
     *
     * @param tabManager the tabManager to be reset to.
     */
    public void resetData(ReadOnlyTabManager tabManager) {
        this.tabsData.setTabsData(tabManager.getTabsDataList());
        this.displayTabPaneIndex.setValue(tabManager.getDisplayTabPaneIndex().getValue());
        this.detailsTabPaneIndex.setValue(tabManager.getDetailsTabPaneIndex().getValue());
    }

    /**
     * Refreshes the TabManager.
     */
    public void refreshTabManager() {
        int displayTabPaneIndexValue = this.displayTabPaneIndex.intValue();
        int detailsTabPaneIndexValue = this.detailsTabPaneIndex.intValue();
        this.tabsData.refresh();
        this.displayTabPaneIndex.setValue(displayTabPaneIndexValue);
        this.detailsTabPaneIndex.setValue(detailsTabPaneIndexValue);
    }

    // ReadOnlyTabManager

    @Override
    public ObservableIntegerValue getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    @Override
    public ObservableIntegerValue getDetailsTabPaneIndex() {
        return detailsTabPaneIndex;
    }

    @Override
    public ObservableList<TabData> getTabsDataList() {
        return tabsData.asUnmodifiableObservableList();
    }

    // WriteOnlyTabManager
    // Display Tab

    /**
     * Checks if a given {@code Index} is a valid Display Tab Index.
     *
     * @param index The index to be checked.
     */
    private boolean isValidDisplayTabPaneIndex(Index index) {
        int indexValue = index.getZeroBased();
        return indexValue >= 0 && indexValue < ModelType.NUMBER_OF_TABS;
    }

    /**
     *
     * @param index
     * @throws IndexOutOfBoundsException
     */
    private void setDisplayTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        if (!isValidDisplayTabPaneIndex(index)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        displayTabPaneIndex.setValue(index.getZeroBased());
    }

    @Override
    public TabCommandType switchDisplayTab(Index index) throws IndexOutOfBoundsException {
        isValidDisplayTabPaneIndex(index);
        setDisplayTabPaneIndex(index);
        return TabCommandType.SWITCH_DISPLAY;
    }

    // Details Tab
    private boolean isValidDetailsTabPaneIndex(Index index) {
        int indexValue = index.getZeroBased();
        return (indexValue >= 0 && indexValue < tabsData.size()) || (indexValue == 0 && tabsData.size() == 0);
    }

    private Index getDetailsTabIndex(TabData tabData) {
        return tabsData.indexOf(tabData);
    }

    private void setDetailsTabPaneIndex(Index index) throws IndexOutOfBoundsException {
        if (!isValidDetailsTabPaneIndex(index)) {
            throw new IndexOutOfBoundsException("Tab value is invalid");
        }
        detailsTabPaneIndex.setValue(index.getZeroBased());
    }

    private void checkIfContains(TabData tabData) throws NoSuchElementException {
        if (!this.tabsData.contains(tabData)) {
            throw new NoSuchElementException("No Such Tab Data exists");
        }
    }

    @Override
    public TabCommandType openDetailsTab(TabData tabData) {
        try {
            // Adds a new tab and switches to that tab
            this.tabsData.add(tabData);
            Index tabIndex = getDetailsTabIndex(tabData);
            setDetailsTabPaneIndex(tabIndex);
            return TabCommandType.OPEN_DETAILS;
            // If TabData is not unique, switch to the existing tab
        } catch (DuplicateTabDataException e) {
            assert this.tabsData.contains(tabData);
            switchDetailsTab(tabData);
            return TabCommandType.SWITCH_DETAILS;
        }
    }

    @Override
    public TabCommandType switchDetailsTab(TabData tabData) throws NoSuchElementException {
        Index tabIndex = getDetailsTabIndex(tabData);
        assert this.tabsData.contains(tabData);
        setDetailsTabPaneIndex(tabIndex);
        return TabCommandType.SWITCH_DETAILS;
    }

    @Override
    public TabCommandType switchDetailsTab(Index index) throws IndexOutOfBoundsException {
        isValidDetailsTabPaneIndex(index);
        setDetailsTabPaneIndex(index);
        return TabCommandType.SWITCH_DETAILS;
    }

    @Override
    public TabCommandType closeDetailsTab(TabData tabData) throws NoSuchElementException {
        checkIfContains(tabData);
        assert this.tabsData.contains(tabData);
        Index tabIndex = getDetailsTabIndex(tabData);
        int detailsTabPaneIndexValue = detailsTabPaneIndex.intValue();
        this.tabsData.remove(tabData);

        // If there are no tab data
        if (this.tabsData.size() == 0) {
            // Do nothing
        } else if (detailsTabPaneIndexValue >= tabIndex.getZeroBased()) {
            // decrement the details tab pane index
            detailsTabPaneIndex.setValue(detailsTabPaneIndexValue - 1);
        }

        return TabCommandType.CLOSE_DETAILS;
    }

    @Override
    public TabCommandType closeDetailsTab(Index index) throws IndexOutOfBoundsException {
        isValidDetailsTabPaneIndex(index);
        int detailsTabPaneIndexValue = detailsTabPaneIndex.intValue();
        this.tabsData.remove(tabsData.asUnmodifiableObservableList().get(index.getZeroBased()));

        // If there are no tab data
        if (this.tabsData.size() == 0) {
            // Do nothing
        } else if (detailsTabPaneIndexValue >= index.getZeroBased()) {
            // decrement the details tab pane index
            detailsTabPaneIndex.setValue(detailsTabPaneIndexValue - 1);
        }

        return TabCommandType.CLOSE_DETAILS;
    }
}
