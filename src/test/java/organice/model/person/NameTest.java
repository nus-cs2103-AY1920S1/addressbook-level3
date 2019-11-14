package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void toStringTest() {
        assertEquals(new Name(VALID_NAME_PATIENT_BOB).toString(), VALID_NAME_PATIENT_BOB);
    }

    @Test
    public void equals() {
        Name name = new Name(VALID_NAME_PATIENT_BOB);

        assertFalse(name.equals(null));
        assertFalse(name.equals(new Name(VALID_NAME_DOCTOR_AMY)));
        assertTrue(name.equals(name));
        assertTrue(name.equals(new Name(VALID_NAME_PATIENT_BOB)));
    }

    @Test
    public void hashCodeTest() {
        Name name = new Name(VALID_NAME_PATIENT_BOB);

        assertEquals(name.hashCode(), new Name(VALID_NAME_PATIENT_BOB).hashCode());
        assertNotEquals(name.hashCode(), new Name(VALID_NAME_DOCTOR_AMY).hashCode());
    }
}
