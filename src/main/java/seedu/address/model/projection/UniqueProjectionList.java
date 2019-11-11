package seedu.address.model.projection;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.projection.exceptions.DuplicateProjectionException;
import seedu.address.model.projection.exceptions.ProjectionNotFoundException;

/**
 * A list of Projections that enforces uniqueness between its elements and does not allow nulls.
 * A Projection is considered unique by comparing using {@code Projection#isSameProjection(Projection)}.
 * As such, adding and updating of Projections uses Projection#isSameProjection(Projection) for equality so as
 * to ensure that the Projection being added or updated is unique in terms of identity in the UniqueProjectionList.
 * However, the removal of a Projection uses Projection#equals(Object) so
 * as to ensure that the Projection with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Projection#isSameProjection(Projection)
 */
public class UniqueProjectionList implements Iterable<Projection> {

    private final ObservableList<Projection> internalList = FXCollections.observableArrayList();
    private final ObservableList<Projection> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent projection as the given argument.
     */
    public boolean contains(Projection toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a Projection to the list.
     * The Projection must not already exist in the list.
     */
    public void add(Projection toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProjectionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent projection from the list.
     * The projection must exist in the list.
     */
    public void remove(Projection toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProjectionNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code Projections}.
     * {@code Projections} must not contain duplicate Projections.
     */
    public void setProjections(List<Projection> projections) {
        requireAllNonNull(projections);
        if (!projectionsAreUnique(projections)) {
            throw new DuplicateProjectionException();
        }

        internalList.setAll(projections);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Projection> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Projection> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProjectionList // instanceof handles nulls
                && internalList.equals(((UniqueProjectionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Projections} contains only unique Projections.
     */
    private boolean projectionsAreUnique(List<Projection> projections) {
        for (int i = 0; i < projections.size() - 1; i++) {
            for (int j = i + 1; j < projections.size(); j++) {
                if (projections.get(i).equals(projections.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
