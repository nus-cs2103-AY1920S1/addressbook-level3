package seedu.address.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.BENSON;
import static seedu.address.testutil.TypicalTransactions.CARL;

import org.junit.jupiter.api.Test;

public class DateComparatorTest {

    private DateComparator dateComparator = new DateComparator();

    @Test
    public void compare_equalToBenson_returnsTrue() {
        assertTrue(dateComparator.compare(BENSON, BENSON) == 0);
    }

    @Test
    public void compare_lessThanBenson_returnsTrue() {
        assertTrue(dateComparator.compare(BENSON, ALICE) > 0);
    }

    @Test
    public void compare_lessThanBenson_returnsFalse() {
        assertFalse(dateComparator.compare(BENSON, CARL) > 0);
    }

    @Test
    public void compare_nullBankAccountOperations_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dateComparator.compare(null, null));
    }

    @Test
    public void equals_sameIdentityAmountComparator_isEqual() {
        assertEquals(dateComparator, new DateComparator());
    }
}
