package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClaimBuilder;

//@@author{weigenie}
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
        assertFalse(new ClaimBuilder().buildApproved().canChangeStatus());
    }

    @Test
    void equals() {
        ApprovedClaim logisticsClaim = new ClaimBuilder().withDescription("Logistics").buildApproved();
        ApprovedClaim validLogisticsClaim = new ClaimBuilder().withDescription("Logistics").buildApproved();
        ApprovedClaim transportClaim = new ClaimBuilder().withDescription("Transport").buildApproved();
        ApprovedClaim otherLogisticsClaim = new ClaimBuilder().withDescription("Logistics")
                .withDate("04-04-2019")
                .buildApproved();
        PendingClaim pendingLogisticsClaim = new ClaimBuilder().withDescription("Logistics").build();
        RejectedClaim rejectedLogisticsClaim = new ClaimBuilder().withDescription("Logistics").buildRejected();

        assertNotEquals(logisticsClaim, otherLogisticsClaim); // different date
        assertNotEquals(logisticsClaim, transportClaim); // different description
        assertNotEquals(logisticsClaim, pendingLogisticsClaim); // different class
        assertNotEquals(logisticsClaim, rejectedLogisticsClaim); // different class

        assertEquals(logisticsClaim, logisticsClaim); // same identity
        assertEquals(logisticsClaim, validLogisticsClaim); // same fields
    }
}
