package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.note.Note;
import seedu.address.model.note.UniqueNoteList;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.question.UniqueQuestionList;
import seedu.address.model.quiz.QuizQuestionList;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.quiz.QuizResultList;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Wraps all application data. Duplicates of lecture notes, questions, tasks, etc. are not allowed by key comparisons.
 */
public class AppData implements ReadOnlyAppData {

    private final UniqueNoteList notes;
    private final UniqueQuestionList questions;
    private final QuizQuestionList quiz;
    private final QuizResultList quizResults;
    private final TaskList tasks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */

    {
        notes = new UniqueNoteList();
        questions = new UniqueQuestionList();
        quiz = new QuizQuestionList();
        quizResults = new QuizResultList();
        tasks = new TaskList();
    }

    public AppData() {
    }

    /**
     * Creates an AppData using the Notes in {@code toBeCopied}.
     */
    public AppData(ReadOnlyAppData toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the note list with {@code notes}.
     * {@code notes} must not contain duplicate titles.
     */
    public void setNotes(List<Note> notes) {
        this.notes.setNotes(notes);
    }

    /**
     * Replaces the contents of the question list with {@code questions}.
     * {@code questions} must not contain duplicate questions.
     */
    public void setQuestions(List<Question> questions) {
        this.questions.setQuestions(questions);
    }

    /**
     * Resets the existing data of this {@code AppData} with {@code newData}.
     */
    public void resetData(ReadOnlyAppData newData) {
        requireNonNull(newData);

        setNotes(newData.getNoteList());
        setQuestions(newData.getQuestionList());
        setQuizResults(newData.getQuizResultList());
        setTasks(newData.getTaskList());
    }

    // note-level operations

    /**
     * Returns true if a lecture note with the same title as {@code note} exists.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a lecture note; its title must not already exist.
     */
    public void addNote(Note p) {
        notes.add(p);
    }

    /**
     * Replaces the given lecture note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist beforehand and titles must remain unique.
     */
    public void setNote(Note target, Note editedNote) {
        requireNonNull(editedNote);

        notes.setNote(target, editedNote);
    }

    /**
     * Retrieves {@code title} from the note list. The note must exists.
     *
     * @param title The note with the same title to be retrieved.
     * @return The note with the same title as specified in input.
     */
    public Note getNote(Note title) {
        return notes.get(title);
    }

    /**
     * Removes {@code title} from the lecture note list. This title must exist.
     */
    public void removeNote(Note title) {
        notes.remove(title);
    }

    /**
     * Clears all lecture notes.
     */
    public void clearNotes() {
        notes.setNotes(new UniqueNoteList());
    }

    //// question operations

    /**
     * Returns true if a question with the same identity as {@code question} exists in this set
     * of application data.
     */
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return questions.contains(question);
    }

    /**
     * Adds a question to NUStudy.
     * The question must not already exist in NUStudy.
     */
    public void addQuestion(Question q) {
        questions.add(q);
    }

    /**
     * Replaces the given question {@code target} in the list with {@code editedQuestion}.
     * {@code target} must exist in NUStudy.
     * The question body of {@code editedQuestion} must not be the same as another existing question in NUStudy.
     */
    public void setQuestion(Question target, Question editedQuestion) {
        requireNonNull(editedQuestion);

        questions.setQuestion(target, editedQuestion);
    }

    /**
     * Removes {@code key} from this {@code AppData}.
     * {@code key} must exist in the application data.
     */
    public void removeQuestion(Question key) {
        questions.remove(key);
    }

    /**
     * Clears all questions.
     */
    public void clearQuestions() {
        questions.setQuestions(new UniqueQuestionList());
    }

    // quiz operations

    /**
     * Returns a list with questions for quiz with {@code numQuestions}, {@code subject} and {@code difficulty}.
     */
    public ObservableList<Question> getQuizQuestions(int numQuestions, Subject subject, Difficulty difficulty) {
        List<Question> filteredQuestions = getQuestionList()
                .stream()
                .filter(question -> subject.subject.equalsIgnoreCase(question.getSubject().subject)
                        && difficulty.difficulty.equalsIgnoreCase(question.getDifficulty().difficulty))
                .collect(Collectors.toList());
        if (filteredQuestions.size() < numQuestions) {
            throw new IllegalArgumentException("(you have " + filteredQuestions.size() + ")");
        }
        Collections.shuffle(filteredQuestions);
        return FXCollections.observableList(filteredQuestions.subList(0, numQuestions));
    }

    /**
     * Sets the question list in quiz as {@code quizQuestionList}.
     */
    public void setQuizQuestionList(ObservableList<Question> quizQuestionList) {
        quiz.setQuizQuestionList(quizQuestionList);
    }

    /**
     * Gets one question from the list and return a new list contains this question.
     */
    public ObservableList<Question> getOneQuizQuestionAsList() {
        return quiz.getOneQuizQuestionAsList();
    }

    public Question getOneQuizQuestion() {
        return quiz.getOneQuizQuestion();
    }

    public void removeOneQuizQuestion() {
        quiz.removeOneQuizQuestion();
    }

    public int getSize() {
        return quiz.getSize();
    }

    /**
     * Returns an answer for the question in quiz with specific {@code index}.
     */
    public Answer showQuizAnswer() {
        return quiz.showAnswer();
    }

    /**
     * Checks the answer input by user and return a boolean value as the result.
     */
    public boolean checkQuizAnswer(Answer answer) {
        return quiz.checkQuizAnswer(answer);
    }

    /**
     * Clears the quiz question list.
     */
    public void clearQuizQuestionList() {
        quiz.clearQuizQuestionList();
    }

    // Quiz Result operations
    public void setQuizResults(List<QuizResult> quizResults) {
        this.quizResults.setQuizResults(quizResults);
    }

    public void addQuizResult(QuizResult quizResult) {
        quizResults.add(quizResult);
    }

    public boolean hasQuizResult(QuizResult quizResult) {
        return quizResults.contains(quizResult);
    }

    public ObservableList<QuizResult> filterQuizResult(QuizResultFilter quizResultFilter)
            throws EmptyQuizResultListException {
        return quizResults.filterQuizResult(quizResultFilter);
    }

    public ObservableList<Subject> getUniqueSubjectList() {
        return quizResults.getUniqueSubjectList();
    }

    public ObservableList<Difficulty> getUniqueDifficultyList() {
        return quizResults.getUniqueDifficultyList();
    }

    public ObservableList<QuizResult> getQnsReport(Question question) {
        return quizResults.getQnsReport(question);
    }

    // util methods

    @Override
    public String toString() {
        return notes.asUnmodifiableObservableList().size() + " lecture notes, "
                + questions.asUnmodifiableObservableList().size() + " questions, "
                + tasks.asUnmodifiableObservableList().size() + " tasks";
    }

    @Override
    public ObservableList<Note> getNoteList() {
        return notes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Question> getQuestionList() {
        return questions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Question> getQuizQuestionList() {
        return quiz.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<QuizResult> getQuizResultList() {
        return quizResults.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppData // instanceof handles nulls
                && notes.equals(((AppData) other).notes)
                && questions.equals(((AppData) other).questions)
                && quizResults.equals(((AppData) other).quizResults)
                && tasks.equals(((AppData) other).tasks));
    }

    @Override
    public int hashCode() {
        return Objects.hash(notes, quiz, questions, tasks);
    }

    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }


    /**
     * Returns true if a revision task with the same note / question, same date and time, and same status
     * as {@code task} exists.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a revision task.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist beforehand.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code toRemove} from the revision task list. This task must exist.
     */
    public void removeTask(Task toRemove) {
        tasks.remove(toRemove);
    }

    /**
     * Marks a given task {@code taskDone} as done. The task must exist.
     */
    public void markTaskAsDone(Task taskDone) {
        tasks.markTaskAsDone(taskDone);
    }

    /**
     * Clears all tasks in the task list.
     */
    public void clearTaskList() {
        tasks.clear();
    }
}
