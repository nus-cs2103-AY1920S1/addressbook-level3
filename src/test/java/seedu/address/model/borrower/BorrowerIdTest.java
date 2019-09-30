package seedu.address.model.borrower;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BorrowerIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BorrowerId(null));
    }

    @Test
    public void constructor_invalidBorrowerId_throwsIllegalArgumentException() {
        String invalidBorrowerId = "";
        assertThrows(IllegalArgumentException.class, () -> new BorrowerId(invalidBorrowerId));
    }

    @Test
    public void isValidBorrowerId() {
        // null borrower ID;
        assertThrows(NullPointerException.class, () -> BorrowerId.isValidBorrowerId(null));

        // invalid borrower ID
        assertFalse(BorrowerId.isValidBorrowerId("")); // empty string
        assertFalse(BorrowerId.isValidBorrowerId(" ")); // spaces only
        assertFalse(BorrowerId.isValidBorrowerId("K91")); // less than 3 numbers
        assertFalse(BorrowerId.isValidBorrowerId("BorrowerId")); // non-numeric
        assertFalse(BorrowerId.isValidBorrowerId("9011p041")); // alphabets within digits
        assertFalse(BorrowerId.isValidBorrowerId("9312 1534")); // spaces within digits

        // valid BorrowerId numbers
        assertTrue(BorrowerId.isValidBorrowerId("K0911")); // exactly 3 numbers
        assertTrue(BorrowerId.isValidBorrowerId("K0001")); //smallest Borrower ID
        assertTrue(BorrowerId.isValidBorrowerId("K9999")); // largest Borrower ID
    }
}
