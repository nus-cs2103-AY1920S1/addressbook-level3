package seedu.address.transaction.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.TransactionList;

class TransactionListTest {
    private final TransactionList transactionList = new TransactionList();
    private TransactionList unsorted = new TransactionList();


    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), transactionList.getTarrList());
    }


    @Test
    public void deleteTransaction_outOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> transactionList.delete(1));
    }


    @Test
    public void addNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void setTransactionList_successful() {
        transactionList.add(TypicalTransactions.CARL_TRANSACTION_5);
        transactionList.set(0, TypicalTransactions.BENSON_TRANSACTION_2);
        TransactionList expected = new TransactionList();
        expected.add(TypicalTransactions.BENSON_TRANSACTION_2);
        assertEquals(transactionList, expected);
    }

    @Test
    public void setTransactionList_outOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                transactionList.set(2, TypicalTransactions.CARL_TRANSACTION_5));
    }

    @Test
    public void setNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.set(0, null));
    }

    @Test
    public void sort_successful() {
        unsorted.add(TypicalTransactions.BENSON_TRANSACTION_2);
        unsorted.add(TypicalTransactions.ALICE_TRANSACTION_3);
        unsorted.add(TypicalTransactions.CARL_TRANSACTION_5);
        unsorted.add(TypicalTransactions.ALICE_TRANSACTION_1);
        unsorted.sortByName();
        TransactionList expected = new TransactionList();
        expected.add(TypicalTransactions.ALICE_TRANSACTION_3);
        expected.add(TypicalTransactions.ALICE_TRANSACTION_1);
        expected.add(TypicalTransactions.BENSON_TRANSACTION_2);
        expected.add(TypicalTransactions.CARL_TRANSACTION_5);
        assertEquals(unsorted.getTarrList(), expected.getTarrList());

        unsorted.sortByAmount();
        expected = new TransactionList();
        expected.add(TypicalTransactions.BENSON_TRANSACTION_2);
        expected.add(TypicalTransactions.ALICE_TRANSACTION_3);
        expected.add(TypicalTransactions.CARL_TRANSACTION_5);
        expected.add(TypicalTransactions.ALICE_TRANSACTION_1);
        assertEquals(unsorted.getTarrList(), expected.getTarrList());


        unsorted.sortByDate();
        expected = new TransactionList();
        expected.add(TypicalTransactions.ALICE_TRANSACTION_1);
        expected.add(TypicalTransactions.ALICE_TRANSACTION_3);
        expected.add(TypicalTransactions.BENSON_TRANSACTION_2);
        expected.add(TypicalTransactions.CARL_TRANSACTION_5);
        assertEquals(unsorted.getTarrList(), expected.getTarrList());

        unsorted.unSort();
        expected = new TransactionList();
        expected.add(TypicalTransactions.BENSON_TRANSACTION_2);
        expected.add(TypicalTransactions.ALICE_TRANSACTION_3);
        expected.add(TypicalTransactions.CARL_TRANSACTION_5);
        expected.add(TypicalTransactions.ALICE_TRANSACTION_1);
        assertEquals(unsorted, expected);
    }

    @Test
    public void unmodifiableList_exception() {
        transactionList.setAsUnmodifiable();
        assertThrows(UnsupportedOperationException.class, () -> transactionList.delete(1));
        assertThrows(UnsupportedOperationException.class, () ->
                transactionList.set(1, TypicalTransactions.ELLE_TRANSACTION_6));
        assertThrows(UnsupportedOperationException.class, () ->
                transactionList.add(TypicalTransactions.FIONA_TRANSACTION_8));
    }
}
