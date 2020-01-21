package seedu.algobase.model.gui;

import java.util.NoSuchElementException;

import seedu.algobase.commons.core.index.Index;

/**
 * Write-only view of a tabmanager
 */
public interface WriteOnlyTabManager {

    // Display Tab
    /**
     * Switches to the display tab at the given index.
     *
     * @param index The index of the display tab to open
     */
    TabCommandType switchDisplayTab(Index index) throws IndexOutOfBoundsException;

    // Details Tab
    /**
     * Opens a new details tab with a given tab data.
     *
     * @param tabData contains the data to open a new tab.
     */
    TabCommandType openDetailsTab(TabData tabData);

    /**
     * Switches to the details tab with a given tab data.
     *
     * @param tabData contains the data to switch to the new tab.
     */
    TabCommandType switchDetailsTab(TabData tabData) throws NoSuchElementException;

    /**
     * Switches to the details tab at the given index.
     *
     * @param index the index which the details tab should be closed.
     */
    TabCommandType switchDetailsTab(Index index) throws IndexOutOfBoundsException;

    /**
     * Closes the details tab with a given tab data.
     *
     * @param tabData contains the data to switch to the new tab.
     */
    TabCommandType closeDetailsTab(TabData tabData) throws NoSuchElementException;

    /**
     * Closes the details tab at the given index.
     *
     * @param index contains the data to switch to the given index.
     */
    TabCommandType closeDetailsTab(Index index) throws IndexOutOfBoundsException;
}
