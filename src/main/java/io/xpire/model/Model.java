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
     * Returns the user prefs' xpire file path.
     */
    Path getXpireFilePath();

    /**
     * Sets the user prefs' xpire file path.
     */
    void setXpireFilePath(Path xpireFilePath);

    /**
     * Replaces xpire data with the data in {@code xpire}.
     */
    void setXpire(ReadOnlyXpire xpire);

    /** Returns the xpire */
    ReadOnlyXpire getXpire();

    /**
     * Returns true if an item with the same identity as {@code item} exists in xpire.
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given item.
     * The item must exist in xpire.
     */
    void deleteItem(Item target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in xpire.
     */
    void addItem(Item item);

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in xpire.
     * The item identity of {@code editedItem} must not be the same as another existing item in xpire.
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
