package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.ReadOnlyUserPrefs;
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
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAppDataFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppDataFilePath(Path appDataFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addNote(Note note) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTask(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppData(ReadOnlyAppData newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAppData getAppData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasNote(Note note) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteNote(Note target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearNotes() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasQuestion(Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Note getNote(Note note) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addQuestion(Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkQuizAnswer(Answer answer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteQuestion(Question target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setQuestion(Question target, Question editedQuestion) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredQuestionList(Predicate<Question> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearQuizQuestionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getQuizQuestions(int numOfQuestions, Subject subject, Difficulty difficulty) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void markTaskAsDone(Task taskDone) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setQuizQuestionList(ObservableList<Question> quizQuestionList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getOneQuizQuestionAsList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Question getOneQuizQuestion() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeOneQuizQuestion() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getSize() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getFilteredQuizQuestionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void filterQuizResult(QuizResultFilter quizResultFilter) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateQuizResultFilter(QuizResultFilter quizResultFilter) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Answer showQuizAnswer() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addQuizResult(QuizResult quizResult) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<QuizResult> getFilteredQuizResultList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getTotalQuestionsDone() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<PieChart.Data> getStatsPieChartData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<QuizResult> getQuizResultList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void generateQnsReport(Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Subject> getUniqueSubjectList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<StackBarChartModel> getStackBarChartData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<PieChart.Data> getQnsPieChartData() {
        throw new AssertionError("This method should not be called.");
    }
}
