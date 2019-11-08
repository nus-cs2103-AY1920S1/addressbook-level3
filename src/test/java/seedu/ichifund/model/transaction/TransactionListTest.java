package seedu.ichifund.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_ALLOWANCE;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_BUS;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ichifund.model.transaction.exceptions.TransactionNotFoundException;

public class TransactionListTest {

    private final TransactionList transactionList = new TransactionList();

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, TRANSACTION_ALLOWANCE));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(TRANSACTION_ALLOWANCE, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.setTransaction(TRANSACTION_ALLOWANCE,
                TRANSACTION_ALLOWANCE));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(TRANSACTION_ALLOWANCE);
        transactionList.setTransaction(TRANSACTION_ALLOWANCE, TRANSACTION_ALLOWANCE);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(TRANSACTION_ALLOWANCE);
        assertEquals(expectedTransactionList, transactionList);
    }


    @Test
    public void setTransaction_editedTransactionIsDifferentTransaction_success() {
        transactionList.add(TRANSACTION_ALLOWANCE);
        transactionList.setTransaction(TRANSACTION_ALLOWANCE, TRANSACTION_BUS);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(TRANSACTION_BUS);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(TRANSACTION_ALLOWANCE));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(TRANSACTION_ALLOWANCE);
        transactionList.remove(TRANSACTION_ALLOWANCE);
        TransactionList expectedTransactionList = new TransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((TransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(TRANSACTION_ALLOWANCE);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(TRANSACTION_BUS);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((List<Transaction>) null));
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(TRANSACTION_ALLOWANCE);
        List<Transaction> list = Collections.singletonList(TRANSACTION_BUS);
        transactionList.setTransactions(list);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(TRANSACTION_BUS);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> transactionList.asUnmodifiableObservableList().remove(0));
    }
}
