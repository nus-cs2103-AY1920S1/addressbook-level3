package seedu.address.model.quiz;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.quiz.person.Question;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Question> PREDICATE_SHOW_ALL_QUESTIONS = unused -> true;

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
     * Returns true if a question with the same identity as {@code question} exists in the address book.
     */
    boolean hasQuestion(Question question);

    /**
     * Deletes the given question.
     * The question must exist in the address book.
     */
    void deleteQuestion(Question target);

    /**
     * Adds the given question.
     * {@code question} must not already exist in the address book.
     */
    void addQuestion(Question question);

    /**
     * Replaces the given question {@code target} with {@code editedQuestion}.
     * {@code target} must exist in the address book.
     * The question identity of {@code editedQuestion} must not be the same as
     * another existing question in the address book.
     */
    void setQuestion(Question target, Question editedQuestion);

    void setQuestionNumber(int questionNumber);

    int getQuestionNumber();

    void setShowAnswer(boolean showAnswer);

    boolean getShowAnswer();

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoQuizBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoQuizBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoQuizBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoQuizBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitQuizBook();

    /**
     * Set the question to be shown to the StackPane.
     * @param question
     */
    void setShowQuestion(Question question);

    /** Returns an unmodifiable view of the filtered question list */
    ObservableList<Question> getFilteredShowQuestionList();

    /** Returns an unmodifiable view of the filtered question list */
    ObservableList<Question> getFilteredQuestionList();

    /**
     * Updates the filter of the filtered question list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredQuestionList(Predicate<Question> predicate);
}
