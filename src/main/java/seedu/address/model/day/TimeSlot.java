package seedu.address.model.day;

import java.util.Optional;

import seedu.address.model.activity.Activity;

/**
 * Represents the time slots in a {@code Timetable}.
 */
public class TimeSlot {
    private Activity activityAtThisTime;

    public TimeSlot(Activity activity) {
        this.activityAtThisTime = activity;
    }

    public boolean isAvailable() {
        return activityAtThisTime == null;
    }

    public Optional<Activity> getActivity() {
        return Optional.ofNullable(activityAtThisTime);
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
