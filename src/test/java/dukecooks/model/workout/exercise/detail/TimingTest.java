package dukecooks.model.workout.exercise.detail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.Timing;
import dukecooks.testutil.Assert;


public class TimingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Timing(null));
    }

    @Test
    public void isValidTiming() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Timing.isValidExerciseDetail(null));

        // invalid Timing numbers
        assertFalse(Timing.isValidExerciseDetail("")); // empty string
        assertFalse(Timing.isValidExerciseDetail(" ")); // spaces only

        // valid Repetition numbers
        assertTrue(Timing.isValidExerciseDetail("911")); // exactly 3 numbers
        assertTrue(Timing.isValidExerciseDetail("93121534"));
        assertTrue(Timing.isValidExerciseDetail("124293842033123")); // long calories numbers
    }

    @Test
    public void testToString() {
        String expected = "[Timing: 0:21:00]";
        String error = "[Timing: 10kg]";
        Timing timing = new Timing(Duration.parse("PT21M"));
        assertEquals(timing.toString(), expected);
        assertNotEquals(timing.toString(), error);
    }
}
