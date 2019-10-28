package seedu.exercise.testutil.typicalutil;

import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.testutil.builder.ConflictBuilder;

/**
 * Holds a valid conflict object to be used in testing
 */
public class TypicalConflict {

    public static final Conflict VALID_CONFLICT = new ConflictBuilder()
            .withScheduled(TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE)
            .withConflict(TypicalSchedule.VALID_SCHEDULE_LEG_DATE)
            .build();
}
