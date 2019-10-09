package seedu.address.model.itinerary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Represents a list of
 * @param <T>
 */
public abstract class ConsecutiveOccurrenceList<T> implements Iterable<T>{

    public final ObservableList<T> internalList = FXCollections.observableArrayList();
    public final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent occurrence as the given argument.
     */
    public abstract boolean contains(T toCheck);

    /**
     * Returns true if the list contains
     *
     * @param toCheck
     * @return
     */
    public abstract boolean containsClashing(T toCheck);

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public abstract void add(T toAdd) throws ClashingTripException;

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public abstract void set(T target, T edited) throws TripNotFoundException, ClashingTripException;


    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public abstract void remove(T toRemove) throws TripNotFoundException;

    public void set(ConsecutiveOccurrenceList<T> replacement){
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public abstract void set(List<T> entities);

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConsecutiveOccurrenceList // instanceof handles nulls
                && internalList.equals(((ConsecutiveOccurrenceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    public abstract boolean areConsecutive(List<T> persons);

    /**
     * Returns true if {@code trips} contains only unique trips.
     * @param occurrence
     */
    public abstract boolean areUnique(List<T> occurrence);

}
