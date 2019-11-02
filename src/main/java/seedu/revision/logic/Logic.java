package seedu.revision.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;

import seedu.revision.commons.core.GuiSettings;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.answerable.Answerable;

/**
 * API of the MainLogic component
 */
public interface Logic {

    /**
     * Returns the AddressBook.
     *
     * @see seedu.revision.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of answerables */
    ObservableList<Answerable> getFilteredAnswerableList();

    /** Returns an unmodifiable view of the filtered and sorted list of answerables */
    ObservableList<Answerable> getFilteredSortedAnswerableList();

    /**
     * Returns the user prefs' revision tool file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
