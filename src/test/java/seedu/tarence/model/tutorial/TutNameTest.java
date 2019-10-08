package seedu.tarence.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TutNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutName(null));
    }

    @Test
    public void constructor_invalidTutName_throwsIllegalArgumentException() {
        String invalidTutName = "";
        assertThrows(IllegalArgumentException.class, () -> new TutName(invalidTutName));
    }

    @Test
    public void isValidTutName() {
        // null TutName
        assertThrows(NullPointerException.class, () -> TutName.isValidTutName(null));

        // blank TutName
        assertFalse(TutName.isValidTutName("")); // empty string
        assertFalse(TutName.isValidTutName(" ")); // spaces only

        // valid TutName
        assertTrue(TutName.isValidTutName("Lab 1"));
    }
}
