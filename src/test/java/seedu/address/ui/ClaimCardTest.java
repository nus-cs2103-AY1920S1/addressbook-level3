package seedu.address.ui;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClaimCardTest {

    @Test
    public void constructor_nullClaim_throwsExceptionInInitializerError() {
        assertThrows(ExceptionInInitializerError.class, () -> new ClaimCard(null, 1));
    }

    //    @Test
    //    public void constructor_validClaimAndIndex() {
    //        Claim testClaim = new ClaimBuilder().build();
    //        ClaimCard claimCard = new ClaimCard(testClaim, 1);
    //        ClaimCard similarClaimCard = new ClaimCard(testClaim, 1);
    //        ClaimCard claimCardWithDiffIndex = new ClaimCard(testClaim, 2);
    //        ClaimCard claimCardWithDiffClaim = new ClaimCard(new ClaimBuilder().buildRejected(), 1);
    //
    //        assertNotEquals(claimCard, claimCardWithDiffIndex);
    //        assertNotEquals(claimCard, claimCardWithDiffClaim);
    //
    //        assertEquals(claimCard, claimCard);
    //        assertEquals(claimCard, similarClaimCard);
    //    }

}
