package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.quiz.person.Question;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelQuizManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelQuizManager.class);

    private final VersionedQuizBook versionedQuizBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Question> filteredQuestions;
    private int showQuestionNumber;
    private boolean showAnswer;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelQuizManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.showAnswer = true;
        versionedQuizBook = new VersionedQuizBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredQuestions = new FilteredList<>(versionedQuizBook.getQuestionList());
    }

    public ModelQuizManager() {
        this(new AddressQuizBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.versionedQuizBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedQuizBook;
    }

    @Override
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return versionedQuizBook.hasQuestion(question);
    }

    @Override
    public void deleteQuestion(Question target) {
        versionedQuizBook.removeQuestion(target);
    }

    @Override
    public void addQuestion(Question question) {
        versionedQuizBook.addQuestion(question);
        updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
    }

    @Override
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        versionedQuizBook.setQuestion(target, editedQuestion);
    }

    @Override
    public void setShowQuestion(Question question) {
        versionedQuizBook.setShowQuestion(question);
    }

    @Override
    public void setQuestionNumber(int questionNumber) {
        showQuestionNumber = questionNumber;
    }

    public int getQuestionNumber() {
        return showQuestionNumber;
    }

    public void setShowAnswer(boolean showAnswer) {
        this.showAnswer = showAnswer;
    }

    public boolean getShowAnswer() {
        return showAnswer;
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoQuizBook() {
        return versionedQuizBook.canUndo();
    }

    @Override
    public boolean canRedoQuizBook() {
        return versionedQuizBook.canRedo();
    }

    @Override
    public void undoQuizBook() {
        versionedQuizBook.undo();
    }

    @Override
    public void redoQuizBook() {
        versionedQuizBook.redo();
    }

    @Override
    public void commitQuizBook() {
        versionedQuizBook.commit();
    }

    //=========== Filtered Question List Accessors =============================================================

    @Override
    public ObservableList<Question> getFilteredShowQuestionList() {
        return versionedQuizBook.getShowQuestionList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Question} backed by the internal list of
     * {@code versionedQuizBook}
     */
    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return filteredQuestions;
    }

    @Override
    public void updateFilteredQuestionList(Predicate<Question> predicate) {
        requireNonNull(predicate);
        filteredQuestions.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelQuizManager)) {
            return false;
        }

        // state check
        ModelQuizManager other = (ModelQuizManager) obj;
        return versionedQuizBook.equals(other.versionedQuizBook)
                && userPrefs.equals(other.userPrefs)
                && filteredQuestions.equals(other.filteredQuestions);
    }

}
