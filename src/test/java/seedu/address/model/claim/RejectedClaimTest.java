package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClaimBuilder;

//@@author{weigenie}
class RejectedClaimTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RejectedClaim(null,
                null, null, null, null, null));
        assertThrows(NullPointerException.class, () -> new RejectedClaim(null,
                null, null, null, null));
        assertThrows(NullPointerException.class, () -> new RejectedClaim(null));
    }

    @Test
    void canChangeStatus() {
        assertFalse(new ClaimBuilder().buildRejected().canChangeStatus());
    }

    @Test
    void equals() {
        RejectedClaim logisticsClaim = new ClaimBuilder().withDescription("Logistics").buildRejected();
        RejectedClaim validLogisticsClaim = new ClaimBuilder().withDescription("Logistics").buildRejected();
        RejectedClaim transportClaim = new ClaimBuilder().withDescription("Transport").buildRejected();
        RejectedClaim otherLogisticsClaim = new ClaimBuilder().withDescription("Logistics")
                .withDate("04-04-2019")
                .buildRejected();
        PendingClaim pendingLogisticsClaim = new ClaimBuilder().withDescription("Logistics").build();
        ApprovedClaim rejectedLogisticsClaim = new ClaimBuilder().withDescription("Logistics").buildApproved();

        assertNotEquals(logisticsClaim, otherLogisticsClaim); // different date
        assertNotEquals(logisticsClaim, transportClaim); // different description
        assertNotEquals(logisticsClaim, pendingLogisticsClaim); // different class
        assertNotEquals(logisticsClaim, rejectedLogisticsClaim); // different class

        assertEquals(logisticsClaim, logisticsClaim); // same identity
        assertEquals(logisticsClaim, validLogisticsClaim); // same fields
    }
}
