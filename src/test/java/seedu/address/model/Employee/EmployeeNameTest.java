package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmployeeNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmployeeName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new EmployeeName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> EmployeeName.isValidName(null));

        // invalid name
        assertFalse(EmployeeName.isValidName("")); // empty string
        assertFalse(EmployeeName.isValidName(" ")); // spaces only
        assertFalse(EmployeeName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(EmployeeName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EmployeeName.isValidName("peter jack")); // alphabets only
        assertTrue(EmployeeName.isValidName("12345")); // numbers only
        assertTrue(EmployeeName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(EmployeeName.isValidName("Capital Tan")); // with capital letters
        assertTrue(EmployeeName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
