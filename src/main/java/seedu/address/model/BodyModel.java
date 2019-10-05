package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.entity.body.Body;

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface BodyModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Body> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns true if a body with the same identity as {@code body} exists in the address book.
     */
    boolean hasBody(Body body);

    /**
     * Deletes the given body.
     * The body must exist in the address book.
     */
    void deleteBody(Body target);

    /**
     * Adds the given body.
     * {@code body} must not already exist in the address book.
     */
    void addBody(Body body);

    /**
     * Replaces the given body {@code target} with {@code editedBody}.
     * {@code target} must exist in the address book.
     * The body identity of {@code editedBody} must not be the same as another existing body in the address book.
     */
    void setBody(Body target, Body editedBody);

    /** Returns an unmodifiable view of the filtered body list */
    ObservableList<Body> getFilteredBodyList();

    /**
     * Updates the filter of the filtered body list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBodyList(Predicate<Body> predicate);
}
