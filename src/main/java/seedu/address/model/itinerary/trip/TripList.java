package seedu.address.model.itinerary.trip;

import seedu.address.model.itinerary.UniqueEntityList;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class TripList extends UniqueEntityList<Trip> {

    @Override
    public boolean contains(Trip toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    @Override
    public void add(Trip toAdd) {

    }

    @Override
    public void setPerson(Trip target, Trip edited) {

    }

    @Override
    public void remove(Trip toRemove) {

    }

    @Override
    public void set(UniqueEntityList<Trip> replacement) {

    }

    @Override
    public void set(List<Trip> persons) {

    }

    @Override
    public boolean areUnique(List<Trip> persons) {
        return false;
    }
}
