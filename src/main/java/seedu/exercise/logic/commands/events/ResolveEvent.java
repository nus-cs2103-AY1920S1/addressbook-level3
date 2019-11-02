package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.resource.Schedule;

/**
 * Represents a particular resolve schedule conflict event that can be redone or undone.
 */
public class ResolveEvent implements Event {

    public static final String KEY_RESOLVED_SCHEDULE = "resolvedSchedule";
    public static final String KEY_CONFLICT = "conflict";
    private static final String EVENT_DESCRIPTION = "Scheduled: Regime %1$s\nOn: %2$s";

    private final Schedule resolvedSchedule;
    private final Conflict conflict;
    private final boolean isNewRegimeCreated;

    /**
     * Creates an ResolveEvent to store the particular event of a schedule conflict
     * being resolved.
     *
     * @param eventPayload a data carrier that stores the essential information for undo and redo
     */
    public ResolveEvent(EventPayload<Object> eventPayload) {
        this.resolvedSchedule = (Schedule) eventPayload.get(KEY_RESOLVED_SCHEDULE);
        this.conflict = (Conflict) eventPayload.get(KEY_CONFLICT);
        this.isNewRegimeCreated = isNewRegimeCreated();
    }

    @Override
    public void undo(Model model) {
        if (isNewRegimeCreated) {
            model.deleteRegime(resolvedSchedule.getRegime());
        }
        model.removeSchedule(resolvedSchedule);
        model.addSchedule(conflict.getScheduled());
    }

    @Override
    public void redo(Model model) {
        if (isNewRegimeCreated) {
            model.addRegime(resolvedSchedule.getRegime());
        }
        model.removeSchedule(conflict.getScheduled());
        model.addSchedule(resolvedSchedule);
    }

    /**
     * Checks if the regime in the resolved schedule is newly created and added to the regime book
     * when the resolve command is executed. The regime is created if the regime in the resolved schedule
     * is NOT in any of the two schedules in conflict.
     *
     * @return true if the regime is newly created when the resolve is executed, false otherwise
     */
    private boolean isNewRegimeCreated() {
        return !(resolvedSchedule.getRegime().equals(conflict.getScheduled().getRegime())
                || resolvedSchedule.getRegime().equals(conflict.getConflicted().getRegime()));
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                resolvedSchedule.getRegimeName(),
                resolvedSchedule.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResolveEvent // instanceof handles nulls
                && resolvedSchedule.equals(((ResolveEvent) other).resolvedSchedule)
                && conflict.equals(((ResolveEvent) other).conflict));
    }
}
