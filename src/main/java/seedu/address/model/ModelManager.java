package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.PieChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.note.Note;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.statistics.TempStatsQnsModel;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Note> filteredNotes;
    private final FilteredList<Question> filteredQuestions;
    private final FilteredList<Question> filteredQuizQuestions;
    private final FilteredList<QuizResult> filteredQuizResults;
    private final FilteredList<Task> filteredTasks;
    private ObservableList<TempStatsQnsModel> statsQnsList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredNotes = new FilteredList<>(this.addressBook.getNoteList());
        filteredQuestions = new FilteredList<>(this.addressBook.getQuestionList());
        filteredQuizQuestions = new FilteredList<>(this.addressBook.getQuizQuestionList());
        filteredQuizResults = new FilteredList<>(this.addressBook.getQuizResultList());
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    // note
    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return addressBook.hasNote(note);
    }

    @Override
    public void deleteNote(Note target) {
        addressBook.removeNote(target);
    }

    @Override
    public void addNote(Note note) {
        addressBook.addNote(note);
        updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        addressBook.setNote(target, editedNote);
    }

    // question
    @Override
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return addressBook.hasQuestion(question);
    }

    @Override
    public void deleteQuestion(Question target) {
        addressBook.removeQuestion(target);
    }

    @Override
    public void addQuestion(Question question) {
        addressBook.addQuestion(question);
        updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
    }

    @Override
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        addressBook.setQuestion(target, editedQuestion);
    }

    @Override
    public Note getNote(Note note) {
        requireNonNull(note);
        return addressBook.getNote(note);
    }

    //quiz
    @Override
    public ObservableList<Question> getQuizQuestions(int numOfQuestions, Subject subject, Difficulty difficulty) {
        requireAllNonNull(subject, difficulty);

        return addressBook.getQuizQuestions(numOfQuestions, subject, difficulty);
    }

    @Override
    public void setQuizQuestionList(ObservableList<Question> quizQuestionList) {
        requireNonNull(quizQuestionList);

        addressBook.setQuizQuestionList(quizQuestionList);
    }

    @Override
    public Answer showQuizAnswer(int index) {
        return addressBook.showQuizAnswer(index);
    }

    @Override
    public boolean checkQuizAnswer(int index, Answer answer) {
        requireNonNull(answer);

        return addressBook.checkQuizAnswer(index, answer);
    }

    @Override
    public void addQuizResult(QuizResult quizResult) {
        requireNonNull(quizResult);

        addressBook.addQuizResult(quizResult);
    }

    @Override
    public void clearQuizQuestionList() {
        addressBook.clearQuizQuestionList();
    }

    //=========== Filtered Note List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Note}s backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return filteredNotes;
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
    }

    //=========== Filtered Question List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Question} backed by the internal list of
     * {@code versionedAddressBook}
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

    //=========== Filtered Quiz Question List Accessors =========================================================

    @Override
    public ObservableList<Question> getFilteredQuizQuestionList() {
        return filteredQuizQuestions;
    }

    @Override
    public ObservableList<QuizResult> getFilteredQuizResultList() {
        return filteredQuizResults;
    }

    //=========== Filtered Task List accessors =============================================================

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return addressBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        addressBook.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        addressBook.setTask(target, editedTask);
    }

    @Override
    public void markTaskAsDone(Task taskDone) {
        addressBook.markTaskAsDone(taskDone);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task}s backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public void clearTaskList() {
        addressBook.clearTaskList();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredNotes.equals(other.filteredNotes)
                && filteredQuestions.equals(other.filteredQuestions)
                && filteredTasks.equals(other.filteredTasks);
    }

    //=========== Statistics ===============================================================================
    /*@Override
    public void setStatistics() {
        filteredQuizResults = new FilteredList<>(this.addressBook.getQuizResultList());
    }*/

    @Override
    public ObservableList<TempStatsQnsModel> getStatsQnsList() {
        return statsQnsList;
    }

    @Override
    public int getTotalQuestionsDone() {
        return addressBook.getTotalQuestionsDone();
    }

    @Override
    public int getTotalQuestionsCorrect() {
        return addressBook.getTotalQuestionsCorrect();
    }

    @Override
    public int getTotalQuestionsIncorrect() {
        return addressBook.getTotalQuestionsIncorrect();
    }

    @Override
    public void setCorrectQnsList() {
        statsQnsList = addressBook.getCorrectQns();
    }

    @Override
    public void setIncorrectQnsList() {
        statsQnsList = addressBook.getIncorrectQns();
    }

    @Override
    public ObservableList<PieChart.Data> getStatsChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("Correct", getTotalQuestionsCorrect()));
        pieChartData.add(new PieChart.Data("Incorrect", getTotalQuestionsIncorrect()));
        return pieChartData;
    }
}
