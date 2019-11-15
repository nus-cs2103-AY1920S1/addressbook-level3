package dukecooks.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.components.MusclesTrained;
import dukecooks.testutil.Assert;


public class MusclesTrainedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MusclesTrained(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new MusclesTrained(new MuscleType("Chest"), null));
        Assert.assertThrows(NullPointerException.class, () -> new MusclesTrained(null, new ArrayList<>()));
    }

    @Test
    public void equals() {
        MuscleType firstPrimaryMuscle = new MuscleType("Chest");
        ArrayList<MuscleType> firstSecondaryMuscles = new ArrayList<>(Arrays.asList(new MuscleType("Back"),
                new MuscleType("Abs")));

        MuscleType secondPrimaryMuscle = new MuscleType("Biceps");
        ArrayList<MuscleType> secondSecondaryMuscles = new ArrayList(Arrays.asList(new MuscleType("Cardio"),
                new MuscleType("Triceps")));

        MusclesTrained firstMusclesTrained = new MusclesTrained(firstPrimaryMuscle, firstSecondaryMuscles);
        MusclesTrained secondMusclesTrained = new MusclesTrained(secondPrimaryMuscle, secondSecondaryMuscles);

        // same object -> returns true
        assertTrue(firstMusclesTrained.equals(firstMusclesTrained));

        // same values -> returns true
        MusclesTrained firstMusclesTrainedCopy =
                new MusclesTrained(firstPrimaryMuscle, firstSecondaryMuscles);
        assertTrue(firstMusclesTrained.equals(firstMusclesTrainedCopy));

        // different types -> returns false
        assertFalse(firstMusclesTrained.equals(1));

        // null -> returns false
        assertFalse(firstMusclesTrained.equals(null));

        // different person -> returns false
        assertFalse(firstMusclesTrained.equals(secondMusclesTrained));

        // same primary muscle, different secondary muscles -> false
        MusclesTrained samePrimaryMuscle = new MusclesTrained(firstPrimaryMuscle, secondSecondaryMuscles);
        assertFalse(firstMusclesTrained.equals(samePrimaryMuscle));

        // same secondary muscles, different primary muscle -> return false
        MusclesTrained sameSecondaryMuscles = new MusclesTrained(secondPrimaryMuscle, firstSecondaryMuscles);
        assertFalse(firstMusclesTrained.equals(sameSecondaryMuscles));
    }

    @Test
    public void testToString() {
        String expectedEmptySecondaryMuscles = " [Primary Muscle: Chest]";
        String expectedFilledSecondaryMuscles = " [Primary Muscle: Chest "
                + "Secondary Muscles: Biceps, ]";
        MusclesTrained emptySecondaryMuscles = new MusclesTrained(new MuscleType("Chest"), new ArrayList<>());
        ArrayList<MuscleType> secondaryMuscles = new ArrayList<>(Arrays.asList(new MuscleType("Biceps")));
        MusclesTrained filledSecondaryMuscles = new MusclesTrained(new MuscleType("Chest"), secondaryMuscles);
        assertEquals(emptySecondaryMuscles.toString(), expectedEmptySecondaryMuscles);
        assertEquals(filledSecondaryMuscles.toString(), expectedFilledSecondaryMuscles);
    }
}
