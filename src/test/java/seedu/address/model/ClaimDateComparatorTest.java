package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_CORPORATECLAIMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SCHOOLCLAIMS;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.Claim;
import seedu.address.testutil.ClaimBuilder;

class ClaimDateComparatorTest {

    public static final Claim CLAIM_1 = new ClaimBuilder().withName("John")
            .withDescription("Execution").withAmount(VALID_AMOUNT_CORPORATECLAIMS)
            .withDate("28-09-2019").build();

    public static final Claim CLAIM_2 = new ClaimBuilder().withName("Bob")
            .withDescription("MacPro").withAmount(VALID_AMOUNT_SCHOOLCLAIMS)
            .withDate("30-09-2019").build();

    private ClaimDateComparator claimDateComparator = new ClaimDateComparator();

    @Test
    public void comparator_lessThan_negativeInteger() {
        int result = claimDateComparator.compare(CLAIM_1, CLAIM_2);
        assertEquals(result, -2);
    }

    @Test
    public void comparator_equal_zero() {
        int result = claimDateComparator.compare(CLAIM_1, CLAIM_1);
        assertEquals(result, 0);
    }

    @Test
    public void comparator_moreThan_positiveInteger() {
        int result = claimDateComparator.compare(CLAIM_2, CLAIM_1);
        assertEquals(result, 2);
    }
}
