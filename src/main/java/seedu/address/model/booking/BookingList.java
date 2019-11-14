package seedu.address.model.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

/**
 * List containing {@code Expenditure}s.
 */
public class BookingList implements Iterable<Booking> {

    public final ObservableList<Booking> internalList = FXCollections.observableArrayList();
    public final ObservableList<Booking> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Booking toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBooking);
    }

    /**
     * Adds an expenditure to the list.
     * The expenditure must not already exist in the list.
     */
    public void add(Booking toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the expenditure {@code target} in the list with {@code editedExpenditure}.
     * {@code target} must exist in the list.
     * The expenditure identity of {@code editedExpenditure} must not be the same as another in the list.
     */
    public void set(Booking target, Booking edited) throws BookingNotFoundException {
        requireAllNonNull(target, edited);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookingNotFoundException("Booking not found");
        }
        if (!target.isSameBooking(edited) && contains(edited)) {
            throw new DuplicateBookingException();
        }
        internalList.set(index, edited);
    }

    public void set(List<Booking> occurrences) {
        requireAllNonNull(occurrences);
        if (!bookingsAreUnique(occurrences)) {
            throw new DuplicateBookingException();
        }
        internalList.setAll(occurrences);
    }

    /**
     * Removes the equivalent expenditure from the list.
     * The expenditure must exist in the list.
     */
    public void remove(Booking toRemove) throws BookingNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BookingNotFoundException("Booking not found");
        }
    }

    /**
     * Removes the item at the specified index.
     *
     * @param index The index of the item to remove.
     */
    public void remove(Index index) {
        requireNonNull(index);
        internalList.remove(index.getZeroBased());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Booking> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Booking> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookingList // instanceof handles nulls
                && internalList.equals(((BookingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if the list contains only unique expenditures.
     */
    private boolean bookingsAreUnique(List<Booking> occurrence) {
        for (int i = 0; i < occurrence.size() - 1; i++) {
            for (int j = i + 1; j < occurrence.size(); j++) {
                if (occurrence.get(i).isSameBooking(occurrence.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
