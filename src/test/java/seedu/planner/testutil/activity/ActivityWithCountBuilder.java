package seedu.planner.testutil.activity;

import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.ActivityWithCount;

/**
 * An utility class to help with building ActivityWithCount objects.
 */
public class ActivityWithCountBuilder {
    public static final Activity DEFAULT_ACTIVITY = TypicalActivity.ACTIVITY_ONE;
    public static final Long DEFAULT_COUNT = (long) 6;
    private Activity activity;
    private Long count;

    public ActivityWithCountBuilder() {
        activity = DEFAULT_ACTIVITY;
        count = DEFAULT_COUNT;
    }

    /**
     * Initializes the ActivityWithCount with the data of {@code toCopy}.
     */
    public ActivityWithCountBuilder(ActivityWithCount toCopy) {
        activity = toCopy.getActivity();
        this.count = toCopy.getCount();
    }

    /**
     * Sets the {@code Activity} of the {@code ActivityWithCount} that we are building.
     * @param activity new activity to be set
     */
    public ActivityWithCountBuilder withActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * Sets the count of the {@code ActivityWithCount} that we are building.
     */
    public ActivityWithCountBuilder withCount(long count) {
        this.count = count;
        return this;
    }


    public ActivityWithCount build() {
        return new ActivityWithCount(activity, count);
    }
}
