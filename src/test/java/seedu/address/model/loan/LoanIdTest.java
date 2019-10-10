package seedu.address.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LoanIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanId(null));
    }

    @Test
    public void constructor_invalidLoanId_throwsIllegalArgumentException() {
        String invalidLoanId = "";
        assertThrows(IllegalArgumentException.class, () -> new LoanId(invalidLoanId));
    }

    @Test
    public void isValidLoanId() {
        // null loan ID
        assertThrows(NullPointerException.class, () -> LoanId.isValidLoanId(null));

        // invalid loan IDs
        assertFalse(LoanId.isValidLoanId("")); // empty string
        assertFalse(LoanId.isValidLoanId(" ")); // spaces only
        assertFalse(LoanId.isValidLoanId("L91")); // less than 6 numbers
        assertFalse(LoanId.isValidLoanId("L1234567")); // more than 6 numbers
        assertFalse(LoanId.isValidLoanId("C000001")); // different prefix
        assertFalse(LoanId.isValidLoanId("loanId")); // non-numeric
        assertFalse(LoanId.isValidLoanId("L123a56")); // alphabets within digits
        assertFalse(LoanId.isValidLoanId("L123 456")); // spaces within digits

        // valid loan IDs
        assertTrue(LoanId.isValidLoanId("L000911")); // exactly 6 numbers
        assertTrue(LoanId.isValidLoanId("L000001")); // smallest loan ID
        assertTrue(LoanId.isValidLoanId("L999999")); // largest loan ID
    }

    @Test
    public void toString_correctStringRepresentation_returnsTrue() {
        String loanIdString = "L123456";
        assertEquals(new LoanId(loanIdString).toString(), loanIdString);
    }

    @Test
    public void equals_sameLoanId_returnsTrue() {
        String loanIdString = "L123456";
        assertEquals(new LoanId(loanIdString), new LoanId(loanIdString));
    }

    @Test
    public void hashCode_sameLoanIdSameHashCode_assertEquals() {
        String loanIdString = "L123456";
        LoanId loanId1 = new LoanId(loanIdString);
        LoanId loanId2 = new LoanId(loanIdString);
        assertEquals(loanId1.hashCode(), loanId2.hashCode());
    }
}