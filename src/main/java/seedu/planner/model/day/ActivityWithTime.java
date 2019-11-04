package seedu.planner.model.day;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import jfxtras.scene.control.agenda.Agenda;
import seedu.planner.model.activity.Activity;

/**
 * Wraps the Activity in an object with time and duration fields.
 */
public class ActivityWithTime implements Comparable<ActivityWithTime> {
    private final Activity activity;
    private final LocalDateTime startDateTime;
    private Agenda.AppointmentGroup appointmentGroup = null;

    public ActivityWithTime(Activity activity, LocalDateTime startDateTime) {
        requireAllNonNull(activity, startDateTime);
        this.activity = activity;
        this.startDateTime = startDateTime;
    }

    public ActivityWithTime changeStartDateTime(LocalDateTime newStartDateTime) {
        return new ActivityWithTime(this.activity, newStartDateTime);
    }

    public Activity getActivity() {
        return activity;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return startDateTime.plusMinutes(activity.getDuration().value);
    }

    public Optional<Agenda.AppointmentGroup> getAppointmentGroup() {
        return Optional.ofNullable(appointmentGroup);
    }

    public void setAppointmentGroup(Agenda.AppointmentGroup appointmentGroup) {
        this.appointmentGroup = appointmentGroup;
    }

    /**
     * Checks whether activities are overlapping.
     */
    public boolean isOverlapping(ActivityWithTime other) {
        return !((this.startDateTime.compareTo(other.startDateTime) < 0
                && this.getEndDateTime().compareTo(other.startDateTime) <= 0)
            || (other.startDateTime.compareTo(this.startDateTime) < 0
                && other.getEndDateTime().compareTo(this.startDateTime) <= 0));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Activity) {
            return this.getActivity().equals(other);
        }

        if (!(other instanceof ActivityWithTime)) {
            return false;
        }

        ActivityWithTime otherActivity = (ActivityWithTime) other;

        return this.activity.equals(otherActivity.activity)
                && this.startDateTime.equals(otherActivity.startDateTime)
                && this.getEndDateTime().equals(otherActivity.getEndDateTime());
    }

    @Override
    public int compareTo(ActivityWithTime other) {
        return this.startDateTime.compareTo(other.startDateTime);
    }
}
