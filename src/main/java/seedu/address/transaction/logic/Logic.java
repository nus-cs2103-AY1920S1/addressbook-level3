package seedu.address.transaction.logic;

import java.io.IOException;

import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.util.CommandResult;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws Exception If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws Exception;

    /**
     * Writes the transaction list into the transactionHistory text file.
     * @throws Exception If an error occurs when writing into the text file.
     */
    void writeIntoTransactionFile() throws IOException;

    /**
     *Sets the transaction in the transaction list to a new transaction to replace it.
     * @param transaction Transaction in the transaction list to be replaced.
     * @param newTransaction Transaction to replace {@code transaction}
     */
    void setTransaction(Transaction transaction, Transaction newTransaction);

    /**
     * Returns the transaction list in the model manager.
     * @return Transaction List in the model manager.
     */
    TransactionList getTransactionList();

    /**
     * Returns the filtered list of the transaction list by applying the predicate in model manager.
     * @return Filtered list according to predicate in model manager.
     */
    TransactionList getFilteredList();

    /**
     * Adds a transaction into the transaction list in model manager.
     * @param transaction Transaction to be added.
     */
    void addTransaction(Transaction transaction);

    /**
     * Updates storage in transaction package.
     * @throws IOException
     */
    void updateTransactionStorage() throws IOException;

    /**
     * Appends a transaction to the storage.
     * @param transaction Transaction to be appended.
     */
    public void appendToTransactionFile(Transaction transaction) throws Exception;
}
