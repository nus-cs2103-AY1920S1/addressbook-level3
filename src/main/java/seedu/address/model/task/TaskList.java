package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.note.exceptions.NoteNotFoundException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

public class TaskList implements Iterable<Task> {
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a task with notes sharing title with the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a lecture note to the list. The title must be different from all existing ones.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
//        if (contains(toAdd)) {
//            throw new DuplicateTitleException();
//        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the existing lecture note {@code target} in the list with {@code edited}.
     * The new title must be different from all existing ones.
     */
    public void setTask(Task target, Task edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new NoteNotFoundException();
        }

//        if (!target.isSameNote(edited) && contains(edited)) {
//            throw new DuplicateTitleException();
//        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent, existing lecture note from the list.
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
     * Replaces the contents of this list with {@code notes} containing no duplicates.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
//        if (!notesAreUnique(tasks)) {
//            throw new DuplicateTitleException();
//        }

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

//    /**
//     * Returns true if {@code note} contains only unique notes.
//     */
//    private boolean notesAreUnique(List<Note> notes) {
//        for (int i = 0; i < notes.size() - 1; i++) {
//            for (int j = i + 1; j < notes.size(); j++) {
//                if (notes.get(i).isSameNote(notes.get(j))) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

}
