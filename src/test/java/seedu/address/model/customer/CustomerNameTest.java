package seedu.address.model.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CustomerNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CustomerName(null));
    }

    @Test
    public void constructor_invalidCustomerName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new CustomerName(invalidName));
    }

    @Test
    public void isValidCustomerName() {
        // null name
        assertThrows(NullPointerException.class, () -> CustomerName.isValidCustomerName(null));

        // invalid name
        assertFalse(CustomerName.isValidCustomerName("")); // empty string
        assertFalse(CustomerName.isValidCustomerName(" ")); // spaces only
        assertFalse(CustomerName.isValidCustomerName("^")); // only non-alphanumeric characters
        assertFalse(CustomerName.isValidCustomerName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(CustomerName.isValidCustomerName("peter jack")); // alphabets only
        assertTrue(CustomerName.isValidCustomerName("12345")); // numbers only
        assertTrue(CustomerName.isValidCustomerName("peter the 2nd")); // alphanumeric characters
        assertTrue(CustomerName.isValidCustomerName("Capital Tan")); // with capital letters
        assertTrue(CustomerName.isValidCustomerName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
