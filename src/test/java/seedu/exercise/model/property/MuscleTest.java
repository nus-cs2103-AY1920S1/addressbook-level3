package seedu.exercise.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.CommonTestData;

public class MuscleTest {

    private Muscle testMuscle = new Muscle(CommonTestData.VALID_MUSCLE_BASKETBALL);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Muscle(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Muscle(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Muscle.isValidMuscleName(null));
    }

    @Test
    public void constructor_invalidValues_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, Muscle.MESSAGE_CONSTRAINTS, () ->
                new Muscle(CommonTestData.INVALID_SYMBOLS_FOR_ALPHABETS_AND_SPACES));
        assertThrows(IllegalArgumentException.class, Muscle.MESSAGE_CONSTRAINTS, () ->
                new Muscle(CommonTestData.INVALID_NUMBERS_FOR_ALPHABETS_AND_SPACES));
    }

    @Test
    public void equals_variousScenarios_success() {
        // null value -> false
        assertFalse(testMuscle.equals(null));

        // same object -> true
        assertTrue(testMuscle.equals(testMuscle));

        // same name -> true
        assertTrue(testMuscle.equals(new Muscle(CommonTestData.VALID_MUSCLE_BASKETBALL)));

        // different name -> false
        assertFalse(testMuscle.equals(new Muscle(CommonTestData.VALID_MUSCLE_AEROBICS)));
    }

    @Test
    public void hashCode_variousScenarios_success() {
        //same name -> same hashcode
        assertEquals(testMuscle.hashCode(), new Muscle(CommonTestData.VALID_MUSCLE_BASKETBALL).hashCode());

        //different name -> different hashcode
        assertNotEquals(testMuscle.hashCode(), new Muscle(CommonTestData.VALID_MUSCLE_AEROBICS).hashCode());
    }
}
