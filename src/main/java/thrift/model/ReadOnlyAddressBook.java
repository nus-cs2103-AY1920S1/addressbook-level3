package thrift.model;

import javafx.collections.ObservableList;
import thrift.model.transaction.Transaction;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the transactions list.
     */
    ObservableList<Transaction> getTransactionList();

}
