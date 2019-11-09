package dukecooks.model.workout.exercise.detail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.unit.DistanceUnit;
import dukecooks.testutil.Assert;



public class DistanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Distance(null, DistanceUnit.KILOMETER));
        Assert.assertThrows(NullPointerException.class, () -> new Distance(Float
                .valueOf(1), null));
    }

    @Test
    public void constructor_invalidDistance_throwsIllegalArgumentException() {
        String inValidDistanceMagnitude = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Distance(Float
                .valueOf(inValidDistanceMagnitude), DistanceUnit.METER));
    }

    @Test
    public void isValidDistance() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Distance.isValidExerciseDetail(null));

        // invalid Distance numbers
        assertFalse(Distance.isValidExerciseDetail("")); // empty string
        assertFalse(Distance.isValidExerciseDetail(" ")); // spaces only

        // valid Repetition numbers
        assertTrue(Distance.isValidExerciseDetail("911")); // exactly 3 numbers
        assertTrue(Distance.isValidExerciseDetail("93121534"));
        assertTrue(Distance.isValidExerciseDetail("124293842033123")); // long calories numbers
    }

    @Test
    public void testToString() {
        String expected = "[Distance: 10.0km]";
        String error = "Distance: 10.0km";
        Distance distance = new Distance(Float.valueOf(10), DistanceUnit.KILOMETER);
        assertEquals(distance.toString(), expected);
        assertNotEquals(distance.toString(), error);
    }
}
