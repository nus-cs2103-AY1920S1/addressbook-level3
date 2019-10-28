package seedu.address.model.claim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

class PendingClaimTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PendingClaim(null ,null, null, null, null, null));
    }

//    @Test
//    void canChangeStatus() {
//    }
}