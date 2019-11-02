package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Schedule;

/**
 * Represents a particular schedule regime event that can be redone or undone.
 */
public class ScheduleRegimeEvent implements Event {

    public static final String KEY_TO_SCHEDULE = "toSchedule";
    private static final String EVENT_DESCRIPTION = "Scheduled: Regime %1$s\nOn: %2$s";

    private final Schedule toSchedule;

    /**
     * Creates an ScheduleRegimeEvent to store the particular event of a regime being scheduled to the schedule list.
     *
     * @param eventPayload a data carrier that stores the essential information for undo and redo
     */
    public ScheduleRegimeEvent(EventPayload<Schedule> eventPayload) {
        this.toSchedule = (Schedule) eventPayload.get(KEY_TO_SCHEDULE);
    }

    @Override
    public void undo(Model model) {
        model.removeSchedule(toSchedule);
    }

    @Override
    public void redo(Model model) {
        model.addSchedule(toSchedule);
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                toSchedule.getRegimeName(),
                toSchedule.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleRegimeEvent // instanceof handles nulls
                && toSchedule.equals(((ScheduleRegimeEvent) other).toSchedule));
    }
}
