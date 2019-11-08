package seedu.planner.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.commons.core.index.Index;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.exceptions.DayNotFoundException;

/**
 * DayList class helps to manage the list of days in an Planner.
 */
public class DayList implements Iterable<Day> {
    private final ObservableList<Day>
            internalList = FXCollections.observableArrayList();
    private final ObservableList<Day> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent contacts as the given argument.
     */
    public boolean contains(Day toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDay);
    }

    /**
     * Adds a day to the planner
     */
    public void add(Day d) {
        requireNonNull(d);
        internalList.add(d);
    }

    /**
     * Adds a day to the planner at a specific index
     */
    public void addAtIndex(Index index, Day d) {
        requireAllNonNull(index, d);

        internalList.add(index.getZeroBased(), d);
    }

    /**
     * Adds a number of days to the planner.
     */
    public void adds(int numDays) {
        requireNonNull(numDays);
        for (int i = 0; i < numDays; i++) {
            Day toAdd = new Day();
            internalList.add(toAdd);
        }
    }

    public void setDays(DayList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code days}.
     */
    public void setDays(List<Day> days) {
        requireAllNonNull(days);
        internalList.setAll(days);
    }

    /**
     * Replaces the days {@code target} in the list with {@code editedDay}.
     * {@code target} must exist in the planner.
     * The contacts identity of {@code editedDay} must not be the same as another existing days in the
     * planner.
     */
    public void setDay(Day target,
                       Day editedDay) {
        requireAllNonNull(target, editedDay);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DayNotFoundException();
        }
        internalList.set(index, editedDay);
    }

    /**
     * Removes the equivalent days from the list.
     * The days must exist in the list.
     */
    public void remove(Day toRemove) {
        requireNonNull(toRemove);
        int indexOfToRemove = internalList.indexOf(toRemove);
        if (indexOfToRemove == -1) {
            throw new DayNotFoundException();
        }
        shiftDatesInItinerary(
                -1,
                Index.fromZeroBased(indexOfToRemove + 1),
                Index.fromOneBased(internalList.size())
        );
        internalList.remove(toRemove);
    }

    /**
     * Removes the last n number of days from the list.
     * @param n number of days to remove.
     */
    public void deleteDays(int n) {
        int lastIndex = internalList.size() - 1;
        for (int i = 0; i < n; i++) {
            internalList.remove(lastIndex - i);
        }
    }

    public List<Day> getDays(Activity activity) {
        List<Day> listOfDays = new ArrayList<>();
        for (Day day : internalList) {
            if (day.hasActivity(activity)) {
                listOfDays.add(day);
            }
        }
        return listOfDays;
    }

    public int getNumberOfDays() {
        return internalList.size();
    }

    /**
     * Shifts the dates of activities in days from startIndex to endIndex - 1.
     * @param days the amount of days to shift by
     * @param startIndex the start index, inclusive
     * @param endIndex the end index, exclusive
     */
    public void shiftDatesInItinerary(long days, Index startIndex, Index endIndex) {
        for (int i = startIndex.getZeroBased(); i < endIndex.getZeroBased(); i++) {
            Day currDay = internalList.get(i);
            Day editedDay = shiftDatesOfAllActivitiesInDay(currDay, days);
            setDay(currDay, editedDay);
        }
    }

    /**
     * Shifts the dates of activities in a day.
     * @param day the day of activities to shift
     * @param numberOfDays the amount of days to shift by
     * @return an edited day where the dates have shifted
     */
    private Day shiftDatesOfAllActivitiesInDay(Day day, long numberOfDays) {
        List<ActivityWithTime> activitiesInADay = day.getListOfActivityWithTime();
        List<ActivityWithTime> editedActivitiesInADay = new ArrayList<>();
        for (ActivityWithTime a : activitiesInADay) {
            ActivityWithTime editedActivityWithTime = new ActivityWithTime(a.getActivity(),
                    a.getStartDateTime().plusDays(numberOfDays));
            editedActivitiesInADay.add(editedActivityWithTime);
        }
        return new Day(editedActivitiesInADay);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Day> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Day> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DayList // instanceof handles nulls
                && internalList.equals(((DayList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
