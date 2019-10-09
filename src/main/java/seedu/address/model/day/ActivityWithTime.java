package seedu.address.model.day;

import seedu.address.model.activity.Activity;
import seedu.address.model.day.time.DurationInHalfHour;
import seedu.address.model.day.time.TimeInHalfHour;

/**
 * Wraps the Activity in a time range.
 */
public class ActivityWithTime {
    private final Activity activity;
    private final TimeInHalfHour time;
    private final DurationInHalfHour duration;

    public ActivityWithTime(Activity activity, TimeInHalfHour time, DurationInHalfHour duration) {
        this.activity = activity;
        this.time = time;
        this.duration = duration;
    }

    public Activity getActivity() {
        return activity;
    }

    public TimeInHalfHour getTime() {
        return time;
    }

    public DurationInHalfHour getDuration() {
        return duration;
    }
}
