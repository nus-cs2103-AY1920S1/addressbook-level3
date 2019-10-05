package seedu.address.model.trip;

import seedu.address.model.itinerary.ConsecutiveOccurrenceList;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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

        internalList.remove(index);
        if (containsClashing(editedTrip)) {
            internalList.add(index, targetTrip);
            throw new ClashingTripException();
        }

        internalList.add(index, editedTrip);
    }

    @Override
    public void remove(Trip toRemove) throws TripNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TripNotFoundException();
        }
    }

    @Override
    public void set(ConsecutiveOccurrenceList<Trip> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    @Override
    public void set(List<Trip> persons) {
        requireAllNonNull(persons);

        internalList.setAll(persons);
    }

    @Override
    public boolean areConsecutive(List<Trip> persons) {
        return true;
    }
}
