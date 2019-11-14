package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmployeePhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmployeePhone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new EmployeePhone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> EmployeePhone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(EmployeePhone.isValidPhone("")); // empty string
        assertFalse(EmployeePhone.isValidPhone(" ")); // spaces only
        assertFalse(EmployeePhone.isValidPhone("91")); // less than 3 numbers
        assertFalse(EmployeePhone.isValidPhone("phone")); // non-numeric
        assertFalse(EmployeePhone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(EmployeePhone.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(EmployeePhone.isValidPhone("9112456")); // 7 numbers
        assertTrue(EmployeePhone.isValidPhone("93121534"));
        assertTrue(EmployeePhone.isValidPhone("124293842033123")); // long phone numbers
    }
}
