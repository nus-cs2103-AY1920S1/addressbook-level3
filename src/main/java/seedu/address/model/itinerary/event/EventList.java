package seedu.address.model.itinerary.event;

import seedu.address.model.itinerary.ConsecutiveOccurrenceList;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class EventList extends ConsecutiveOccurrenceList<Event> {

    @Override
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    @Override
    public boolean containsClashing(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);

    }

    @Override
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if(containsClashing(toAdd)){
            throw new ClashingEventException();
        }
        internalList.add(toAdd);
    }

    @Override
    public void set(Event target, Event edited) {
        requireAllNonNull(target, edited);
        int index = internalList.indexOf(target);
        if(index == -1){
            throw new EventNotFoundException();
        }

        if (!target.isClashingWith(edited) && contains(edited)) {
            throw new ClashingEventException();
        }

        internalList.set(index, edited);
    }

    @Override
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    @Override
    public void set(List<Event> occurrences) {
        requireAllNonNull(occurrences);
        if(!areConsecutive(occurrences)){
            throw new ClashingEventException();
        }
        internalList.setAll(occurrences);
    }

    @Override
    public boolean areConsecutive(List<Event> occurrences) {
        for (int i = 0; i < occurrences.size() - 1; i++) {
            for (int j = i + 1; j < occurrences.size(); j++) {
                if (occurrences.get(i).isClashingWith(occurrences.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
