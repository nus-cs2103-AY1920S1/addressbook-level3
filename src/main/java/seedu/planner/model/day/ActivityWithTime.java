package seedu.planner.model.day;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalTime;

import seedu.planner.model.activity.Activity;

/**
 * Wraps the Activity in an object with time and duration fields.
 */
public class ActivityWithTime implements Comparable<ActivityWithTime> {
    private final Activity activity;
    private final LocalTime startTime;

    public ActivityWithTime(Activity activity, LocalTime startTime) {
        requireAllNonNull(activity, startTime);
        this.activity = activity;
        this.startTime = startTime;
    }

    public Activity getActivity() {
        return activity;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return startTime.plusMinutes(activity.getDuration().value);
    }

    /**
     * Checks whether activities are overlapping.
     */
    public boolean isOverlapping(ActivityWithTime other) {
        return !((this.startTime.compareTo(other.startTime) < 0 && this.getEndTime().compareTo(other.startTime) <= 0)
            || (other.startTime.compareTo(this.startTime) < 0 && other.getEndTime().compareTo(this.startTime) <= 0));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Activity) {
            return this.getActivity().equals(other);
        }

        if (!(other instanceof ActivityWithTime)) {
            return false;
        }

        ActivityWithTime otherActivity = (ActivityWithTime) other;

        return this.activity.equals(otherActivity.activity)
                && this.startTime.equals(otherActivity.startTime)
                && this.getEndTime().equals(otherActivity.getEndTime());
    }

    @Override
    public int compareTo(ActivityWithTime other) {
        return this.startTime.compareTo(other.startTime);
    }
}
