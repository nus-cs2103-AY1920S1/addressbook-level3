package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.ClaimBuilder;

class ApprovedClaimTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ApprovedClaim(null,
                null, null, null, null, null));
        assertThrows(NullPointerException.class, () -> new ApprovedClaim(null,
                null, null, null, null));
        assertThrows(NullPointerException.class, () -> new ApprovedClaim(null));
    }

    @Test
    void canChangeStatus() {
        assertTrue(new ClaimBuilder().build().canChangeStatus());
    }

    @Test
    void equals() {
        ApprovedClaim logisticsClaim = new ClaimBuilder().withDescription("Logistics").buildApproved();
        ApprovedClaim transportClaim = new ClaimBuilder().withDescription("Transport").buildApproved();
        ApprovedClaim otherLogisticsClaim = new ClaimBuilder().withDescription("Logistics")
                .withDate("04-04-2019")
                .withName("Bobby")
                .withAmount("1009")
                .buildApproved();
        PendingClaim approvedLogisticsClaim = new ClaimBuilder().withDescription("Logistics").build();
        RejectedClaim rejectedLogistiscClaim = new ClaimBuilder().withDescription("Logistics").buildRejected();

        assertEquals(logisticsClaim, otherLogisticsClaim);
        assertNotEquals(logisticsClaim, transportClaim);
        assertNotEquals(logisticsClaim, approvedLogisticsClaim);
        assertNotEquals(logisticsClaim, rejectedLogistiscClaim);
    }
}
