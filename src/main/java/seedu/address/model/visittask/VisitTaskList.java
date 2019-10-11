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
 * A list of visits that enforces uniqueness between its elements and does not allow nulls.
 * A visit is considered unique by comparing using {@code Visit#isSameVisitTask(Visit)}.
 * As such, adding and updating of visits uses Visit#isSameVisitTask(Visit) for
 * equality so as to ensure that the visit being added or updated is unique in terms of identity
 * in the UniqueVisitList. However, the removal of a visit uses Visit#equals(Object) so
 * as to ensure that the visit with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see VisitTask#isSameVisitTask(VisitTask)
 */
public class VisitTaskList implements Iterable<VisitTask> {

    private final ObservableList<VisitTask> internalList = FXCollections.observableArrayList();
    private final ObservableList<VisitTask> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent visit as the given argument.
     */
    public boolean contains(VisitTask toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameVisitTask);
    }

    /**
     * Adds a visit to the list.
     * The visit must not already exist in the list.
     */
    public void add(VisitTask toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVisitTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the visit {@code target} in the list with {@code editedVisit}.
     * {@code target} must exist in the list.
     * The visit identity of {@code editedVisit} must not be the same as another existing visit in the list.
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
     * Removes the equivalent visit from the list.
     * The visit must exist in the list.
     */
    public void remove(VisitTask toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new VisitTaskNotFoundException();
        }
    }

    public void setVisits(VisitTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code visits}.
     * {@code visits} must not contain duplicate visits.
     */
    public void setVisits(List<VisitTask> visitTasks) {
        requireAllNonNull(visitTasks);
        if (!visitsAreUnique(visitTasks)) {
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
                || (other instanceof VisitTaskList // instanceof handles nulls
                        && internalList.equals(((VisitTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code visits} contains only unique visits.
     */
    private boolean visitsAreUnique(List<VisitTask> visitTasks) {
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
