package seedu.exercise.model.conflict;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.builder.ConflictBuilder;
import seedu.exercise.testutil.typicalutil.TypicalConflict;
import seedu.exercise.testutil.typicalutil.TypicalRegime;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;

public class ConflictTest {

    private static final Conflict TEST_CONFLICT = TypicalConflict.VALID_CONFLICT;
    private static final Conflict DIFFERENT_SCHEDULED_CONFLICT = new ConflictBuilder()
            .withScheduled(TypicalSchedule.VALID_SCHEDULE_LEG_DATE_2)
            .withConflict(TypicalSchedule.VALID_SCHEDULE_LEG_DATE)
            .build();
    private static final Conflict DIFFERENT_CONFLICTED_CONFLICT = new ConflictBuilder()
            .withScheduled(TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE)
            .withConflict(TypicalSchedule.VALID_SCHEDULE_LEG_DATE_2)
            .build();
    private static final Conflict SAME_CONFLICT = new ConflictBuilder()
            .withScheduled(TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE)
            .withConflict(TypicalSchedule.VALID_SCHEDULE_LEG_DATE)
            .build();

    @Test
    public void getScheduledUnmodifiableExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> TEST_CONFLICT
                .getScheduledUnmodifiableExerciseList().remove(0));
    }

    @Test
    public void getConflictedUnmodifiableExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> TEST_CONFLICT
                .getConflictedUnmodifiableExerciseList().remove(0));
    }

    @Test
    public void getRegimes_bothConflictAndScheduled_correctRegime() {
        assertEquals(TEST_CONFLICT.getScheduledRegime(), TypicalRegime.VALID_REGIME_CARDIO);
        assertEquals(TEST_CONFLICT.getConflictingRegime(), TypicalRegime.VALID_REGIME_LEGS);
    }

    @Test
    public void getScheduleByRegime_bothConflictAndScheduledRegime_correctSchedule() {
        assertEquals(TEST_CONFLICT.getScheduleByRegime(TypicalRegime.VALID_REGIME_CARDIO),
                TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE);
        assertEquals(TEST_CONFLICT.getScheduleByRegime(TypicalRegime.VALID_REGIME_LEGS),
                TypicalSchedule.VALID_SCHEDULE_LEG_DATE);
    }

    @Test
    public void getScheduleByRegime_nonExistentRegime_returnsNull() {
        //Both schedules in conflict are LEGS.
        assertNull(DIFFERENT_SCHEDULED_CONFLICT.getScheduleByRegime(TypicalRegime.VALID_REGIME_CARDIO));
    }

    @Test
    public void getScheduledName_validConflict_correctName() {
        assertEquals(TEST_CONFLICT.getScheduledName(), TypicalRegime.VALID_REGIME_NAME_CARDIO);
    }

    @Test
    public void getScheduledExerciseList_validConflict_correctList() {
        assertEquals(TEST_CONFLICT.getScheduledExerciseList(),
                TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE.getExercises());
    }

    @Test
    public void getConflictDate_validConflict_correctDate() {
        assertEquals(TEST_CONFLICT.getConflictDate(),
                TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE.getDate());
    }

    @Test
    public void equals_variousScenarios_returnsCorrectValue() {
        // same value -> returns true
        assertTrue(TEST_CONFLICT.equals(SAME_CONFLICT));

        // null -> returns false
        assertFalse(TEST_CONFLICT.equals(null));

        // same object -> returns true
        assertTrue(TEST_CONFLICT.equals(TEST_CONFLICT));

        // different scheduled conflict -> returns false
        assertFalse(TEST_CONFLICT.equals(DIFFERENT_SCHEDULED_CONFLICT));

        // different conflicted conflict -> returns false
        assertFalse(TEST_CONFLICT.equals(DIFFERENT_CONFLICTED_CONFLICT));
    }

    @Test
    public void hashCode_variousScenarios_returnsCorrectValue() {
        // same object -> same hash
        assertEquals(TEST_CONFLICT.hashCode(), TEST_CONFLICT.hashCode());

        // same instance variables -> same hash
        assertEquals(TEST_CONFLICT.hashCode(), SAME_CONFLICT.hashCode());

        // different conflicted conflict -> different hash
        assertNotEquals(TEST_CONFLICT.hashCode(), DIFFERENT_CONFLICTED_CONFLICT.hashCode());

        // different scheduled conflict -> different hash
        assertNotEquals(TEST_CONFLICT.hashCode(), DIFFERENT_SCHEDULED_CONFLICT.hashCode());
    }
}
