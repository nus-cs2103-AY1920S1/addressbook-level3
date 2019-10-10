package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.spending.Spending;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Spending> PREDICATE_SHOW_ALL_SPENDINGS = unused -> true;

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
     * Returns true if a Spending with the same identity as {@code Spending} exists in the address book.
     */
    boolean hasSpending(Spending spending);

    /**
     * Deletes the given Spending.
     * The Spending must exist in the address book.
     */
    void deleteSpending(Spending target);

    /**
     * Adds the given Spending.
     * {@code Spending} must not already exist in the address book.
     */
    void addSpending(Spending spending);

    /**
     * Replaces the given Spending {@code target} with {@code editedSpending}.
     * {@code target} must exist in the address book.
     * The Spending identity of {@code editedSpending} must not be the same as
     * another existing Spending in the address book.
     */
    void setSpending(Spending target, Spending editedSpending);

    /** Returns an unmodifiable view of the filtered Spending list */
    ObservableList<Spending> getFilteredSpendingList();

    /**
     * Updates the filter of the filtered Spending list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSpendingList(Predicate<Spending> predicate);
}
