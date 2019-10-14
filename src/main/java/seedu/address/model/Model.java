package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.food.GroceryItem;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<GroceryItem> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
    void setGroceryList(ReadOnlyAddressBook groceryList);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getGroceryList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasGroceryItem(GroceryItem food);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteGroceryItem(GroceryItem target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addGroceryItem(GroceryItem food);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setGroceryItem(GroceryItem target, GroceryItem editedFood);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<GroceryItem> getFilteredGroceryItemList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroceryItemList(Predicate<GroceryItem> predicate);
}
