package seedu.address.transaction.model;

import seedu.address.transaction.model.exception.NoSuchIndexException;

public interface Model {

    void addTransaction(Transaction trans);

    Transaction findTransactionByIndex(int index) throws NoSuchIndexException;

    void deleteTransaction(int index);

    void writeInFile() throws Exception;
}
