package seedu.address.model.day;

import seedu.address.model.activity.Activity;

import java.util.Date;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Wraps the Activity in an object with time and duration fields.
 */
public class ActivityWithTime implements Comparable<ActivityWithTime> {
    private final Activity activity;
    private final Date startTime;
    private final Date endTime;

    public ActivityWithTime(Activity activity, Date startTime, Date endTime) {
        requireAllNonNull(activity, startTime, endTime);
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Activity getActivity() {
        return activity;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
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
