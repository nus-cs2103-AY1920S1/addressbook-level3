package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.note.Note;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;
import seedu.address.model.statistics.StackBarChartModel;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of application data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AppData appData;
    private final UserPrefs userPrefs;
    private final FilteredList<Note> filteredNotes;
    private final FilteredList<Question> filteredQuestions;
    private final FilteredList<Question> filteredQuizQuestions;
    private final FilteredList<QuizResult> filteredQuizResults;
    private final FilteredList<Task> filteredTasks;
    private QuizResultFilter quizResultFilter;
    private ObservableList<QuizResult> quizResults;

    /**
     * Initializes a ModelManager with the given appData and userPrefs.
     */
    public ModelManager(ReadOnlyAppData appData, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(appData, userPrefs);

        logger.fine("Initializing with application data: " + appData + " and user prefs " + userPrefs);

        this.appData = new AppData(appData);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredNotes = new FilteredList<>(this.appData.getNoteList());
        filteredQuestions = new FilteredList<>(this.appData.getQuestionList());
        filteredQuizQuestions = new FilteredList<>(this.appData.getQuizQuestionList());
        filteredQuizResults = new FilteredList<>(this.appData.getQuizResultList());
        filteredTasks = new FilteredList<>(this.appData.getTaskList());
    }

    public ModelManager() {
        this(new AppData(), new UserPrefs());
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
    public Path getAppDataFilePath() {
        return userPrefs.getAppDataFilePath();
    }

    @Override
    public void setAppDataFilePath(Path appDataFilePath) {
        requireNonNull(appDataFilePath);
        userPrefs.setAppDataFilePath(appDataFilePath);
    }

    //=========== AppData ================================================================================

    @Override
    public void setAppData(ReadOnlyAppData appData) {
        this.appData.resetData(appData);
    }

    @Override
    public ReadOnlyAppData getAppData() {
        return appData;
    }

    // note
    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return appData.hasNote(note);
    }

    @Override
    public void deleteNote(Note target) {
        appData.removeNote(target);
    }

    @Override
    public void clearNotes() {
        appData.clearNotes();
    }

    @Override
    public void addNote(Note note) {
        appData.addNote(note);
        updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        appData.setNote(target, editedNote);
    }

    // question
    @Override
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return appData.hasQuestion(question);
    }

    @Override
    public void deleteQuestion(Question target) {
        appData.removeQuestion(target);
    }

    @Override
    public void clearQuestions() {
        appData.clearQuestions();
    }

    @Override
    public void addQuestion(Question question) {
        appData.addQuestion(question);
        updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
    }

    @Override
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        appData.setQuestion(target, editedQuestion);
    }

    @Override
    public Note getNote(Note note) {
        requireNonNull(note);
        return appData.getNote(note);
    }

    //quiz
    @Override
    public ObservableList<Question> getQuizQuestions(int numOfQuestions, Subject subject, Difficulty difficulty) {
        requireAllNonNull(subject, difficulty);

        return appData.getQuizQuestions(numOfQuestions, subject, difficulty);
    }

    @Override
    public void setQuizQuestionList(ObservableList<Question> quizQuestionList) {
        requireNonNull(quizQuestionList);

        appData.setQuizQuestionList(quizQuestionList);
    }

    @Override
    public ObservableList<Question> getOneQuizQuestionAsList() {
        return appData.getOneQuizQuestionAsList();
    }

    @Override
    public Question getOneQuizQuestion() {
        return appData.getOneQuizQuestion();
    }

    @Override
    public void removeOneQuizQuestion() {
        appData.removeOneQuizQuestion();
    }

    @Override
    public int getSize() {
        return appData.getSize();
    }

    @Override
    public Answer showQuizAnswer() {
        return appData.showQuizAnswer();
    }

    @Override
    public boolean checkQuizAnswer(Answer answer) {
        requireNonNull(answer);

        return appData.checkQuizAnswer(answer);
    }

    @Override
    public void addQuizResult(QuizResult quizResult) {
        requireNonNull(quizResult);

        appData.addQuizResult(quizResult);
    }

    @Override
    public void clearQuizQuestionList() {
        appData.clearQuizQuestionList();
    }

    @Override
    public void filterQuizResult(QuizResultFilter quizResultFilter) throws EmptyQuizResultListException {
        this.quizResults = appData.filterQuizResult(quizResultFilter);
        if (quizResults.isEmpty()) {
            throw new EmptyQuizResultListException();
        }
    }

    private ObservableList<QuizResult> filterQuizResultAndReturn(QuizResultFilter quizResultFilter) {
        return appData.filterQuizResult(quizResultFilter);
    }

    @Override
    public void updateQuizResultFilter(QuizResultFilter quizResultFilter) throws EmptyQuizResultListException {
        if (filteredQuizResults.isEmpty() || appData.filterQuizResult(quizResultFilter).isEmpty()) {
            throw new EmptyQuizResultListException();
        }
        this.quizResultFilter = quizResultFilter;
    }

    //=========== Filtered Note List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Note}s backed by the internal list of
     * {@code versionedAppData}
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
     * {@code versionedAppData}
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
        return appData.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        appData.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        appData.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        appData.setTask(target, editedTask);
    }

    @Override
    public void markTaskAsDone(Task taskDone) {
        appData.markTaskAsDone(taskDone);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task}s backed by the internal list of
     * {@code versionedAppData}
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
        appData.clearTaskList();
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
        return appData.equals(other.appData)
                && userPrefs.equals(other.userPrefs)
                && filteredNotes.equals(other.filteredNotes)
                && filteredQuestions.equals(other.filteredQuestions)
                && filteredTasks.equals(other.filteredTasks);
    }

    //=========== Statistics ===============================================================================
    @Override
    public ObservableList<QuizResult> getQuizResultList() {
        return quizResults;
    }

    @Override
    public int getTotalQuestionsDone() {
        return quizResults.size();
    }

    @Override
    public void generateQnsReport(Question question) throws EmptyQuizResultListException {
        quizResults = appData.getQnsReport(question);
        if (quizResults.isEmpty()) {
            throw new EmptyQuizResultListException();
        }
    }

    private int getTotalQuestionsCorrect() {
        int totalCorrectQns = 0;
        for (QuizResult q : quizResults) {
            if (q.getResult()) {
                totalCorrectQns++;
            }
        }
        return totalCorrectQns;
    }

    private int getTotalQuestionsIncorrect() {
        int totalIncorrectQns = 0;
        for (QuizResult q : quizResults) {
            if (!q.getResult()) {
                totalIncorrectQns++;
            }
        }
        return totalIncorrectQns;
    }

    @Override
    public ObservableList<PieChart.Data> getStatsPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("Correct", getTotalQuestionsCorrect()));
        pieChartData.add(new PieChart.Data("Incorrect", getTotalQuestionsIncorrect()));
        return pieChartData;
    }

    @Override
    public ObservableList<PieChart.Data> getQnsPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        List<Difficulty> uniqueDifficultyList = appData.getUniqueDifficultyList();
        for (Difficulty d : uniqueDifficultyList) {
            quizResultFilter.setOperation(d);
            ObservableList<QuizResult> results = filterQuizResultAndReturn(quizResultFilter);
            if (results.size() > 0) {
                pieChartData.add(new PieChart.Data(d.difficulty, results.size()));
            }
        }
        return pieChartData;
    }

    @Override
    public ObservableList<Subject> getUniqueSubjectList() {
        return appData.getUniqueSubjectList();
    }

    @Override
    public ObservableList<StackBarChartModel> getStackBarChartData() {
        List<Difficulty> uniqueDifficultyList = appData.getUniqueDifficultyList();
        List<Subject> uniqueSubjectList = getUniqueSubjectList();
        List<StackBarChartModel> barChartData = new ArrayList<>();

        for (Difficulty d : uniqueDifficultyList) {
            List<XYChart.Data<String, Number>> dataListPerDifficulty = new ArrayList<>();
            for (Subject s : uniqueSubjectList) {
                quizResultFilter.setOperation(s, d);
                int n = filterQuizResultAndReturn(quizResultFilter).size();
                dataListPerDifficulty.add(new XYChart.Data<>(s.toString(), n));
            }
            barChartData.add(new StackBarChartModel(d, dataListPerDifficulty));
        }
        return FXCollections.observableArrayList(barChartData);
    }
}
