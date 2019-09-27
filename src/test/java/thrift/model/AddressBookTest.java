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

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTransactionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = TypicalTransactions.getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTransaction(TypicalTransactions.LAKSA));
    }

    @Test
    public void hasTransaction_transactionInAddressBook_returnsTrue() {
        addressBook.addTransaction(TypicalTransactions.LAKSA);
        assertTrue(addressBook.hasTransaction(TypicalTransactions.LAKSA));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTransactionList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose transaction list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        AddressBookStub(Collection<Transaction> transactions) {
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return transactions;
        }
    }

}
