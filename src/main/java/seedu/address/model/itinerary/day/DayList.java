package seedu.address.model.itinerary.day;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.model.itinerary.ConsecutiveOccurrenceList;
import seedu.address.model.itinerary.day.exceptions.ClashingDayException;
import seedu.address.model.itinerary.day.exceptions.DayNotFoundException;

/**
 * List holding {@code Day}s.
 */
public class DayList extends ConsecutiveOccurrenceList<Day> {
    private static final String MESSAGE_INVALID_DATETIME = "Date should be within valid duration";

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public DayList(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Checks if target day can be added to the list.
     */
    public boolean isValidDay(Day day) {
        return !(day.getStartDate().isBefore(startDate))
                && !(day.getEndDate().isAfter(endDate));
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        this.internalList.removeIf(day -> !this.isValidDay(day));
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        this.internalList.removeIf(day -> !this.isValidDay(day));
    }

    /**
     * Get day from daylist by index.
     * @param index the index of Day to be returned
     * @return the day with the specified index
     */
    public Day get(int index) throws IndexOutOfBoundsException {
        return internalList.get(index);
    }

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
        checkArgument(isValidDay(toAdd), MESSAGE_INVALID_DATETIME);
        if (containsClashing(toAdd)) {
            throw new ClashingDayException();
        }
        internalList.add(toAdd);
    }

    @Override
    public void set(Day targetDay, Day editedDay) {
        requireAllNonNull(targetDay, editedDay);
        checkArgument(isValidDay(editedDay), MESSAGE_INVALID_DATETIME);
        int index = internalList.indexOf(targetDay);
        if (index == -1) {
            throw new DayNotFoundException();
        }
        Day day = internalList.remove(index);
        if (containsClashing(editedDay)) {
            internalList.add(day);
            throw new ClashingDayException();
        } else {
            internalList.add(editedDay);
        }
    }

    @Override
    public void set(List<Day> occurrences) {
        requireAllNonNull(occurrences);
        if (!areConsecutive(occurrences)) {
            throw new ClashingDayException();
        }
        internalList.setAll(occurrences);
    }

    @Override
    public void remove(Day toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DayNotFoundException();
        }

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
