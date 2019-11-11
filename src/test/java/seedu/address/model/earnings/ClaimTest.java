package seedu.address.model.earnings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClaimTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Claim(null));
    }

    @Test
    public void constructor_invalidClaim_throwsIllegalArgumentException() {
        String invalidClaim = "done";
        assertThrows(IllegalArgumentException.class, () -> new Claim(invalidClaim));
    }

    @Test
    public void constructor_invalidClaimWaiting_throwsIllegalArgumentException() {
        String invalidClaim = "waiting";
        assertThrows(IllegalArgumentException.class, () -> new Claim(invalidClaim));
    }

    @Test
    public void isValidAmount() {
        // null claim
        assertThrows(NullPointerException.class, () -> Claim.isValidClaim(null));

        // invalid claim
        assertFalse(Claim.isValidClaim("")); // empty string
        assertFalse(Claim.isValidClaim(" ")); // spaces only
        assertFalse(Claim.isValidClaim("yes")); // letter amount characters
        assertFalse(Claim.isValidClaim("waiting to be processed")); // invalid characters
        assertFalse(Claim.isValidClaim("rejected approved")); // combination of valid claims

        // valid claim
        assertTrue(Claim.isValidClaim("rejected")); // zero only
        assertTrue(Claim.isValidClaim("approved")); // numbers only
        assertTrue(Claim.isValidClaim("pending submission")); // max value
    }
}
