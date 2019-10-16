package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.note.exceptions.NoteNotFoundException;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Represents a task list.
 */
public class TaskList implements Iterable<Task> {
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a task which equals with the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a revision task to the task list. The task must be different from all existing ones.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the existing revision task {@code target} in the list with {@code edited}.
     * The new task must be different from all existing ones.
     */
    public void setTask(Task target, Task edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new NoteNotFoundException();
        }

        if (!target.equals(edited) && contains(edited)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent, existing revision task from the list.
     */
    public void remove(Task toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    public Task get(int index) {
        return internalList.get(index);
    }

    public void setTasks(TaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks} containing no duplicates.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        //if (!notesAreUnique(tasks)) {
        //    throw new DuplicateTitleException();
        //}

        internalList.setAll(tasks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && internalList.equals(((TaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Marks the selected task as done.
     *
     * @param taskDone The task to be marked as done.
     */
    public void markTaskAsDone(Task taskDone) {
        requireNonNull(taskDone);

        int index = internalList.indexOf(taskDone);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        internalList.get(index).markAsDone();
    }

    public void clear() {
        internalList.clear();
    }
}
