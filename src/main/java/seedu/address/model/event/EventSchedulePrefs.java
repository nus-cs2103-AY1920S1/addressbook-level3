package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

/**
 * Encapsulates the preferences and settings for the schedule panel
 */
public class EventSchedulePrefs {
    private EventScheduleViewMode viewMode;
    private LocalDateTime targetViewDateTime;

    public EventSchedulePrefs(EventScheduleViewMode viewMode, LocalDateTime targetViewDateTime) {
        requireAllNonNull(viewMode, targetViewDateTime);

        this.viewMode = viewMode;
        this.targetViewDateTime = targetViewDateTime;
    }

    public EventScheduleViewMode getViewMode() {
        return viewMode;
    }

    public void setViewMode(EventScheduleViewMode viewMode) {
        requireNonNull(viewMode);
        this.viewMode = viewMode;
    }

    public LocalDateTime getTargetViewDateTime() {
        return targetViewDateTime;
    }

    public void setTargetViewDateTime(LocalDateTime targetViewDateTime) {
        requireNonNull(targetViewDateTime);
        this.targetViewDateTime = targetViewDateTime;
    }

    /**
     * Returns true if both EventSchedulePrefs have the same identity and data fields.
     * This defines a stronger notion of equality between two EventSchedulePrefs.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventSchedulePrefs)) {
            return false;
        }

        EventSchedulePrefs otherPrefs = (EventSchedulePrefs) other;
        return otherPrefs.getTargetViewDateTime().equals(getTargetViewDateTime())
                && otherPrefs.getViewMode().equals(getViewMode());
    }

    /**
     * Formats the event schedule pref object into a printable string format
     * @return a string representation of the event schedule prefs object
     */
    @Override
    public String toString() {
        return viewMode.name() + "_" + targetViewDateTime.toLocalDate().toString();
    }
}
