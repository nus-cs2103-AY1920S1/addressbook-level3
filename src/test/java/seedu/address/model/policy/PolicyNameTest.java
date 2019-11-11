package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PolicyNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PolicyName(null));
    }

    @Test
    public void constructor_invalidPolicyName_throwsIllegalArgumentException() {
        String invalidPolicyName = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyName(invalidPolicyName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> PolicyName.isValidName(null));

        // invalid name
        assertFalse(PolicyName.isValidName("")); // empty string
        assertFalse(PolicyName.isValidName(" ")); // spaces only
        assertFalse(PolicyName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(PolicyName.isValidName("health*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PolicyName.isValidName("health insured")); // alphabets only
        assertTrue(PolicyName.isValidName("12345")); // numbers only
        assertTrue(PolicyName.isValidName("health the 2nd")); // alphanumeric characters
        assertTrue(PolicyName.isValidName("Health Insurance")); // with capital letters
        assertTrue(PolicyName.isValidName("Fire Insurance of personal mobility devices")); // long names
    }
}
