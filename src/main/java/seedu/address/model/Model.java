package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.note.Note;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.statistics.StackBarChartModel;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    Predicate<Note> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    Predicate<Task> PREDICATE_SHOW_NO_TASKS = task -> false;

    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    Predicate<Task> PREDICATE_SHOW_DONE_TASKS = Task::getStatus;

    Predicate<Task> PREDICATE_SHOW_NOT_DONE_TASKS = task -> !task.getStatus();

    Predicate<Task> PREDICATE_SHOW_OVERDUE_TASKS = task -> {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime taskDateTime = LocalDateTime.of(task.getDate(), task.getTime());
        return !task.getStatus() && taskDateTime.isBefore(now);
    };

    /**
     * {@code Predicate} that always evaluate to true
     */
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
     * Returns the user prefs' application data file path.
     */
    Path getAppDataFilePath();

    /**
     * Sets the user prefs' application data file path.
     */
    void setAppDataFilePath(Path appDataFilePath);

    /**
     * Replaces current application data with the data in {@code appData}.
     */
    void setAppData(ReadOnlyAppData appData);

    /**
     * Returns the AppData
     */
    ReadOnlyAppData getAppData();

    /**
     * Returns true if a note with the same identity as {@code note} exists in the current application data.
     */
    boolean hasNote(Note note);

    /**
     * Deletes the given existing lecture note.
     */
    void deleteNote(Note target);

    /**
     * Clears all lecture notes.
     */
    void clearNotes();

    /**
     * Adds the given (not yet existing) lecture note.
     */
    void addNote(Note note);

    /**
     * Replaces the given lecture note {@code target} with {@code editedNote}.
     * {@code target} must exist, while the title of {@code editedNote} must not be the same as another
     * existing lecture note.
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

    /**
     * Returns true if a task with the same identity as {@code task} exists in the current application data.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given existing task.
     */
    void deleteTask(Task target);

    /**
     * Adds the given (not yet existing) task.
     */
    void addTask(Task task);

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    Note getNote(Note note);

    /**
     * Returns true if a question with the same identity as {@code question} exists in NUStudy.
     */
    boolean hasQuestion(Question question);

    /**
     * Adds the given question.
     * {@code question} must not already exist in NUStudy.
     */
    void addQuestion(Question question);

    /**
     * Deletes the given question.
     * The question must exist in NUStudy.
     */
    void deleteQuestion(Question target);

    /**
     * Clears all questions.
     */
    void clearQuestions();

    /**
     * Replaces the given question {@code target} with {@code editedQuestion}.
     * {@code target} must exist in NUStudy.
     * The question body of {@code editedQuestion} must not be the same as another existing question in NUStudy.
     */
    void setQuestion(Question target, Question editedQuestion);

    /**
     * Returns an unmodifiable view of the filtered question list
     */
    ObservableList<Question> getFilteredQuestionList();

    /**
     * Updates the filter of the filtered question list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredQuestionList(Predicate<Question> predicate);

    /**
     * Returns a question list with specific {@code numOfQuestions}, {@code subject} and {@code difficulty}.
     */
    ObservableList<Question> getQuizQuestions(int numOfQuestions, Subject subject, Difficulty difficulty);

    /**
     * Sets the question list in quiz as {@code quizQuestionList}.
     */
    void setQuizQuestionList(ObservableList<Question> quizQuestionList);

    /**
     * Gets one question from the list and return a new list contains this question.
     */
    ObservableList<Question> getOneQuizQuestionAsList();

    /**
     * Return the number of remaining quiz questions.
     */
    int getSize();

    Question getOneQuizQuestion();

    void removeOneQuizQuestion();

    void setTask(Task target, Task editedTask);

    /**
     * Returns an unmodifiable view of the filtered quiz question list.
     */
    ObservableList<Question> getFilteredQuizQuestionList();

    /**
     * Filters the quiz result list with {@code QuizResultFilter}.
     * @param quizResultFilter The filter to be applied.
     */
    void filterQuizResult(QuizResultFilter quizResultFilter);

    /**
     * Updates the existing {@code QuizResultFilter}.
     * @param quizResultFilter The filter to update with.
     */
    void updateQuizResultFilter(QuizResultFilter quizResultFilter);

    /**
     * Returns an unmodifiable view of the filtered quiz result list.
     */
    ObservableList<QuizResult> getFilteredQuizResultList();

    /**
     * Checks the an answer input by user and return the boolean value as the result.
     */
    boolean checkQuizAnswer(Answer answer);

    /**
     * Adds the quiz result to the quiz result list.
     * @param quizResult The quiz result to be added.
     */
    void addQuizResult(QuizResult quizResult);

    /**
     * Clears the quiz question list.
     */
    void clearQuizQuestionList();

    void markTaskAsDone(Task taskDone);

    void clearTaskList();

    /**
     * Returns an answer for question in quiz with specific {@code index}.
     */
    Answer showQuizAnswer();

    /**
     * Returns the total number of questions answered.
     */
    int getTotalQuestionsDone();

    /**
     * Returns an unmodifiable view of the pie chart data.
     */
    ObservableList<PieChart.Data> getStatsPieChartData();

    /**
     * Returns an unmodifiable view of the quiz result list.
     */
    ObservableList<QuizResult> getQuizResultList();

    /**
     * Filters the quiz result list by the question.
     * @param question The question to filter by.
     */
    void generateQnsReport(Question question);

    /**
     * Returns an unmodifiable view of a list of unique subjects.
     */
    ObservableList<Subject> getUniqueSubjectList();

    /**
     * Returns an unmodifiable view of the series list of a bar chart.
     */
    ObservableList<StackBarChartModel> getStackBarChartData();

    /**
     * Returns an unmodifiable view of the data list for a pie chart.
     */
    ObservableList<PieChart.Data> getQnsPieChartData();
}
