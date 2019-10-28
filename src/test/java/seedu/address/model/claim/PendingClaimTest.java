package seedu.address.model.claim;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class PendingClaimTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PendingClaim(null,
                null, null, null, null, null));
    }

    //    @Test
    //    void canChangeStatus() {
    //    }
}
