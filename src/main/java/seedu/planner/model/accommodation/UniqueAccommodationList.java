package seedu.planner.model.accommodation;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.commons.core.index.Index;
import seedu.planner.model.accommodation.exceptions.AccommodationNotFoundException;
import seedu.planner.model.accommodation.exceptions.DuplicateAccommodationException;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Name;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A contacts is considered unique by comparing using {@code Accommodation#isSamePerson(Accommodation)}. As such,
 * adding and updating of persons uses Accommodation#isSamePerson(Accommodation) for equality so as to ensure that
 * the contacts being added or updated is unique in terms of identity in the UniqueAccommodationList. However, the
 * removal of a contacts uses Accommodation#equals(Object) so as to ensure that the contacts with exactly the same
 * fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Accommodation#isSameAccommodation
 * (Accommodation)
 */
public class UniqueAccommodationList implements Iterable<Accommodation> {

    private final ObservableList<Accommodation>
            internalList = FXCollections.observableArrayList();
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
     * Returns the first instance of an accommodation possessing the specified name and address.
     */
    public Optional<Accommodation> getAccommodation(Name name, Address address) {
        requireAllNonNull(name, address);
        return internalList.stream().filter(x -> x.getName().equals(name) && x.getAddress().equals(address))
                .findFirst();
    }

    /**
     * Returns the Index of the accommodation to find. Else returns empty optional.
     */
    public Optional<Index> indexOf(Accommodation toFind) {
        requireNonNull(toFind);
        int indexOfToFind = internalList.indexOf(toFind);
        if (indexOfToFind == -1) {
            return Optional.empty();
        } else {
            return Optional.of(Index.fromZeroBased(indexOfToFind));
        }
    }

    /**
     Adds an accommodation to the list.
     */
    public void add(Accommodation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccommodationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds an accommodation to the list at a specific index.
     * The accommodation must not already exist in the list.
     */
    public void addAtIndex(Index index, Accommodation toAdd) {
        requireAllNonNull(index, toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccommodationException();
        }
        internalList.add(index.getZeroBased(), toAdd);
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
