package seedu.exercise.model.tag;

import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MuscleTest {

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

}
