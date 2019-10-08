package seedu.address.model.day;

import seedu.address.model.day.time.DurationInHalfHour;
import seedu.address.model.day.time.TimeInHalfHour;

/**
 * Wraps the Activity in a time range.
 */
public class ActivityInTimeRange {
    private final ActivityStub activity;
    private final TimeInHalfHour time;
    private final DurationInHalfHour duration;

    public ActivityInTimeRange(ActivityStub activity, TimeInHalfHour time, DurationInHalfHour duration) {
        this.activity = activity;
        this.time = time;
        this.duration = duration;
    }

    public ActivityStub getActivity() {
        return activity;
    }

    public TimeInHalfHour getTime() {
        return time;
    }

    public DurationInHalfHour getDuration() {
        return duration;
    }
}
