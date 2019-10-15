package seedu.address.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // invalid addresses
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only

        // valid addresses
        assertTrue(Category.isValidCategory("Blk 456, Den Road, #01-355"));
        assertTrue(Category.isValidCategory("-")); // one character
        assertTrue(Category.isValidCategory("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
