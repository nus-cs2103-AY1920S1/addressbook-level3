package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_AEROBICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BASKETBALL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.BASKETBALL;
import static seedu.address.testutil.TypicalExercises.WALK;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ExerciseBuilder;

public class ExerciseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Exercise exercise = new ExerciseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> exercise.getMuscles().remove(0));
    }

    @Test
    public void isSameExercise() {
        // same object -> returns true
        assertTrue(WALK.isSameExercise(WALK));

        // null -> returns false
        assertFalse(WALK.isSameExercise(null));

        // different date and calories -> returns false
        Exercise editedWalk = new ExerciseBuilder(WALK).withDate(VALID_DATE_BASKETBALL)
            .withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(WALK.isSameExercise(editedWalk));

        // different name -> returns false
        editedWalk = new ExerciseBuilder(WALK).withName(VALID_NAME_BASKETBALL).build();
        assertFalse(WALK.isSameExercise(editedWalk));

        // same name, same calories, different attributes -> returns true
        editedWalk = new ExerciseBuilder(WALK).withCalories(VALID_CALORIES_BASKETBALL)
            .withQuantity(VALID_QUANTITY_BASKETBALL).withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertTrue(WALK.isSameExercise(editedWalk));
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

        // different phone -> returns false
        editedWalk = new ExerciseBuilder(WALK).withDate(VALID_DATE_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different email -> returns false
        editedWalk = new ExerciseBuilder(WALK).withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different address -> returns false
        editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));

        // different tags -> returns false
        editedWalk = new ExerciseBuilder(WALK).withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertFalse(WALK.equals(editedWalk));
    }
}
