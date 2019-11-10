package io.xpire.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import io.xpire.commons.core.GuiSettings;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.state.State;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

//@@author JermyTan
/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<? extends Item> PREDICATE_SORT_ALL_ITEMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<? super Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

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
    Path getListFilePath();

    /**
     * Sets the user prefs' xpire file path.
     */
    void setListFilePath(Path xpireFilePath);

    /**
     * Returns an array containing xpire and replenish list.
     */
    ReadOnlyListView<? extends Item>[] getLists();

    /**
     * Returns the {@code Xpire}.
     */
    Xpire getXpire();

    /**
     * Returns the {@code ReplenishList}
     */
    ReplenishList getReplenishList();

    /**
     * Overrides the current {@code Xpire}'s data with another existing {@code Xpire}.
     *
     * @param xpire Another existing {@code Xpire}.
     */
    void setXpire(ReadOnlyListView<XpireItem> xpire);

    /**
     * Overrides the current {@code ReplenishList}'s data with another existing {@code ReplenishList}.
     *
     * @param replenishList Another existing {@code ReplenishList}.
     */
    void setReplenishList(ReadOnlyListView<Item> replenishList);

    /**
     * Returns the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @return Item list belonging to either {@code Xpire} or {@code ReplenishList}.
     */
    ObservableList<? extends Item> getItemList(ListType listType);

    /**
     * Checks if an item exists in the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param item Item to be checked.
     * @return {@code true} if item already exists in the list else {@code false}.
     */
    boolean hasItem(ListType listType, Item item);

    /**
     * Deletes an item in the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param item Item to be deleted.
     */
    void deleteItem(ListType listType, Item item);

    /**
     * Adds an item to the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param item Item to be added.
     */
    void addItem(ListType listType, Item item);

    /**
     * Replaces an existing item with the new item in the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param currentItem Existing item in the list to be replaced.
     * @param newItem New item to replace the existing item.
     */
    void setItem(ListType listType, Item currentItem, Item newItem);

    /**
     * Updates the sorting method of the list in {@code Xpire}.
     *
     * @param method Sorting method.
     */
    void sortXpire(XpireMethodOfSorting method);

    /**
     * Returns the current view list of {@code Item} backed by the internal list of
     * {@code Xpire} or {@code ReplenishList}.
     */
    FilteredList<? extends Item> getCurrentList();

    /**
     * Updates the current view list to the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     */
    void setCurrentList(ListType listType);

    /**
     * Adds a new filtering constraint to the existing current view list.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param predicate Filtering constraint.
     */
    void filterCurrentList(ListType listType, Predicate<? extends Item> predicate);

    /**
     * Update the entire state of Xpire.
     *
     * @param state New state.
     */
    void update(State state);

    /**
     * Returns the identifier of which list the current view list is showing.
     *
     * @return Either {@code XPIRE} or {@code REPLENISH}.
     */
    ListType getCurrentView();
}
