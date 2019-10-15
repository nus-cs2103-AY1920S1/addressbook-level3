package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.eatery.Eatery;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Eatery> PREDICATE_SHOW_ALL_EATERIES = unused -> true;

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
     * Returns true if a eatery with the same identity as {@code eatery} exists in the address book.
     */
    boolean hasEatery(Eatery eatery);

    /**
     * Deletes the given eatery.
     * The eatery must exist in the address book.
     */
    void deleteEatery(Eatery target);

    /**
     * Adds the given eatery.
     * {@code eatery} must not already exist in the address book.
     */
    void addEatery(Eatery eatery);

    /**
     * Replaces the given eatery {@code target} with {@code editedEatery}.
     * {@code target} must exist in the address book.
     * The eatery identity of {@code editedEatery} must not be the same as another existing eatery in the address book.
     */
    void setEatery(Eatery target, Eatery editedEatery);

    /** Returns an unmodifiable view of the filtered eatery list */
    ObservableList<Eatery> getFilteredEateryList();

    /**
     * Updates the filter of the filtered eatery list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEateryList(Predicate<Eatery> predicate);
}
