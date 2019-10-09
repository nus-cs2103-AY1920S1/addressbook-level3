package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BloodtypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Bloodtype(null));
    }

    @Test
    public void constructor_invalidBloodtype_throwsIllegalArgumentException() {
        String invalidBloodtype = "";
        assertThrows(IllegalArgumentException.class, () -> new Bloodtype(invalidBloodtype));
    }

    @Test
    public void isValidBloodtype() {
        // null bloodtype
        assertThrows(NullPointerException.class, () -> Bloodtype.isValidBloodtype(null));

        // invalid bloodtype
        assertFalse(Bloodtype.isValidBloodtype("")); // empty string
        assertFalse(Bloodtype.isValidBloodtype(" ")); // spaces only
        assertFalse(Bloodtype.isValidBloodtype("C")); // wrong bloodtype
        assertFalse(Bloodtype.isValidBloodtype("1")); // numeric
        assertFalse(Bloodtype.isValidBloodtype("C+C")); // + within alphabets
        assertFalse(Bloodtype.isValidBloodtype("A B")); // spaces within alphabets

        // valid bloodtype
        assertTrue(Bloodtype.isValidBloodtype("B")); // exactly 1 alphabet
        assertTrue(Bloodtype.isValidBloodtype("AB")); //2 alphabets
        assertTrue(Bloodtype.isValidBloodtype("Ab")); //case insensitive
        assertTrue(Bloodtype.isValidBloodtype("O+")); // with positive
    }
}
