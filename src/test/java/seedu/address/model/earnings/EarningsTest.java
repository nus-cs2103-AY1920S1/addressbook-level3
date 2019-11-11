package seedu.address.model.earnings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EARNINGS_CS2100_A01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EARNINGS_CS2100_A01;
import static seedu.address.testutil.TypicalEarnings.CS2103T_EARNINGS;
import static seedu.address.testutil.TypicalEarnings.CS2107_EARNINGS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EarningsBuilder;

public class EarningsTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(CS2103T_EARNINGS.isSameEarnings(CS2103T_EARNINGS));

        // null -> returns false
        assertFalse(CS2103T_EARNINGS.isSameEarnings(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Earnings cs2103TCopy = new EarningsBuilder(CS2103T_EARNINGS).build();
        assertTrue(CS2103T_EARNINGS.equals(cs2103TCopy));

        // same object -> returns true
        assertTrue(CS2103T_EARNINGS.equals(CS2103T_EARNINGS));

        // null -> returns false
        assertFalse(CS2103T_EARNINGS.equals(null));

        // different type -> returns false
        assertFalse(CS2103T_EARNINGS.equals(5));

        // different person -> returns false
        assertFalse(CS2103T_EARNINGS.equals(CS2107_EARNINGS));

        // different name -> returns false
        Earnings editedCS2103T = new EarningsBuilder(CS2103T_EARNINGS).withDate(VALID_DATE_EARNINGS_CS2100_A01).build();
        assertFalse(CS2103T_EARNINGS.equals(editedCS2103T));

        // different picture -> returns false
        editedCS2103T = new EarningsBuilder(CS2103T_EARNINGS).withAmount(VALID_AMOUNT_EARNINGS_CS2100_A01).build();
        assertFalse(CS2103T_EARNINGS.equals(editedCS2103T));
    }

}
