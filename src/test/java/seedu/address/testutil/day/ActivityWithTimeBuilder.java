package seedu.address.testutil.day;

import java.time.LocalTime;

import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.itineraryitem.activity.Activity;
import seedu.address.testutil.activity.TypicalActivity;

/**
 * A utility class to help with building Contact objects.
 */
public class ActivityWithTimeBuilder {

    public static final Activity DEFAULT_ACTIVITY = TypicalActivity.ACTIVITYONE;
    public static final int DEFAULT_START_HOUR = 10;
    public static final int DEFAULT_START_MIN = 30;
    public static final int DEFAULT_END_HOUR = 12;
    public static final int DEFAULT_END_MIN = 30;

    private Activity activity;
    private LocalTime startTime;
    private LocalTime endTime;

    public ActivityWithTimeBuilder() {
        activity = DEFAULT_ACTIVITY;
        startTime = LocalTime.of(DEFAULT_START_HOUR, DEFAULT_START_MIN);
        endTime = LocalTime.of(DEFAULT_END_HOUR, DEFAULT_END_MIN);
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ActivityWithTimeBuilder(ActivityWithTime toCopy) {
        activity = toCopy.getActivity();
        startTime = toCopy.getStartTime();
        endTime = toCopy.getEndTime();
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

    /**
     * Sets the end time of the {@code ActivityWithTime} that we are building.
     * @param hour new ending hour.
     * @param min new ending min.
     * @return
     */
    public ActivityWithTimeBuilder withEndTime(int hour, int min) {
        this.endTime = LocalTime.of(hour, min);
        return this;
    }

    /**
     * Sets the end time of the {@code ActivityWithTime} that we are building.
     * @param end new end time to be set.
     */
    public ActivityWithTimeBuilder withEndTime(LocalTime end) {
        this.endTime = end;
        return this;
    }


    public ActivityWithTime build() {
        return new ActivityWithTime(activity, startTime, endTime);
    }

}
