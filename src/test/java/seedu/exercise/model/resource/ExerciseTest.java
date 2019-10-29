package seedu.exercise.model.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.VALID_BASKETBALL_STRING_WITH_CUSTOM_PROPERTY;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.builder.ExerciseBuilder;

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
    public void equalsAndHashCode_variousScenarios_success() {
        // same values -> returns true/same hashcode
        Exercise walkCopy = new ExerciseBuilder(WALK).build();
        assertTrue(WALK.equals(walkCopy));
        assertEquals(WALK.hashCode(), walkCopy.hashCode());

        // same object -> returns true/same hashcode
        assertTrue(WALK.equals(WALK));
        assertEquals(WALK.hashCode(), WALK.hashCode());

        // null -> returns false
        assertFalse(WALK.equals(null));

        // different type -> returns false
        assertFalse(WALK.equals(5));

        // different Exercise -> returns false/different hashcode
        assertFalse(WALK.equals(BASKETBALL));
        assertNotEquals(WALK.hashCode(), BASKETBALL.hashCode());

        // different name -> returns false/different hashcode
        Exercise editedWalk = new ExerciseBuilder(WALK).withName(VALID_NAME_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different date -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withDate(VALID_DATE_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different calories -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different quantity -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different quantity -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different tags -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());
    }

    @Test
    public void toString_nonEmptyCustomProperties_returnsCorrectString() {
        Map<String, String> testCustomProperty = new HashMap<>();
        testCustomProperty.put(VALID_PREFIX_NAME_RATING, VALID_PREFIX_NAME_REMARK);
        Exercise typicalExercise = BASKETBALL;
        Exercise testWithCustomProperty = new Exercise(typicalExercise.getName(), typicalExercise.getDate(),
                typicalExercise.getCalories(), typicalExercise.getQuantity(), typicalExercise.getUnit(),
                typicalExercise.getMuscles(), testCustomProperty);


        assertEquals(VALID_BASKETBALL_STRING_WITH_CUSTOM_PROPERTY, testWithCustomProperty.toString());
    }
}
