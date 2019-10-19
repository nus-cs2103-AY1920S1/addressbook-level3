package seedu.exercise.model.resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_DATE_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_NAME_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.exercise.TypicalExercises.BASKETBALL;
import static seedu.exercise.testutil.exercise.TypicalExercises.WALK;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.exercise.ExerciseBuilder;

public class ExerciseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Exercise exercise = new ExerciseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> exercise.getMuscles().remove(0));
    }

    @Test
    public void isSameExercise() {
        // same object -> returns true
        assertTrue(WALK.isSameResource(WALK));

        // null -> returns false
        assertFalse(WALK.isSameResource(null));

        // different date and calories -> returns false
        Exercise editedWalk = new ExerciseBuilder(WALK).withDate(VALID_DATE_BASKETBALL)
            .withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(WALK.isSameResource(editedWalk));

        // different name -> returns false
        editedWalk = new ExerciseBuilder(WALK).withName(VALID_NAME_BASKETBALL).build();
        assertFalse(WALK.isSameResource(editedWalk));

        // same name, same calories, different attributes -> returns true
        editedWalk = new ExerciseBuilder(WALK).withCalories(VALID_CALORIES_BASKETBALL)
            .withQuantity(VALID_QUANTITY_BASKETBALL).withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertTrue(WALK.isSameResource(editedWalk));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Exercise walkCopy = new ExerciseBuilder(WALK).build();
        assertTrue(WALK.equals(walkCopy));

        // same object -> returns true
        assertTrue(WALK.equals(WALK));

        // null -> returns false
        assertFalse(WALK.equals(null));

        // different type -> returns false
        assertFalse(WALK.equals(5));

        // different Exercise -> returns false
        assertFalse(WALK.equals(BASKETBALL));

        // different name -> returns false
        Exercise editedWalk = new ExerciseBuilder(WALK).withName(VALID_NAME_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different date -> returns false
        editedWalk = new ExerciseBuilder(WALK).withDate(VALID_DATE_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different calories -> returns false
        editedWalk = new ExerciseBuilder(WALK).withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different quantity -> returns false
        editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different quantity -> returns false
        editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different tags -> returns false
        editedWalk = new ExerciseBuilder(WALK).withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertFalse(WALK.equals(editedWalk));
    }
}
