package thrift.model;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import thrift.commons.core.index.Index;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetList;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionList;

/**
 * Wraps all data at the THRIFT level
 */
public class Thrift implements ReadOnlyThrift {

    private final TransactionList transactions;
    private final BudgetList budgets;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        transactions = new TransactionList();
        budgets = new BudgetList();
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
     * Replaces the contents of the budget list with {@code budgets}.
     */
    public void setBudgets(BudgetList budgets) {
        this.budgets.setBudgets(budgets);
    }

    /**
     * Resets the existing data of this transactions list with {@code newData}.
     */
    public void resetData(ReadOnlyThrift newData) {
        requireNonNull(newData);
        setTransactions(newData.getTransactionList());
        setBudgets(newData.getBudgetList());
    }

    //// transaction-level operations

    /**
     * Adds a transaction to THRIFT.
     */
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    /**
     * Adds a transaction to a specified index in THRIFT.
     */
    public void addTransaction(Transaction t, Index index) {
        transactions.add(t, index);
    }

    /**
     * Adds the specified {@code budget} into the budget list, updates the budget instead if it already exists.
     *
     * @param budget budget to be replaced with.
     * @return replaced budget wrapped in optional.
     */
    public Optional<Budget> setBudget(Budget budget) {
        requireNonNull(budget);
        return budgets.setBudget(budget);
    }

    /**
     * Removes {@code budget} from budgets.
     *
     * @param budget is the budget to be removed.
     */
    public void removeBudget(Budget budget) {
        requireNonNull(budget);
        budgets.removeBudget(budget);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code updatedTransaction}.
     * {@code target} must exist in THRIFT.
     */
    public void setTransaction(Transaction target, Transaction updatedTransaction) {
        requireAllNonNull(target, updatedTransaction);
        transactions.setTransaction(target, updatedTransaction);
    }

    /**
     * Replaces the given transaction {@code actualIndex} in the list with {@code updatedTransaction}.
     * {@code actualIndex} must be a valid index.
     */
    public void setTransactionWithIndex(Index actualIndex, Transaction updatedTransaction) {
        requireAllNonNull(actualIndex, updatedTransaction);
        transactions.setTransactionWithIndex(actualIndex, updatedTransaction);
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

    /**
     * Removes last transaction from this {@code Thrift}.
     */
    public void removeLastTransaction() {
        transactions.removeLast();
    }

    /**
     * Remove the transaction from the list based on the index.
     */
    public void removeTransactionByIndex(Index index) {
        transactions.removeByIndex(index);
    }

    //// util methods

    /**
     * Returns an Optional that contains the {@link Index} of the {@code transaction}.
     *
     * @param transaction is the transaction that you are interested in its index in the full transaction list.
     * @return an Optional containing the index of the transaction.
     */
    public Optional<Index> getTransactionIndex(Transaction transaction) {
        requireNonNull(transaction);
        return transactions.getIndex(transaction);
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public BudgetList getBudgetList() {
        return budgets;
    }

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Thrift // instanceof handles nulls
                && transactions.equals(((Thrift) other).transactions)
                && budgets.equals(((Thrift) other).budgets));
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }
}
