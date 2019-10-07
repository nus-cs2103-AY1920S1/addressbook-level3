package calofit.model;

import calofit.commons.core.GuiSettings;
import calofit.model.meal.Meal;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Meal> PREDICATE_SHOW_ALL_MEALS = unused -> true;

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
     * Returns true if a meal with the same identity as {@code meal} exists in the address book.
     */
    boolean hasMeal(Meal meal);

    /**
     * Deletes the given meal.
     * The meal must exist in the address book.
     */
    void deleteMeal(Meal target);

    /**
     * Adds the given meal.
     * {@code meal} must not already exist in the address book.
     */
    void addMeal(Meal meal);

    /**
     * Replaces the given meal {@code target} with {@code editedMeal}.
     * {@code target} must exist in the address book.
     * The meal identity of {@code editedMeal} must not be the same as another existing meal in the address book.
     */
    void setMeal(Meal target, Meal editedMeal);

    /** Returns an unmodifiable view of the filtered meal list */
    ObservableList<Meal> getFilteredMealList();

    /**
     * Updates the filter of the filtered meal list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMealList(Predicate<Meal> predicate);
}
