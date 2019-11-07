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

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<? extends Item> PREDICATE_SORT_ALL_ITEMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<? super Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Item> PREDICATE_SHOW_ALL_REPLENISH_ITEMS = unused -> true;


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

    Xpire getXpire();

    ReadOnlyListView<Item> getReplenishList();

    void setXpire(ReadOnlyListView<XpireItem> xpire);

    void setReplenishList(ReadOnlyListView<Item> replenishList);

    ObservableList<? extends Item> getItemList(ListType listType);

    boolean hasItem(ListType listType, Item item);

    void deleteItem(ListType listType, Item item);

    void addItem(ListType listType, Item item);

    void setItem(ListType listType, Item currentItem, Item newItem);

    void sortXpire(XpireMethodOfSorting method);

    FilteredList<? extends Item> getCurrentList();

    void setCurrentList(ListType listType);

    void filterCurrentList(ListType listType, Predicate<? extends Item> predicate);

    void update(State state);

    ListType getCurrentView();
}
