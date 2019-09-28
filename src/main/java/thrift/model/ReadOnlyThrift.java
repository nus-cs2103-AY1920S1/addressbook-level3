package thrift.model;

import javafx.collections.ObservableList;
import thrift.model.transaction.Transaction;

/**
 * Unmodifiable view of THRIFT.
 */
public interface ReadOnlyThrift {

    /**
     * Returns an unmodifiable view of the transactions list.
     */
    ObservableList<Transaction> getTransactionList();

}
