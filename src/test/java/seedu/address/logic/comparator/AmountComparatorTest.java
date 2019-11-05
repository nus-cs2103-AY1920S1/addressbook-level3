package seedu.address.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.BENSON;
import static seedu.address.testutil.TypicalTransactions.CARL;

import org.junit.jupiter.api.Test;

public class AmountComparatorTest {

    private AmountComparator amountComparator = new AmountComparator();

    @Test
    public void compare_equalToBenson_returnsTrue() {
        assertTrue(amountComparator.compare(BENSON, BENSON) == 0);
    }

    @Test
    public void compare_lessThanBenson_returnsTrue() {
        assertTrue(amountComparator.compare(BENSON, ALICE) > 0);
    }

    @Test
    public void compare_lessThanBenson_returnsFalse() {
        assertFalse(amountComparator.compare(BENSON, CARL) > 0);
    }

    @Test
    public void compare_nullBankAccountOperations_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> amountComparator.compare(null, null));
    }

    @Test
    public void equals_sameIdentityAmountComparator_isEqual() {
        assertEquals(amountComparator, new AmountComparator());
    }
}
