package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.food.Food;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Food> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns true if a food with the same identity as {@code food} exists in the address book.
     */
    boolean hasPerson(Food food);

    /**
     * Deletes the given food.
     * The food must exist in the address book.
     */
    void deletePerson(Food target);

    /**
     * Adds the given food.
     * {@code food} must not already exist in the address book.
     */
    void addPerson(Food food);

    /**
     * Replaces the given food {@code target} with {@code editedFood}.
     * {@code target} must exist in the address book.
     * The food identity of {@code editedFood} must not be the same as another existing food in the address book.
     */
    void setFood(Food target, Food editedFood);

    /** Returns an unmodifiable view of the filtered food list */
    ObservableList<Food> getFilteredPersonList();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Food> predicate);
}
