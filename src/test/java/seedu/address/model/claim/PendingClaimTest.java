package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClaimBuilder;

//@@author{weigenie}
class PendingClaimTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PendingClaim(null,
                null, null, null, null, null));
        assertThrows(NullPointerException.class, () -> new PendingClaim(null,
                null, null, null, null));
    }

    @Test
    void canChangeStatus() {
        assertTrue(new ClaimBuilder().build().canChangeStatus());
    }

    @Test
    void equals() {
        PendingClaim logisticsClaim = new ClaimBuilder().withDescription("Logistics").build();
        PendingClaim validLogisticsClaim = new ClaimBuilder().withDescription("Logistics").build();
        PendingClaim transportClaim = new ClaimBuilder().withDescription("Transport").build();
        PendingClaim otherLogisticsClaim = new ClaimBuilder().withDescription("Logistics")
                .withDate("04-04-2019")
                .build();
        ApprovedClaim approvedLogisticsClaim = new ClaimBuilder().withDescription("Logistics").buildApproved();
        RejectedClaim rejectedLogisticsClaim = new ClaimBuilder().withDescription("Logistics").buildRejected();

        assertNotEquals(logisticsClaim, otherLogisticsClaim); // different date
        assertNotEquals(logisticsClaim, transportClaim); // different description
        assertNotEquals(logisticsClaim, approvedLogisticsClaim); // different class
        assertNotEquals(logisticsClaim, rejectedLogisticsClaim); // different class

        assertEquals(logisticsClaim, logisticsClaim); // same identity
        assertEquals(logisticsClaim, validLogisticsClaim); // same fields
    }
}
