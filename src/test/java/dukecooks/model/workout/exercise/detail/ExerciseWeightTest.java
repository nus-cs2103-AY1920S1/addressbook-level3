package dukecooks.model.workout.exercise.detail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.unit.WeightUnit;
import dukecooks.testutil.Assert;

public class ExerciseWeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExerciseWeight(null, WeightUnit.GRAM));
        Assert.assertThrows(NullPointerException.class, () -> new ExerciseWeight(Float
                .valueOf(1), null));
    }

    @Test
    public void constructor_invalidExerciseWeight_throwsIllegalArgumentException() {
        String inValidExerciseWeightMagnitude = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExerciseWeight(Float
                .valueOf(inValidExerciseWeightMagnitude), WeightUnit.GRAM));
    }

    @Test
    public void isValidExerciseWeight() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> ExerciseWeight.isValidExerciseDetail(null));

        // invalid ExerciseWeight numbers
        assertFalse(ExerciseWeight.isValidExerciseDetail("")); // empty string
        assertFalse(ExerciseWeight.isValidExerciseDetail(" ")); // spaces only

        // valid Repetition numbers
        assertTrue(ExerciseWeight.isValidExerciseDetail("911")); // exactly 3 numbers
        assertTrue(ExerciseWeight.isValidExerciseDetail("93121534"));
        assertTrue(ExerciseWeight.isValidExerciseDetail("124293842033123")); // long calories numbers
    }

    @Test
    public void testToString() {
        String expected = "[Weight: 10.0kg]";
        String error = "[Weights: 10kg]";
        ExerciseWeight exerciseWeight = new ExerciseWeight(Float.valueOf(10), WeightUnit.KILOGRAM);
        assertEquals(exerciseWeight.toString(), expected);
        assertNotEquals(exerciseWeight.toString(), error);
    }
}
