package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EquipmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Equipment(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Equipment(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Equipment.isValidEquipmentName(null));

        // invalid name
        assertFalse(CcaName.isValidName("")); // empty string
        assertFalse(CcaName.isValidName(" ")); // spaces only
        assertFalse(CcaName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(CcaName.isValidName("paddles*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(CcaName.isValidName("dance shoes")); // alphabets only
        assertTrue(CcaName.isValidName("2000")); // numbers only
        assertTrue(CcaName.isValidName("2 pairs of shoes each")); // alphanumeric characters
        assertTrue(CcaName.isValidName("Loud Singing")); // with capital letters
        assertTrue(CcaName.isValidName("K1 nelo boat with rudder attached")); // long names
    }
}
