package seedu.address.transaction.model;

import seedu.address.person.model.person.Person;
import seedu.address.transaction.util.TransactionList;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * Adds the transaction to the transaction list in model
     * @param transaction Transaction to be added.
     */
    void addTransaction(Transaction transaction);

    /**
     * Finds the transaction in the filtered list in model and returns it.
     * @param index Index of the transaction in the filtered list.
     * @return Transaction desired.
     */
    Transaction findTransactionInFilteredListByIndex(int index);

    /**
     * Deletes the transaction according to the index in the filtered list.
     * Deletes the transaction in the transaction list.
     * @param index Index of the filtered list
     */
    void deleteTransaction(int index);

    /**
     * Returns the transaction list by applying the default predicate
     * @return Transaction list.
     */
    TransactionList getTransactionList();

    /**
     * Sets a transaction in transaction list to a new transaction.
     * @param transactionToEdit Transaction to be replaced.
     * @param editedTransaction Transaction to replace {@code transactionToEdit}
     */
    void setTransaction(Transaction transactionToEdit, Transaction editedTransaction);

    /**
     * Checks if the transaction exists in the transaction list.
     * @param editedTransaction Transaction to find
     * @return Boolean if transaction exists in transaction list.
     */
    boolean hasTransaction(Transaction editedTransaction);

    /**
     * Updates all the indexes of the transactions in the transaction list.
     */
    void updateIndexes();

    /**
     * Sorts all the transactions in the transaction list by date.
     */
    void sortByDate();

    /**
     * Sorts all the transactions in the transaction list by name.
     */
    void sortByName();

    /**
     * Sorts all the transactions in the transaction list by amount.
     */
    void sortByAmount();

    /**
     * Resets the order of the transactions in the transaction list to the original order of input when file was read.
     */
    void sortReset();

    /**
     * Returns the filtered list after applying the predicate on the transaction list.
     * @return Filtered list.
     */
    TransactionList getFilteredList();

    /**
     * Updates the predicate in the model to be applied on the transaction list.
     * @param predicate Predicate that was created with the user input keywords.
     */
    void updatePredicate(TransactionContainsKeywordsPredicate predicate);

    /**
     * Resets the predicate to the default one where all is true.
     */
    void resetPredicate();

    /**
     * Deletes all transactions in transaction list that contains the same person as specified.
     * @param person Person to check for in transaction.
     */
    void deleteAllTransactionOfPerson(Person person);
}
