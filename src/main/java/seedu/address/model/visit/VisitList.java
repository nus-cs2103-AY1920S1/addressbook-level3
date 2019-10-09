package seedu.address.model.visit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.visit.exceptions.DuplicateVisitException;
import seedu.address.model.visit.exceptions.VisitNotFoundException;

/**
 * A list of visits that enforces uniqueness between its elements and does not allow nulls.
 * A visit is considered unique by comparing using {@code Visit#isSameVisit(Visit)}. As such, adding and updating of
 * visits uses Visit#isSameVisit(Visit) for equality so as to ensure that the visit being added or updated is
 * unique in terms of identity in the UniqueVisitList. However, the removal of a visit uses Visit#equals(Object) so
 * as to ensure that the visit with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Visit#isSameVisit(Visit)
 */
public class VisitList implements Iterable<Visit> {

    private final ObservableList<Visit> internalList = FXCollections.observableArrayList();
    private final ObservableList<Visit> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent visit as the given argument.
     */
    public boolean contains(Visit toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameVisit);
    }

    /**
     * Adds a visit to the list.
     * The visit must not already exist in the list.
     */
    public void add(Visit toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVisitException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the visit {@code target} in the list with {@code editedVisit}.
     * {@code target} must exist in the list.
     * The visit identity of {@code editedVisit} must not be the same as another existing visit in the list.
     */
    public void setVisit(Visit target, Visit editedVisit) {
        requireAllNonNull(target, editedVisit);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new VisitNotFoundException();
        }

        if (!target.isSameVisit(editedVisit) && contains(editedVisit)) {
            throw new DuplicateVisitException();
        }

        internalList.set(index, editedVisit);
    }

    /**
     * Removes the equivalent visit from the list.
     * The visit must exist in the list.
     */
    public void remove(Visit toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new VisitNotFoundException();
        }
    }

    public void setVisits(VisitList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code visits}.
     * {@code visits} must not contain duplicate visits.
     */
    public void setVisits(List<Visit> visits) {
        requireAllNonNull(visits);
        if (!visitsAreUnique(visits)) {
            throw new DuplicateVisitException();
        }

        internalList.setAll(visits);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Visit> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Visit> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitList // instanceof handles nulls
                        && internalList.equals(((VisitList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code visits} contains only unique visits.
     */
    private boolean visitsAreUnique(List<Visit> visits) {
        for (int i = 0; i < visits.size() - 1; i++) {
            for (int j = i + 1; j < visits.size(); j++) {
                if (visits.get(i).isSameVisit(visits.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
