package seedu.address.model.entity.body;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.exceptions.DuplicateBodyException;
import seedu.address.model.entity.body.exceptions.BodyNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of bodies that enforces uniqueness between its elements and does not allow nulls.
 * A body is considered unique by comparing using {@code Body#isSameBody(Body)}. As such, adding and updating of
 * bodies uses Body#isSameBody(Body) for equality so as to ensure that the body being added or updated is
 * unique in terms of identity in the UniqueBodyList. However, the removal of a body uses Body#equals(Object) so
 * as to ensure that the body with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Body#isSameBody(Object)
 */
public class UniqueBodyList implements Iterable<Body> {

    private final ObservableList<Body> internalList = FXCollections.observableArrayList();
    private final ObservableList<Body> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent body as the given argument.
     */
    public boolean contains(Body toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBody);
    }

    /**
     * Adds a body to the list.
     * The body must not already exist in the list.
     */
    public void add(Body toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBodyException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the body {@code target} in the list with {@code editedBody}.
     * {@code target} must exist in the list.
     * The body identity of {@code editedBody} must not be the same as another existing body in the list.
     */
    public void setBody(Body target, Body editedBody) {
        requireAllNonNull(target, editedBody);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BodyNotFoundException();
        }

        if (!target.isSameBody(editedBody) && contains(editedBody)) {
            throw new DuplicateBodyException();
        }

        internalList.set(index, editedBody);
    }

    /**
     * Removes the equivalent body from the list.
     * The body must exist in the list.
     */
    public void remove(Body toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BodyNotFoundException();
        }
    }

    public void setBodies(UniqueBodyList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code bodies}.
     * {@code bodies} must not contain duplicate bodies.
     */
    public void setBodies(List<Body> bodies) {
        requireAllNonNull(bodies);
        if (!bodiesAreUnique(bodies)) {
            throw new DuplicateBodyException();
        }

        internalList.setAll(bodies);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Body> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Body> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBodyList // instanceof handles nulls
                        && internalList.equals(((UniqueBodyList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code bodies} contains only unique bodies.
     */
    private boolean bodiesAreUnique(List<Body> bodies) {
        for (int i = 0; i < bodies.size() - 1; i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                if (bodies.get(i).isSameBody(bodies.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
