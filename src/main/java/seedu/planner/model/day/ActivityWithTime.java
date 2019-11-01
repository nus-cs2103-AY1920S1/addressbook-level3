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
    private final LocalTime endTime;

    public ActivityWithTime(Activity activity, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(activity, startTime, endTime);
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Activity getActivity() {
        return activity;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    /**
     * Checks whether activities are overlapping.
     */
    public boolean isOverlapping(ActivityWithTime other) {
        return !((this.startTime.compareTo(other.startTime) < 0 && this.endTime.compareTo(other.startTime) <= 0)
            || (other.startTime.compareTo(this.startTime) < 0 && other.endTime.compareTo(this.startTime) <= 0));
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
                && this.endTime.equals(otherActivity.endTime);
    }

    @Override
    public int compareTo(ActivityWithTime other) {
        return this.startTime.compareTo(other.startTime);
    }
}
