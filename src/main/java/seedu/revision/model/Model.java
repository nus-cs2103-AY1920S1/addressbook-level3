package seedu.revision.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.model.answerable.Answerable;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Answerable> PREDICATE_SHOW_ALL_ANSWERABLE = unused -> true;

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
     * Returns the user prefs' revision tool file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' revision tool file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces revision tool data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a answerable with the same identity as {@code answerable} exists in the revision tool.
     */
    boolean hasAnswerable(Answerable answerable);

    /**
     * Deletes the given answerable.
     * The answerable must exist in the revision tool.
     */
    void deleteAnswerable(Answerable target);

    /**
     * Adds the given answerable.
     * {@code answerable} must not already exist in the revision tool.
     */
    void addAnswerable(Answerable answerable);

    /**
     * Replaces the given answerable {@code target} with {@code editedAnswerable}.
     * {@code target} must exist in the revision tool.
     * The answerable identity of {@code editedAnswerable} must not be the same as another existing answerable in the revision tool.
     */
    void setAnswerable(Answerable target, Answerable editedAnswerable);

    /** Returns an unmodifiable view of the filtered answerable list */
    ObservableList<Answerable> getFilteredAnswerableList();

    /**
     * Updates the filter of the filtered answerable list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAnswerableList(Predicate<Answerable> predicate);
}
