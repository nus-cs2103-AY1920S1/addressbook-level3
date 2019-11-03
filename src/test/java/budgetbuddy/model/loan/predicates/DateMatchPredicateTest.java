package budgetbuddy.model.loan.predicates;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import budgetbuddy.testutil.loanutil.TypicalLoans;

public class DateMatchPredicateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateMatchPredicate(null));
    }

    @Test
    public void test_equalDates_returnsTrue() {
        DateMatchPredicate dateMatchPredicate = new DateMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDate());
        assertTrue(dateMatchPredicate.test(TypicalLoans.JOHN_OUT_UNPAID)); // equal dates -> returns true
    }

    @Test
    public void test_unequalDates_returnsFalse() {
        DateMatchPredicate dateMatchPredicate = new DateMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDate());
        assertFalse(dateMatchPredicate.test(TypicalLoans.MARY_IN_UNPAID)); // unequal dates -> returns false
    }

    @Test
    public void equals() {
        // same values -> returns true
        DateMatchPredicate dateMatchPredicate = new DateMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDate());
        assertEquals(dateMatchPredicate, new DateMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDate()));

        // same object -> returns true
        assertEquals(dateMatchPredicate, dateMatchPredicate);

        // null -> returns false
        assertNotEquals(null, dateMatchPredicate);

        // different type -> returns false
        assertNotEquals(10, dateMatchPredicate);

        // different date -> returns false
        assertNotEquals(new DateMatchPredicate(TypicalLoans.PETER_OUT_PAID.getDate()), dateMatchPredicate);
    }
}
