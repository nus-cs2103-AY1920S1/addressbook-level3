package budgetbuddy.model.loan.predicates;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import budgetbuddy.testutil.TypicalLoans;

public class AmountMatchPredicateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AmountMatchPredicate(null));
    }

    @Test
    public void test_equalAmounts_returnsTrue() {
        AmountMatchPredicate amountMatchPredicate = new AmountMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getAmount());
        assertTrue(amountMatchPredicate.test(TypicalLoans.JOHN_OUT_UNPAID)); // equal amounts -> returns true
    }

    @Test
    public void test_unequalAmounts_returnsFalse() {
        AmountMatchPredicate amountMatchPredicate = new AmountMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getAmount());
        assertFalse(amountMatchPredicate.test(TypicalLoans.MARY_IN_UNPAID)); // unequal amounts -> returns false
    }

    @Test
    public void equals() {
        // same values -> returns true
        AmountMatchPredicate amountMatchPredicate = new AmountMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getAmount());
        assertEquals(amountMatchPredicate, new AmountMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getAmount()));

        // same object -> returns true
        assertEquals(amountMatchPredicate, amountMatchPredicate);

        // null -> returns false
        assertNotEquals(null, amountMatchPredicate);

        // different type -> returns false
        assertNotEquals(10, amountMatchPredicate);

        // different amount -> returns false
        assertNotEquals(new AmountMatchPredicate(TypicalLoans.PETER_OUT_PAID.getAmount()), amountMatchPredicate);
    }
}
