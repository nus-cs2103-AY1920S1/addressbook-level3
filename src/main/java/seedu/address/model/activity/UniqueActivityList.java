package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A contacts is considered unique by comparing using {@code Accommodation#isSamePerson(Accommodation)}. As such,
 * adding and updating of persons uses Accommodation#isSamePerson(Accommodation) for equality so as to ensure
 * that the contacts being added or updated is unique in terms of identity in the UniqueAccommodationList.
 * However, the removal of a contacts uses Accommodation#equals(Object) so as to ensure that the contacts with
 * exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see seedu.address.model.activity.Activity#isSameActivity(seedu.address.model.activity.Activity)
 */
public class UniqueActivityList implements Iterable<seedu.address.model.activity.Activity> {

    private final ObservableList<seedu.address.model.activity.Activity>
            internalList = FXCollections.observableArrayList();
    private final ObservableList<seedu.address.model.activity.Activity> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent contacts as the given argument.
     */
    public boolean contains(seedu.address.model.activity.Activity toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameActivity);
    }

    /**
     * Adds a contacts to the list.
     * The contacts must not already exist in the list.
     */
    public void add(seedu.address.model.activity.Activity toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
    }

    public void setActivities(seedu.address.model.activity.UniqueActivityList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setActivities(List<seedu.address.model.activity.Activity> activities) {
        requireAllNonNull(activities);
        if (!activitiesAreUnique(activities)) {
            throw new DuplicateActivityException();
        }

        internalList.setAll(activities);
    }

    /**
     * Replaces the contacts {@code target} in the list with {@code editedAccommodation}.
     * {@code target} must exist in the list.
     * The contacts identity of {@code editedAccommodation} must not be the same as another existing contacts in the
     * list.
     */
    public void setActivity(seedu.address.model.activity.Activity target,
                            seedu.address.model.activity.Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        if (!target.isSameActivity(editedActivity) && contains(editedActivity)) {
            throw new DuplicateActivityException();
        }

        internalList.set(index, editedActivity);
    }

    /**
     * Removes the equivalent contacts from the list.
     * The contacts must exist in the list.
     */
    public void remove(seedu.address.model.activity.Activity toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ActivityNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<seedu.address.model.activity.Activity> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<seedu.address.model.activity.Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.activity.UniqueActivityList // instanceof handles nulls
                        && internalList.equals(((seedu.address.model.activity.UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code activities} contains only unique activities.
     */
    private boolean activitiesAreUnique(List<seedu.address.model.activity.Activity> activities) {
        for (int i = 0; i < activities.size() - 1; i++) {
            for (int j = i + 1; j < activities.size(); j++) {
                if (activities.get(i).isSameActivity(activities.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
