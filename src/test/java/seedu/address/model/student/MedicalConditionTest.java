package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MedicalConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MedicalCondition(null));
    }

    @Test
    public void constructor_invalidMedicalCondition_throwsIllegalArgumentException() {
        String invalidMedicalCondition = "";
        assertThrows(IllegalArgumentException.class, () -> new MedicalCondition(invalidMedicalCondition));
    }

    @Test
    public void isValidMedicalCondition() {
        // null address
        assertThrows(NullPointerException.class, () -> MedicalCondition.isValidMedicalCondition(null));

        // invalid addresses
        assertFalse(MedicalCondition.isValidMedicalCondition("")); // empty string
        assertFalse(MedicalCondition.isValidMedicalCondition(" ")); // spaces only

        // valid addresses
        assertTrue(MedicalCondition.isValidMedicalCondition("Sinus"));
        assertTrue(MedicalCondition.isValidMedicalCondition("-")); // one character
        assertTrue(MedicalCondition.isValidMedicalCondition("Pneumonoultramicroscopicsilicovocanoconiosis"));
        // long medical conditions
    }
}
