package budgetbuddy.model.account;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import javafx.collections.ObservableList;

/**
 * Represents an account in the account manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {

    // Identity fields
    private Name name;
    private Description description;
    private final TransactionList transactionList;


    /**
     * Every field must be present and not null.
     */
    public Account(Name name, Description description, TransactionList transactionList) {
        requireAllNonNull(name, transactionList);
        this.name = name;
        this.description = description;
        this.transactionList = transactionList;
    }

    public static Account getDefaultAccount() {
        //TODO implement getDefaultAccount which returns the default account
        return new Account(new Name("DEFAULT"), new Description("null"), new TransactionList());
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public ObservableList<Transaction> getTransactionList() {
        return transactionList.asUnmodifiableObservableList();
    }

    public void setName(Account account, Name name) {
        this.name = name;
    }

    public void addTransaction(Transaction toAdd) {
        this.transactionList.add(toAdd);
    }

    public void deleteTransaction(Transaction toDelete) {
        this.transactionList.remove(toDelete);
    }
    /**
     * Returns true if both accounts of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two accounts, where no 2 accounts should have the same
     * name or the same transaction list.
     */
    public boolean isSameAccount(Account otherAccount) {
        if (otherAccount == this) {
            return true;
        }

        return otherAccount != null
                && (otherAccount.getName().equals(getName())
                    || otherAccount.getTransactionList().equals(getTransactionList()));
    }

    /**
     * Returns true if both accounts have the same identity and data fields.
     * This defines a stronger notion of equality between two accounts.
     */
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
                && otherAccount.getTransactionList().equals(getTransactionList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, transactionList);
    }

    @Override
    public String toString() {
        return getName().toString() + " (" + getDescription().toString() + ")";
    }
}

