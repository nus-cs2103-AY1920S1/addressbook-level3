package calofit.model;

import calofit.commons.core.GuiSettings;
import calofit.model.meal.Dish;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Dish> PREDICATE_SHOW_ALL_DISHES = unused -> true;

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
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a dish with the same identity as {@code dish} exists in the address book.
     */
    boolean hasDish(Dish dish);

    /**
     * Deletes the given dish.
     * The dish must exist in the address book.
     */
    void deleteDish(Dish target);

    /**
     * Adds the given dish.
     * {@code dish} must not already exist in the address book.
     */
    void addDish(Dish dish);

    /**
     * Replaces the given dish {@code target} with {@code editedDish}.
     * {@code target} must exist in the address book.
     * The dish identity of {@code editedDish} must not be the same as another existing dish in the address book.
     */
    void setDish(Dish target, Dish editedDish);

    /** Returns an unmodifiable view of the filtered dish list */
    ObservableList<Dish> getFilteredDishList();

    /**
     * Updates the filter of the filtered dish list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDishList(Predicate<Dish> predicate);
}
