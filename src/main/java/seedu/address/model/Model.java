package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.note.Note;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Note> PREDICATE_SHOW_ALL_NOTES = unused -> true;
    /** {@code Predicate} that always evaluate to false */
    Predicate<Note> PREDICATE_SHOW_NO_NOTES = unused -> false;

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

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a note with the same identity as {@code note} exists in the address book.
     */
    boolean hasNote(Note note);

    /**
     * Deletes the given existing lecture note.
     */
    void deleteNote(Note target);

    /**
     * Adds the given (not yet existing) lecture note
     */
    void addNote(Note note);

    /**
     * Replaces the given lecture note {@code target} with {@code editedNote}.
     * {@code target} must exist in the address book.
     * The title of {@code editedNote} must not be the same as another existing lecture note in the address book.
     */
    void setNote(Note target, Note editedNote);

    /**
     * Returns an unmodifiable view of the filtered note list
     */
    ObservableList<Note> getFilteredNoteList();

    /**
     * Updates the filter of the filtered note list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredNoteList(Predicate<Note> predicate);

    void setStatistics();

    ObservableList<PieChart.Data> getStatsChartData();

    /**
     * Sets the question list in quiz with specific {@code subject} and {@code difficulty}.
     */
    void setQuizQuestionList(int numOfQuestions, Subject subject, Difficulty difficulty);

    /**
     * Checks the an answer input by user and return the boolean value as the result.
     */
    boolean checkQuizAnswer(int index, Answer answer);

    /**
     * Clears the quiz question list.
     */
    void clearQuizQuestionList();
}
