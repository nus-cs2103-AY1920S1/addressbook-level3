package seedu.address.model.itinerary.day;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
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
    public boolean containsClashing(Day toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isClashingWith);
    }

    @Override
    public void add(Day toAdd) {
        requireNonNull(toAdd);
        if(containsClashing(toAdd)){
            throw new ClashingDayException();
        }
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
    public void set(List<Day> occurrences) {
        requireAllNonNull(occurrences);
        if(!areConsecutive(occurrences)){
            throw new ClashingDayException();
        }
        internalList.setAll(occurrences);
    }

    @Override
    public boolean areConsecutive(List<Day> occurrences) {
        for (int i = 0; i < occurrences.size() - 1; i++) {
            for (int j = i + 1; j < occurrences.size(); j++) {
                if (occurrences.get(i).isClashingWith(occurrences.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean areUnique(List<Day> occurrence) {
        for (int i = 0; i < occurrence.size() - 1; i++) {
            for (int j = i + 1; j < occurrence.size(); j++) {
                if (occurrence.get(i).isSameDay(occurrence.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
