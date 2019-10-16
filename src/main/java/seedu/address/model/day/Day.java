package seedu.address.model.day;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.model.activity.Activity;
import seedu.address.model.day.time.DurationInHalfHour;
import seedu.address.model.day.time.TimeInHalfHour;

/**
 * Represents a Day in the travel planner's planner.
 * Guarantees: timetable is present and not null, field values are validated, immutable.
 */
public class Day {
    public static final String MESSAGE_CONSTRAINTS = "Number of days should be an integer greater than 0.";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$\n";

    private final Timetable timetable;

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
        return timetable.equals(otherDay.timetable);
    }

    /**
     * Gets all the activities present in {@code Day} and wraps them in ActivityWithTime.
     * @return list of {@code ActivityWithTime} present in {@code Day}
     */
    public List<ActivityWithTime> getActivitiesWithTime() {
        List<ActivityWithTime> activitiesForDay = new ArrayList<>();
        Activity prevActivity = null;
        ActivityWithTime currActivityWithTime = null;
        for (int i = 0; i < Timetable.NUMBER_OF_HALF_HOUR_IN_A_DAY; i++) {
            Optional<Activity> optionalActivity = timetable.getActivityAtIndex(Index.fromZeroBased(i));
            if (optionalActivity.isPresent()) {
                Activity currActivity = optionalActivity.get();
                if (currActivity.equals(prevActivity)) {
                    currActivityWithTime = increaseDurationOfActivityWithTime(currActivityWithTime, 30);
                } else {
                    addToListIfActivityWithTimeNotNull(activitiesForDay, Optional.ofNullable(currActivityWithTime));
                    prevActivity = currActivity;
                    TimeInHalfHour time = new TimeInHalfHour(i / 2, i % 2 * 30);
                    DurationInHalfHour duration = new DurationInHalfHour(1);
                    currActivityWithTime = new ActivityWithTime(currActivity, time, duration);
                }
            } else {
                addToListIfActivityWithTimeNotNull(activitiesForDay, Optional.ofNullable(currActivityWithTime));
                prevActivity = null;
            }
        }
        addToListIfActivityWithTimeNotNull(activitiesForDay, Optional.ofNullable(currActivityWithTime));
        return activitiesForDay;
    }

    private void addToListIfActivityWithTimeNotNull(List<ActivityWithTime> ls, Optional<ActivityWithTime> a) {
        a.ifPresent(activityWithTime -> ls.add(activityWithTime));
    }

    private ActivityWithTime increaseDurationOfActivityWithTime(ActivityWithTime a, int mins) {
        DurationInHalfHour updatedDuration = new DurationInHalfHour(
                a.getDuration().getNumberOfHalfHour() + mins / 30);
        return new ActivityWithTime(a.getActivity(), a.getTime(), updatedDuration);
    }

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
