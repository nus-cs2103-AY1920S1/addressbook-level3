package budgetbuddy.model.loan;

import static budgetbuddy.model.loan.LoanFilters.getDirectionPredicate;
import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import budgetbuddy.model.attributes.Direction;
import budgetbuddy.testutil.TypicalLoans;

public class LoanFiltersTest {

    @Test
    public void getDirectionPredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> getDirectionPredicate(null));
    }

    @Test
    public void getDirectionPredicate_invalidDirection_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> getDirectionPredicate(Direction.valueOf("")));
    }

    @Test
    public void getDirectionPredicate_validDirection_returnsCorrectPredicate() {
        // pass in Direction.IN -> test against Loan with Direction.IN returns true
        assertTrue(getDirectionPredicate(Direction.IN).test(TypicalLoans.MARY_IN_UNPAID));

        // pass in Direction.OUT -> test against Loan with Direction.OUT returns true
        assertTrue(getDirectionPredicate(Direction.OUT).test(TypicalLoans.JOHN_OUT_UNPAID));
    }
}
