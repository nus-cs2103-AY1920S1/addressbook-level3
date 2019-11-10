package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_CORPORATECLAIMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SCHOOLCLAIMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DURINGMONTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FIRSTDAYOFMONTH;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.Claim;
import seedu.address.testutil.ClaimBuilder;

public class ClaimNameComparatorTest {

    public static final Claim CLAIM_1 = new ClaimBuilder().withName("John")
            .withDescription("Execution").withAmount(VALID_AMOUNT_CORPORATECLAIMS)
            .withDate(VALID_DATE_DURINGMONTH).build();

    public static final Claim CLAIM_2 = new ClaimBuilder().withName("Bob")
            .withDescription("MacPro").withAmount(VALID_AMOUNT_SCHOOLCLAIMS)
            .withDate(VALID_DATE_FIRSTDAYOFMONTH).build();

    private ClaimNameComparator claimNameComparator = new ClaimNameComparator();

    @Test
    public void lessThan() {
        int result = claimNameComparator.compare(CLAIM_1, CLAIM_2);
        assertEquals(result, -8);
    }

    @Test
    public void equal() {
        int result = claimNameComparator.compare(CLAIM_1, CLAIM_1);
        assertEquals(result, 0);
    }

    @Test
    public void moreThan() {
        int result = claimNameComparator.compare(CLAIM_2, CLAIM_1);
        assertEquals(result, 8);
    }


}
