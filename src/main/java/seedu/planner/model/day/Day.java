package seedu.planner.model.day;

import java.util.List;
import java.util.Optional;

import seedu.planner.commons.core.index.Index;
import seedu.planner.model.activity.Activity;

/**
 * Represents a Day in the travel planner's planner.
 */
public class Day {
    //@@author 1nefootstep
    public static final String MESSAGE_CONSTRAINTS = "Number of days should be a non-negative integer less "
            + "than or equal to 31.";
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|[1-9])$";

    private final Timetable timetable;

    public Day() {
        this.timetable = new Timetable();
    }

    public Day(List<ActivityWithTime> activitiesForDay) {
        this.timetable = new Timetable(activitiesForDay);
    }

    /**
     * Returns true if both days contain the same activities.
     */
    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        }
        return this.timetable.equals(otherDay.timetable);
    }

    //@@author 1nefootstep
    /**
     * Gets all the activities present in {@code Day} and wraps them in ActivityWithTime.
     * @return list of {@code ActivityWithTime} present in {@code Day}
     */
    public List<ActivityWithTime> getListOfActivityWithTime() {
        return this.timetable.getActivitiesWithTime();
    }

    public void addActivityWithTime(ActivityWithTime toAdd) {
        this.timetable.addActivityWithTime(toAdd);
    }

    public void removeActivityWithIndex(Index toRemove) {
        this.timetable.removeActivityWithIndex(toRemove);
    }

    public void removeActivity(Activity activity) {
        this.timetable.removeActivity(activity);
    }

    public Index getIndex(ActivityWithTime activity) {
        return timetable.getIndex(activity);
    }

    public ActivityWithTime getActivityWithIndex(Index index) {
        return timetable.getActivityWithTime(index);
    }

    /**
     * Returns the next activity in the schedule that is overlapping with the activity given by the Index.
     */
    public Optional<ActivityWithTime> findNextActNoOverlap(Index index) {
        return timetable.findNextNoOverlap(index);
    }

    /**
     * Returns a list of activity overlapping with the given {@code ActivityWithTime}.
     */
    public List<ActivityWithTime> findAllOverlap(ActivityWithTime activity) {
        return timetable.findAllOverlap(activity);
    }

    /**
     * Checks whether the {@code Day} contains the {@code Activity}.
     */
    public boolean hasActivity(Activity activity) {
        return timetable.getActivitiesWithTime().stream()
                .anyMatch(x -> x.getActivity().equals(activity));
    }

    //@@author 1nefootstep
    /**
     * Returns true if a given string is a valid integer.
     */
    public static boolean isValidDayNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.timetable.equals(this.timetable);
    }
}
