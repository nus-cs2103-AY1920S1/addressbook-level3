package seedu.revision.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;

import seedu.revision.commons.core.GuiSettings;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.quiz.Statistics;

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

    /**
     * Returns the History.
     *
     * @see seedu.revision.model.Model#getHistory()
     */
    ReadOnlyHistory getHistory();

    /** Returns an unmodifiable view of the filtered list of answerables */
    ObservableList<Answerable> getFilteredAnswerableList();

    /** Returns an unmodifiable view of the list of statistics */
    ObservableList<Statistics> getStatisticsList();

    /** Returns an unmodifiable view of the filtered and sorted list of answerables */
    ObservableList<Answerable> getFilteredSortedAnswerableList();

    /** Removes the filters from answerable list and restores the list to its original size. **/
    void removeFiltersFromAnswerableList();

    /**
     * Returns the user prefs' revision tool file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' revision tool file path.
     */
    Path getHistoryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
