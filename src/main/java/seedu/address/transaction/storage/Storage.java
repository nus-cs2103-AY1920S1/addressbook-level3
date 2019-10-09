package seedu.address.transaction.storage;

import java.io.IOException;

import seedu.address.transaction.util.TransactionList;

/**
 * API of the Storage component
 */
public interface Storage {

    /**
     * Reads in the transaction list from the specified text file in Storage.
     * @return Transaction List read.
     */
    TransactionList readTransactionList();

    /**
     * Write the given transaction list into the specified text file in Storage.
     * @param transactionList Transaction List to write into the file.
     * @throws IOException If an error occurs when writing the file.
     */
    void writeFile(TransactionList transactionList) throws IOException;
}
