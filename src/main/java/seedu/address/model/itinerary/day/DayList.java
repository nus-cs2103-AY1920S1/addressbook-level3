package seedu.address.model.itinerary.day;

import seedu.address.model.itinerary.ConsecutiveOccurrenceList;
import seedu.address.model.itinerary.day.exceptions.ClashingDayException;
import seedu.address.model.itinerary.day.exceptions.DayNotFoundException;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class DayList extends ConsecutiveOccurrenceList<Day> {

    @Override
    public boolean contains(Day toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDay);
    }

    @Override
    public void add(Day toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    @Override
    public void set(Day targetDay, Day editedDay) {
        requireAllNonNull(targetDay, editedDay);
        int index = internalList.indexOf(targetDay);
        if(index == -1){
            throw new DayNotFoundException();
        }

        if (!targetDay.isClashingWith(editedDay) && contains(editedDay)) {
            throw new ClashingDayException();
        }

        internalList.set(index, editedDay);
    }

    @Override
    public void remove(Day toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DayNotFoundException();
        }
    }

    @Override
    public void set(ConsecutiveOccurrenceList<Day> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    @Override
    public void set(List<Day> persons) {
        requireAllNonNull(persons);

        internalList.setAll(persons);
    }

    @Override
    public boolean areConsecutive(List<Day> persons) {
        return true;
    }
}
