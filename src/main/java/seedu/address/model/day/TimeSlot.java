package seedu.address.model.day;

import static java.util.Objects.requireNonNull;

import seedu.address.model.activity.Activity;

/**
 * Represents the time slots in a {@code Timetable}.
 */
public class TimeSlot {
    private Activity activityAtThisTime;

    public TimeSlot() {
        activityAtThisTime = null;
    }

    public TimeSlot(Activity accommodation) {
        requireNonNull(accommodation);
        this.activityAtThisTime = accommodation;
    }

    public boolean isAvailable() {
        return activityAtThisTime == null;
    }

    public Activity getActivity() {
        requireNonNull(activityAtThisTime);
        return activityAtThisTime;
    }
}
