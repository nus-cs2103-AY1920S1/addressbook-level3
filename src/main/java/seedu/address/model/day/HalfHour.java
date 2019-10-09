package seedu.address.model.day;

import static java.util.Objects.requireNonNull;

/**
 * Represents the time slots in a {@code Timetable}.
 */
public class HalfHour {
    private ActivityStub activityAtThisTime;

    public HalfHour() {
        activityAtThisTime = null;
    }

    public HalfHour(ActivityStub activity) {
        this.activityAtThisTime = activity;
    }

    public boolean getIsOccupied() {
        return activityAtThisTime != null;
    }

    public ActivityStub getActivity() {
        requireNonNull(activityAtThisTime);
        return activityAtThisTime;
    }
}
