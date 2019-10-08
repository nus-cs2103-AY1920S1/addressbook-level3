package thrift.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionList;

/**
 * Wraps all data at the THRIFT level
 */
public class Thrift implements ReadOnlyThrift {

    private final TransactionList transactions;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        transactions = new TransactionList();
    }

    public Thrift() {}

    /**
     * Creates an THRIFT using the Transaction in the {@code toBeCopied}
     */
    public Thrift(ReadOnlyThrift toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the transaction list with {@code transactions}.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }

    /**
     * Resets the existing data of this transactions list with {@code newData}.
     */
    public void resetData(ReadOnlyThrift newData) {
        requireNonNull(newData);

        setTransactions(newData.getTransactionList());
    }

    //// transaction-level operations

    /**
     * Adds a transaction to THRIFT.
     */
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in THRIFT.
     * The transaction identity of {@code editedTransaction} must not be the same as another existing transaction in
     * THRIFT.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);

        transactions.setTransaction(target, editedTransaction);
    }

    /**
     * Returns true if the specified transaction exists in the transactions list.
     */
    public boolean hasTransaction(Transaction t) {
        requireNonNull(t);
        return transactions.contains(t);
    }

    /**
     * Removes {@code key} from this {@code Thrift}.
     * {@code key} must exist in THRIFT.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Thrift // instanceof handles nulls
                && transactions.equals(((Thrift) other).transactions));
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }
}
