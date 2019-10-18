package seedu.address.model.exercise.detail;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.exercise.details.Repetitions;

public class RepetitionsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Repetitions(null));
    }

    @Test
    public void constructor_invalidRepetitions_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Repetitions(Integer.valueOf(invalidTagName)));
    }

    @Test
    public void isValidExerciseName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Repetitions.isValidExerciseDetail(null));
    }

}
