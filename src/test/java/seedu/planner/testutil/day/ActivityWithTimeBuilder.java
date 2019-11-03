package seedu.planner.testutil.day;

import java.time.LocalTime;

import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.testutil.activity.TypicalActivity;

/**
 * A utility class to help with building Contact objects.
 */
public class ActivityWithTimeBuilder {

    public static final Activity DEFAULT_ACTIVITY = TypicalActivity.ACTIVITYONE;
    public static final int DEFAULT_START_HOUR = 10;
    public static final int DEFAULT_START_MIN = 30;

    private Activity activity;
    private LocalTime startTime;

    public ActivityWithTimeBuilder() {
        activity = DEFAULT_ACTIVITY;
        startTime = LocalTime.of(DEFAULT_START_HOUR, DEFAULT_START_MIN);
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ActivityWithTimeBuilder(ActivityWithTime toCopy) {
        activity = toCopy.getActivity();
        startTime = toCopy.getStartTime();
    }

    /**
     * Sets the {@code Activity} of the {@code ActivityWithTime} that we are building.
     * @param activity new activity to be set
     */
    public ActivityWithTimeBuilder withActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * Sets the start time of the {@code ActivityWithTime} that we are building.
     * @param hour new starting hour.
     * @param min new starting min.
     * @return
     */
    public ActivityWithTimeBuilder withStartTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
        return this;
    }

    /**
     * Sets the start time of the {@code ActivityWithTime} that we are building.
     * @param start new start time to be set.
     */
    public ActivityWithTimeBuilder withStartTime(LocalTime start) {
        this.startTime = start;
        return this;
    }


    public ActivityWithTime build() {
        return new ActivityWithTime(activity, startTime);
    }

}
