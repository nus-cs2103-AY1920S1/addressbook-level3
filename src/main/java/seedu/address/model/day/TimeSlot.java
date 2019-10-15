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

    public TimeSlot(Activity activity) {
        requireNonNull(activity);
        this.activityAtThisTime = activity;
    }

    public boolean isAvailable() {
        return activityAtThisTime == null;
    }

    public Activity getActivity() {
        requireNonNull(activityAtThisTime);
        return activityAtThisTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeSlot)) {
            return false;
        }

        TimeSlot otherTimeSlot = (TimeSlot) other;

        return otherTimeSlot.getActivity().equals(getActivity());
    }
}
