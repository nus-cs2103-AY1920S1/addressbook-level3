package dukecooks.model.workout.exercise.components;

import static dukecooks.testutil.exercise.TypicalExercises.ABS_ROLLOUT;
import static dukecooks.testutil.exercise.TypicalExercises.SITUP;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.Assert;
import dukecooks.testutil.exercise.ExerciseBuilder;

public class ExerciseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Exercise exercise = new ExerciseBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> exercise.getExerciseDetails().remove(0));
    }

    @Test
    public void isSameExercise() {
        // same object -> returns true
        assertTrue(ABS_ROLLOUT.isSameExercise(ABS_ROLLOUT));

        // null -> returns false
        assertFalse(ABS_ROLLOUT.isSameExercise(null));

        // different name -> returns false
        Exercise editedAbsRollout = new ExerciseBuilder(ABS_ROLLOUT).withName(CommandTestUtil.VALID_NAME_PUSHUP)
                .build();
        assertFalse(ABS_ROLLOUT.isSameExercise(editedAbsRollout));

        // same name, same muscles, same intensity, different attributes -> returns true
        editedAbsRollout = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, 99, 99).build();
        assertTrue(ABS_ROLLOUT.isSameExercise(editedAbsRollout));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Exercise aliceCopy = new ExerciseBuilder(ABS_ROLLOUT).build();
        assertTrue(ABS_ROLLOUT.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ABS_ROLLOUT.equals(ABS_ROLLOUT));

        // null -> returns false
        assertFalse(ABS_ROLLOUT.equals(null));

        // different type -> returns false
        assertFalse(ABS_ROLLOUT.equals(5));

        // different person -> returns false
        assertFalse(ABS_ROLLOUT.equals(SITUP));

        // different name -> returns false
        Exercise editedAlice = new ExerciseBuilder(ABS_ROLLOUT).withName(CommandTestUtil.VALID_NAME_SITUP).build();
        assertFalse(ABS_ROLLOUT.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, 99, 99).build();
        assertFalse(ABS_ROLLOUT.equals(editedAlice));
    }
}
