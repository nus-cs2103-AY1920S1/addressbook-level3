package seedu.address.model.notes;

import org.junit.jupiter.api.Test;
import seedu.address.model.note.ClassType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class ClassTypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClassType(null));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidType = " ";
        assertThrows(IllegalArgumentException.class, () -> new ClassType(invalidType));
    }

    @Test
    public void constructor_invalidCombinationType_throwsIllegalArgumentException() {
        String invalidCombinationType = "lec + readings";
        assertThrows(IllegalArgumentException.class, () -> new ClassType(invalidCombinationType));
    }

    @Test
    public void isValidType() {
        // null name
        assertThrows(NullPointerException.class, () -> ClassType.isValidClassType(null));

        // invalid name
        assertFalse(ClassType.isValidClassType("")); // empty string
        assertFalse(ClassType.isValidClassType(" ")); // spaces only
        assertFalse(ClassType.isValidClassType("lecture")); // invalid type characters

        // valid name
        assertTrue(ClassType.isValidClassType("tut")); // lab only
    }
}
