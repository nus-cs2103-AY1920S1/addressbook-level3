package io.xpire.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import io.xpire.commons.core.GuiSettings;
import io.xpire.model.item.Item;
import io.xpire.model.item.sort.MethodOfSorting;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getExpiryDateTrackerFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setExpiryDateTrackerFilePath(Path expiryDateTrackerFilePath);

    /**
     * Replaces expiry date tracker data with the data in {@code expiryDateTracker}.
     */
    void setExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker);

    /** Returns the expiry date tracker */
    ReadOnlyExpiryDateTracker getExpiryDateTracker();

    /**
     * Returns true if an item with the same identity as {@code item} exists in the expiry date tracker.
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given item.
     * The item must exist in the tracker.
     */
    void deleteItem(Item target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the tracker.
     */
    void addItem(Item item);

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in the tracker.
     * The item identity of {@code editedItem} must not be the same as another existing item in the expiry date tracker.
     */
    void setItem(Item target, Item editedItem);

    /** Returns an unmodifiable view of the sorted item list */
    ObservableList<Item> getSortedItemList();

    /**
     * Sorts the filtered item list.
     * @param method The method of sorting.
     */
    void sortItemList(MethodOfSorting method);

    /** Returns an unmodifiable view of the filtered item list */
    ObservableList<Item> getFilteredItemList();

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemList(Predicate<Item> predicate);

}
