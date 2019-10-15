package seedu.address.model.trip;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.itinerary.ConsecutiveOccurrenceList;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.DuplicateTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;

/**
 * Abstraction of a list containing trips, backed by ConsecutiveOccurenceList.
 */
public class TripList extends ConsecutiveOccurrenceList<Trip> {

    @Override
    public boolean contains(Trip toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTrip);
    }

    @Override
    public boolean containsClashing(Trip toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isClashingWith);
    }

    @Override
    public void add(Trip toAdd) throws ClashingTripException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTripException();
        }
        if (containsClashing(toAdd)) {
            throw new ClashingTripException();
        }

        internalList.add(toAdd);
    }

    @Override
    public void set(Trip targetTrip, Trip editedTrip) throws TripNotFoundException, ClashingTripException {
        requireAllNonNull(targetTrip, editedTrip);
        int index = internalList.indexOf(targetTrip);
        if (index == -1) {
            throw new TripNotFoundException();
        }

        if (!targetTrip.isSameTrip(editedTrip) && contains(editedTrip)) {
            throw new DuplicateTripException();
        }

        internalList.remove(index);
        if (containsClashing(editedTrip)) {
            internalList.add(index, targetTrip);
            throw new ClashingTripException();
        }

        internalList.add(index, editedTrip);
    }

    @Override
    public void set(ConsecutiveOccurrenceList<Trip> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    @Override
    public void set(List<Trip> trips) {
        requireAllNonNull(trips);
        if (!areUnique(trips)) {
            throw new DuplicateTripException();
        }
        if (!areConsecutive(trips)) {
            throw new ClashingTripException();
        }

        internalList.setAll(trips);
    }

    @Override
    public void remove(Trip toRemove) throws TripNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TripNotFoundException();
        }
    }

    @Override
    public boolean areConsecutive(List<Trip> trips) {
        for (int i = 0; i < trips.size() - 1; i++) {
            for (int j = i + 1; j < trips.size(); j++) {
                if (trips.get(i).isClashingWith(trips.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean areUnique(List<Trip> occurrence) {
        for (int i = 0; i < occurrence.size() - 1; i++) {
            for (int j = i + 1; j < occurrence.size(); j++) {
                if (occurrence.get(i).isSameTrip(occurrence.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
