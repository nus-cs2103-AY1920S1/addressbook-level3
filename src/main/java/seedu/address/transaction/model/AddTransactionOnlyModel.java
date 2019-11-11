package seedu.address.transaction.model;

import seedu.address.transaction.model.transaction.Transaction;

/**
 * Acts as a facade that allows only some methods from the Model Manager.
 */
public interface AddTransactionOnlyModel {

    /**
     * Adds a transaction to the transaction model.
     * @param transaction the transaction to be added
     */
    void addTransaction(Transaction transaction);
}

