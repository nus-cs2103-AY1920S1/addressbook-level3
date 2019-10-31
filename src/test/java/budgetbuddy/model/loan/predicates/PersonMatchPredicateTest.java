package budgetbuddy.model.loan.predicates;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import budgetbuddy.testutil.TypicalLoans;

public class PersonMatchPredicateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonMatchPredicate(null));
    }

    @Test
    public void test_equalPersons_returnsTrue() {
        PersonMatchPredicate personMatchPredicate =
                new PersonMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getPerson());
        // equal person -> returns true
        assertTrue(personMatchPredicate.test(TypicalLoans.JOHN_OUT_UNPAID));
    }

    @Test
    public void test_unequalPersons_returnsFalse() {
        PersonMatchPredicate personMatchPredicate =
                new PersonMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getPerson());
        assertFalse(personMatchPredicate.test(TypicalLoans.MARY_IN_UNPAID)); // unequal person -> returns false
    }

    @Test
    public void equals() {
        // same values -> returns true
        PersonMatchPredicate personMatchPredicate =
                new PersonMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getPerson());
        assertEquals(personMatchPredicate,
                new PersonMatchPredicate(TypicalLoans.JOHN_OUT_UNPAID.getPerson()));

        // same object -> returns true
        assertEquals(personMatchPredicate, personMatchPredicate);

        // null -> returns false
        assertNotEquals(null, personMatchPredicate);

        // different type -> returns false
        assertNotEquals(10, personMatchPredicate);

        // different person -> returns false
        assertNotEquals(new PersonMatchPredicate(TypicalLoans.PETER_OUT_PAID.getPerson()),
                personMatchPredicate);
    }
}
