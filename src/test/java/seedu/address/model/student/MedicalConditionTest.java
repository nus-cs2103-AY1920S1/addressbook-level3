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
        // null medical condition
        assertThrows(NullPointerException.class, () -> MedicalCondition.isValidMedicalCondition(null));

        // invalid medical conditions
        assertFalse(MedicalCondition.isValidMedicalCondition("")); // empty string
        assertFalse(MedicalCondition.isValidMedicalCondition(" ")); // spaces only

        // valid medical conditions
        assertTrue(MedicalCondition.isValidMedicalCondition("Sinus"));
        assertTrue(MedicalCondition.isValidMedicalCondition("-")); // one character
        assertTrue(MedicalCondition.isValidMedicalCondition("Pneumonoultramicroscopicsilicovocanoconiosis"));
        // long medical conditions
    }
}
