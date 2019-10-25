package dukecooks.model.workout.exercise.detail;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.testutil.Assert;

public class RepetitionsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Repetitions(null));
    }

    @Test
    public void constructor_invalidRepetitions_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Repetitions(Integer.valueOf(invalidTagName)));
    }

    @Test
    public void isValidExerciseName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Repetitions.isValidExerciseDetail(null));
    }

}
