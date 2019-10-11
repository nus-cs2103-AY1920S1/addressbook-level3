package seedu.address.model.visittask;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.visittask.exceptions.DuplicateVisitTaskException;
import seedu.address.model.visittask.exceptions.VisitTaskNotFoundException;

/**
 * A list of visitTasks that enforces uniqueness between its elements and does not allow nulls.
 * A visit task is considered unique by comparing using {@code VisitTask#isSameVisitTask(Visit)}.
 * As such, adding and updating of visitTasks uses VisitTask#isSameVisitTask(Visit) for
 * equality so as to ensure that the visit task being added or updated is unique in terms of identity
 * in the UniqueVisitList. However, the removal of a visit task uses VisitTask#equals(Object) so
 * as to ensure that the visit task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see VisitTask#isSameVisitTask(VisitTask)
 */
public class UniqueVisitTaskList implements Iterable<VisitTask> {

    private final ObservableList<VisitTask> internalList = FXCollections.observableArrayList();
    private final ObservableList<VisitTask> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent visit task as the given argument.
     */
    public boolean contains(VisitTask toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameVisitTask);
    }

    /**
     * Adds a visit task to the list.
     * The visit task must not already exist in the list.
     */
    public void add(VisitTask toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVisitTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the visit task {@code target} in the list with {@code editedVisitTask}.
     * {@code target} must exist in the list.
     * The visit task identity of {@code editedVisitTask} must not be the same as another existing visit task in the list.
     */
    public void setVisit(VisitTask target, VisitTask editedVisitTask) {
        requireAllNonNull(target, editedVisitTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new VisitTaskNotFoundException();
        }

        if (!target.isSameVisitTask(editedVisitTask) && contains(editedVisitTask)) {
            throw new DuplicateVisitTaskException();
        }

        internalList.set(index, editedVisitTask);
    }

    /**
     * Removes the equivalent visit task from the list.
     * The visit task must exist in the list.
     */
    public void remove(VisitTask toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new VisitTaskNotFoundException();
        }
    }

    public void setVisits(UniqueVisitTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code visitTasks}.
     * {@code visitTasks} must not contain duplicate visitTasks.
     */
    public void setVisits(List<VisitTask> visitTasks) {
        requireAllNonNull(visitTasks);
        if (!visitTasksAreUnique(visitTasks)) {
            throw new DuplicateVisitTaskException();
        }

        internalList.setAll(visitTasks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<VisitTask> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<VisitTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueVisitTaskList // instanceof handles nulls
                        && internalList.equals(((UniqueVisitTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code visitTasks} contains only unique visitTasks.
     */
    private boolean visitTasksAreUnique(List<VisitTask> visitTasks) {
        for (int i = 0; i < visitTasks.size() - 1; i++) {
            for (int j = i + 1; j < visitTasks.size(); j++) {
                if (visitTasks.get(i).isSameVisitTask(visitTasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
