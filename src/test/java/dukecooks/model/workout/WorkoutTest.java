package dukecooks.model.workout;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.Assert;

public class WorkoutTest {

    @Test
    public void constructor_nullWorkoutName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Workout(null));
    }

}
