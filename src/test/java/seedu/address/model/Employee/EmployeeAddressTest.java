package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmployeeAddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmployeeAddress(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new EmployeeAddress(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> EmployeeAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(EmployeeAddress.isValidAddress("")); // empty string
        assertFalse(EmployeeAddress.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(EmployeeAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(EmployeeAddress.isValidAddress("-")); // one character
        assertTrue(EmployeeAddress.isValidAddress("Leng Inc; "
                + "1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
