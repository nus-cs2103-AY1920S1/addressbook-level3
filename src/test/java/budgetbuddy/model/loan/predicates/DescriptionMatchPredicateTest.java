package budgetbuddy.model.loan.predicates;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import budgetbuddy.testutil.TypicalLoans;

public class DescriptionMatchPredicateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DescriptionMatchPredicate(null));
    }

    @Test
    public void test_equalDescriptions_returnsTrue() {
        DescriptionMatchPredicate descriptionMatchPredicate =
                new DescriptionMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDescription());
        // equal description -> returns true
        assertTrue(descriptionMatchPredicate.test(TypicalLoans.JOHN_OUT_UNPAID));
    }

    @Test
    public void test_unequalDescriptions_returnsFalse() {
        DescriptionMatchPredicate descriptionMatchPredicate =
                new DescriptionMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDescription());
        // unequal description -> returns false
        assertFalse(descriptionMatchPredicate.test(TypicalLoans.MARY_IN_UNPAID));
    }

    @Test
    public void equals() {
        // same values -> returns true
        DescriptionMatchPredicate descriptionMatchPredicate =
                new DescriptionMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDescription());
        assertEquals(descriptionMatchPredicate,
                new DescriptionMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getDescription()));

        // same object -> returns true
        assertEquals(descriptionMatchPredicate, descriptionMatchPredicate);

        // null -> returns false
        assertNotEquals(null, descriptionMatchPredicate);

        // different type -> returns false
        assertNotEquals(10, descriptionMatchPredicate);

        // different date -> returns false
        assertNotEquals(new DescriptionMatchPredicate(TypicalLoans.PETER_OUT_PAID.getDescription()),
                descriptionMatchPredicate);
    }
}
