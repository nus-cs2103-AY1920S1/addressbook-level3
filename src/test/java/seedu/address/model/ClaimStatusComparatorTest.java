package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_CORPORATECLAIMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SCHOOLCLAIMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DURINGMONTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FIRSTDAYOFMONTH;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.Claim;
import seedu.address.testutil.ClaimBuilder;

class ClaimStatusComparatorTest {

    public static final Claim CLAIM_1 = new ClaimBuilder().withName("John")
            .withDescription("Execution").withAmount(VALID_AMOUNT_CORPORATECLAIMS)
            .withDate(VALID_DATE_DURINGMONTH).buildApproved();

    public static final Claim CLAIM_2 = new ClaimBuilder().withName("Bob")
            .withDescription("MacPro").withAmount(VALID_AMOUNT_SCHOOLCLAIMS)
            .withDate(VALID_DATE_FIRSTDAYOFMONTH).buildRejected();

    private ClaimStatusComparator claimStatusComparator = new ClaimStatusComparator();

    @Test
    public void comparator_lessThan_negativeInteger() {
        int result = claimStatusComparator.compare(CLAIM_1, CLAIM_2);
        assertEquals(result, -1);
    }

    @Test
    public void comparator_equal_zero() {
        int result = claimStatusComparator.compare(CLAIM_1, CLAIM_1);
        assertEquals(result, 0);
    }

    @Test
    public void comparator_moreThan_positiveInteger() {
        int result = claimStatusComparator.compare(CLAIM_2, CLAIM_1);
        assertEquals(result, 1);
    }

}
