package thrift.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thrift.model.transaction.Transaction;
import thrift.testutil.TypicalTransactions;

public class ThriftTest {

    private final Thrift thrift = new Thrift();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), thrift.getTransactionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> thrift.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyThrift_replacesData() {
        Thrift newData = TypicalTransactions.getTypicalThrift();
        thrift.resetData(newData);
        assertEquals(newData, thrift);
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> thrift.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInThrift_returnsFalse() {
        assertFalse(thrift.hasTransaction(TypicalTransactions.LAKSA));
    }

    @Test
    public void hasTransaction_transactionInThrift_returnsTrue() {
        thrift.addTransaction(TypicalTransactions.LAKSA);
        assertTrue(thrift.hasTransaction(TypicalTransactions.LAKSA));
    }

    @Test
    public void getTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> thrift.getTransactionList().remove(0));
    }

    /**
     * A stub ReadOnlyThrift whose transaction list can violate interface constraints.
     */
    private static class ThriftStub implements ReadOnlyThrift {
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        ThriftStub(Collection<Transaction> transactions) {
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return transactions;
        }
    }

}
