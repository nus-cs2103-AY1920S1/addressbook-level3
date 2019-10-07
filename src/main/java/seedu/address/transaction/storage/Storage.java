package seedu.address.transaction.storage;

import seedu.address.transaction.util.TransactionList;

public interface Storage {

    public TransactionList getTransactionList();
}
