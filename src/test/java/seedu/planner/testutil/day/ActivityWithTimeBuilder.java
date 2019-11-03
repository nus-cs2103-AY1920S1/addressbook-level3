package seedu.planner.testutil.day;

import java.time.LocalDateTime;

import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.testutil.activity.TypicalActivity;

/**
 * A utility class to help with building Contact objects.
 */
public class ActivityWithTimeBuilder {

    public static final Activity DEFAULT_ACTIVITY = TypicalActivity.ACTIVITYONE;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(2019, 3,
            3, 10, 30);
    private Activity activity;
    private LocalDateTime startDateTime;

    public ActivityWithTimeBuilder() {
        activity = DEFAULT_ACTIVITY;
        startDateTime = DEFAULT_DATE_TIME;
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ActivityWithTimeBuilder(ActivityWithTime toCopy) {
        activity = toCopy.getActivity();
        startDateTime = toCopy.getStartDateTime();
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
    public ActivityWithTimeBuilder withStartDateTime(int year, int month, int day, int hour, int min) {
        this.startDateTime = LocalDateTime.of(year, month, day, hour, min);
        return this;
    }

    /**
     * Sets the start time of the {@code ActivityWithTime} that we are building.
     * @param start new start time to be set.
     */
    public ActivityWithTimeBuilder withStartDateTime(LocalDateTime start) {
        this.startDateTime = start;
        return this;
    }


    public ActivityWithTime build() {
        return new ActivityWithTime(activity, startDateTime);
    }

}
