package seedu.address.model.event;

import java.time.LocalDateTime;

/**
 * Encapsualtes the preferences and settings for the schedule panel
 */
public class  EventSchedulePrefs {
    private EventScheduleViewMode viewMode;
    private LocalDateTime targetViewDateTime;

    public EventSchedulePrefs(EventScheduleViewMode viewMode, LocalDateTime targetViewDateTime) {
        this.viewMode = viewMode;
        this.targetViewDateTime = targetViewDateTime;
    }

    public EventScheduleViewMode getViewMode() {
        return viewMode;
    }

    public void setViewMode(EventScheduleViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public LocalDateTime getTargetViewDateTime() {
        return targetViewDateTime;
    }

    public void setTargetViewDateTime(LocalDateTime targetViewDateTime) {
        this.targetViewDateTime = targetViewDateTime;
    }

    /**
     * A string representation of the event schedule preferences
     * @return a string representing Event Schedule Preferences using the targetViewDateTime and viewMode.
     */
    @Override
    public String toString() {
        return targetViewDateTime.toLocalDate().toString() + "_" + viewMode.name();
    }

}
