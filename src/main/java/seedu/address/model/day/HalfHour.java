package seedu.address.model.day;

import static java.util.Objects.requireNonNull;

import seedu.address.model.activity.Activity;

/**
 * Represents the time slots in a {@code Timetable}.
 */
public class HalfHour {
    private Activity activityAtThisTime;

    public HalfHour() {
        activityAtThisTime = null;
    }

    public HalfHour(Activity activity) {
        this.activityAtThisTime = activity;
    }

    public boolean getIsOccupied() {
        return activityAtThisTime != null;
    }

    public Activity getActivity() {
        requireNonNull(activityAtThisTime);
        return activityAtThisTime;
    }
}
