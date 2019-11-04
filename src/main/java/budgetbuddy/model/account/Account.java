package budgetbuddy.model.account;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;

/**
 * Represents an account in the account manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Account {

    // Identity fields
    private Name name;
    private Description description;
    private final TransactionList transactionList;
    private boolean isActive = false;
    private long balance;

    /**
     * Every field must be present and not null.
     */
    public Account(Name name, Description description, TransactionList transactionList, long balance) {
        requireAllNonNull(name, transactionList);
        this.name = name;
        this.description = description;
        this.transactionList = transactionList;
        this.balance = balance;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public TransactionList getTransactionList() {
        return transactionList;
    }

    public Transaction getTransaction(Index toGet) {
        return this.transactionList.getTransaction(toGet);
    }

    /**
     * Add a transaction to the transactionList.
     * @param toAdd
     */
    public void addTransaction(Transaction toAdd) {
        this.transactionList.add(toAdd);
        if (toAdd.getDirection().equals(Direction.IN)) {
            balance = balance + toAdd.getAmount().toLong();
        } else {
            balance = balance - toAdd.getAmount().toLong();
        }
    }

    /**
     * Update the transaction.
     * @param txnIndex
     * @param editedTxn
     */
    public void updateTransaction(Index txnIndex, Transaction editedTxn) {
        this.transactionList.setTransaction(txnIndex, editedTxn);
    }

    /**
     * Deletes a transaction from the transactionList.
     * @param toDelete
     */
    public void deleteTransaction(Transaction toDelete) {
        this.transactionList.remove(toDelete);
        if (toDelete.getDirection().equals(Direction.IN)) {
            balance = balance - toDelete.getAmount().toLong();
        } else {
            balance = balance - toDelete.getAmount().toLong();
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive() {
        this.isActive = true;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return otherAccount.getName().equals(getName())
                && otherAccount.getDescription().equals(getDescription())
                && otherAccount.getTransactionList().equals(getTransactionList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, transactionList, balance);
    }

    @Override
    public String toString() {
        return getName().toString() + " (" + getDescription().toString() + ")";
    }


}

