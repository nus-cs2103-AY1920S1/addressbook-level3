package seedu.address.model.visittodoitem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.visittodoitem.exceptions.DuplicateVisitTodoItemException;
import seedu.address.model.visittodoitem.exceptions.VisitTodoItemNotFoundException;

/**
 * A list of visits that enforces uniqueness between its elements and does not allow nulls.
 * A visit is considered unique by comparing using {@code Visit#isSameVisitTodoItem(Visit)}. As such, adding and updating of
 * visits uses Visit#isSameVisitTodoItem(Visit) for equality so as to ensure that the visit being added or updated is
 * unique in terms of identity in the UniqueVisitList. However, the removal of a visit uses Visit#equals(Object) so
 * as to ensure that the visit with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see VisitTodoItem#isSameVisitTodoItem(VisitTodoItem)
 */
public class VisitTodoItemList implements Iterable<VisitTodoItem> {

    private final ObservableList<VisitTodoItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<VisitTodoItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent visit as the given argument.
     */
    public boolean contains(VisitTodoItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameVisitTodoItem);
    }

    /**
     * Adds a visit to the list.
     * The visit must not already exist in the list.
     */
    public void add(VisitTodoItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVisitTodoItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the visit {@code target} in the list with {@code editedVisit}.
     * {@code target} must exist in the list.
     * The visit identity of {@code editedVisit} must not be the same as another existing visit in the list.
     */
    public void setVisit(VisitTodoItem target, VisitTodoItem editedVisitTodoItem) {
        requireAllNonNull(target, editedVisitTodoItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new VisitTodoItemNotFoundException();
        }

        if (!target.isSameVisitTodoItem(editedVisitTodoItem) && contains(editedVisitTodoItem)) {
            throw new DuplicateVisitTodoItemException();
        }

        internalList.set(index, editedVisitTodoItem);
    }

    /**
     * Removes the equivalent visit from the list.
     * The visit must exist in the list.
     */
    public void remove(VisitTodoItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new VisitTodoItemNotFoundException();
        }
    }

    public void setVisits(VisitTodoItemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code visits}.
     * {@code visits} must not contain duplicate visits.
     */
    public void setVisits(List<VisitTodoItem> visitTodoItems) {
        requireAllNonNull(visitTodoItems);
        if (!visitsAreUnique(visitTodoItems)) {
            throw new DuplicateVisitTodoItemException();
        }

        internalList.setAll(visitTodoItems);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<VisitTodoItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<VisitTodoItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitTodoItemList // instanceof handles nulls
                        && internalList.equals(((VisitTodoItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code visits} contains only unique visits.
     */
    private boolean visitsAreUnique(List<VisitTodoItem> visitTodoItems) {
        for (int i = 0; i < visitTodoItems.size() - 1; i++) {
            for (int j = i + 1; j < visitTodoItems.size(); j++) {
                if (visitTodoItems.get(i).isSameVisitTodoItem(visitTodoItems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
