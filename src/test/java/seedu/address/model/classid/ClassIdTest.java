package seedu.address.model.classid;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClassIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClassId(null));
    }

    @Test
    public void constructor_invalidClassId_throwsIllegalArgumentException() {
        String invalidClassId = "";
        assertThrows(IllegalArgumentException.class, () -> new ClassId(invalidClassId));
    }

    @Test
    public void isValidClassId() {
        // null classId
        assertThrows(NullPointerException.class, () -> ClassId.isValidClassId(null));

        // invalid classId
        assertFalse(ClassId.isValidClassId("")); // empty string
        assertFalse(ClassId.isValidClassId(" ")); // spaces only

        assertTrue(ClassId.isValidClassId("CS2030"));
        assertTrue(ClassId.isValidClassId("CS1231"));
        assertTrue(ClassId.isValidClassId("CS2030 Tut 7"));
        assertTrue(ClassId.isValidClassId("CS40000000"));
        assertTrue(ClassId.isValidClassId("random class"));
    }
}
