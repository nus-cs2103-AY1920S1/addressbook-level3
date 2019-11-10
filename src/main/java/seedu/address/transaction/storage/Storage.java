package seedu.address.transaction.storage;

import java.io.IOException;

import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.storage.exception.FileReadException;

/**
 * API of the Storage component
 */
public interface Storage {

    /**
     * Reads in the transaction list from the specified text file in Storage.
     * @return Transaction List read.
     */
    TransactionList readTransactionList() throws FileReadException;

    /**
     * Write the given transaction list into the specified text file in Storage.
     * @param transactionList Transaction List to write into the file.
     * @throws IOException If an error occurs when writing the file.
     */
    void writeFile(TransactionList transactionList) throws IOException;

    /**
     * Appends the specified transaction to the data file.
     * @param transaction the transaction to be written to file
     * @throws FileReadException If an error occurs while reading the file
     * @throws IOException If an error occurs when writing to the file
     */
    void appendToTransaction(Transaction transaction) throws FileReadException, IOException;

}
