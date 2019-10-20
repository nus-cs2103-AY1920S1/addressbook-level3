package seedu.address.model.itineraryitem.accommodation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.itineraryitem.accommodation.exceptions.AccommodationNotFoundException;
import seedu.address.model.itineraryitem.accommodation.exceptions.DuplicateAccommodationException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A contacts is considered unique by comparing using {@code Accommodation#isSamePerson(Accommodation)}. As such,
 * adding and updating of persons uses Accommodation#isSamePerson(Accommodation) for equality so as to ensure that
 * the contacts being added or updated is unique in terms of identity in the UniqueAccommodationList. However, the
 * removal of a contacts usesvAccommodation#equals(Object) so as to ensure that the contacts with exactly the same
 * fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Accommodation#isSameAccommodation
 * (seedu.address.model.ItineraryItem.accommodation.Accommodation)
 */
public class UniqueAccommodationList implements Iterable<Accommodation> {

    private final ObservableList<Accommodation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Accommodation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent contacts as the given argument.
     */
    public boolean contains(Accommodation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAccommodation);
    }

    /**
     * Adds a contacts to the list.
     * The contacts must not already exist in the list.
     */
    public void add(Accommodation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccommodationException();
        }
        internalList.add(toAdd);
    }

    public void setAccommodations(UniqueAccommodationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAccommodations(List<Accommodation> accommodations) {
        requireAllNonNull(accommodations);
        if (!accommodationsAreUnique(accommodations)) {
            throw new DuplicateAccommodationException();
        }

        internalList.setAll(accommodations);
    }

    /**
     * Replaces the contacts {@code target} in the list with {@code editedAccommodation}.
     * {@code target} must exist in the list.
     * The contacts identity of {@code editedAccommodation} must not be the same as another existing contacts in the
     * list.
     */
    public void setAccommodation(Accommodation target, Accommodation editedAccommodation) {
        requireAllNonNull(target, editedAccommodation);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AccommodationNotFoundException();
        }

        if (!target.isSameAccommodation(editedAccommodation) && contains(editedAccommodation)) {
            throw new DuplicateAccommodationException();
        }

        internalList.set(index, editedAccommodation);
    }

    /**
     * Removes the equivalent contacts from the list.
     * The contacts must exist in the list.
     */
    public void remove(Accommodation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AccommodationNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Accommodation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Accommodation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAccommodationList // instanceof handles nulls
                        && internalList.equals(((UniqueAccommodationList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code activities} contains only unique activities.
     */
    private boolean accommodationsAreUnique(List<Accommodation> accommodations) {
        for (int i = 0; i < accommodations.size() - 1; i++) {
            for (int j = i + 1; j < accommodations.size(); j++) {
                if (accommodations.get(i).isSameAccommodation(accommodations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
