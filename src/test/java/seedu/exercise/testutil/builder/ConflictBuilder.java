package seedu.exercise.testutil.builder;

import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;

/**
 * Builder for {@code Conflict} objects
 */
public class ConflictBuilder {

    private Schedule conflictSchedule = TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE;
    private Schedule scheduledSchedule = TypicalSchedule.VALID_SCHEDULE_LEG_DATE;

    /**
     * Sets the conflict schedule of the object we are building to {@code schedule}.
     */
    public ConflictBuilder withConflict(Schedule schedule) {
        this.conflictSchedule = schedule;
        return this;
    }

    /**
     * Sets the scheduled schedule of the object we are building to {@code scheduled}.
     */
    public ConflictBuilder withScheduled(Schedule scheduled) {
        this.scheduledSchedule = scheduled;
        return this;
    }

    public Conflict build() {
        return new Conflict(scheduledSchedule, conflictSchedule);
    }
}
