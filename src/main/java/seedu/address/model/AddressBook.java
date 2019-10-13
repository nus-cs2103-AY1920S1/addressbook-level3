package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.note.Note;
import seedu.address.model.note.UniqueNoteList;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizQuestionList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameNote comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueNoteList notes;
    private final QuizQuestionList quiz;
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
        quiz = new QuizQuestionList();
        tasks = new TaskList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Notes in {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setNotes(newData.getNoteList());
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
     * Removes {@code title} from the lecture note list. This title must exist.
     */
    public void removeNote(Note title) {
        notes.remove(title);
    }

    // quiz operations

    /**
     * Sets the question list in quiz with specific {@code subject} and {@code difficulty}.
     */
    public void setQuizQuestionList(int numOfQuestions, Subject subject, Difficulty difficulty) {
        quiz.setQuizQuestionList(numOfQuestions, subject, difficulty);
    }

    // util methods

    @Override
    public String toString() {
        return notes.asUnmodifiableObservableList().size() + " lecture notes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Note> getNoteList() {
        return notes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && notes.equals(((AddressBook) other).notes));
    }

    @Override
    public int hashCode() {
        return notes.hashCode();
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

    // note-level operations

    /**
     * Returns true if a revision task with the same note / question, same date and time, and same status as {@code task} exists.
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
}
