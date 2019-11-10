package budgetbuddy.model.account;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.util.Callback;

/**
 * Represents an account in the account manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Account {

    // Identity fields
    public static final String CURRENCY_SIGN = "$";
    private Name name;
    private Description description;
    private final TransactionList transactionList;
    private BooleanProperty isActiveBooleanProperty = new SimpleBooleanProperty(false);
    private long balance;
    private LongProperty balanceLongProperty = new SimpleLongProperty(0);
    private Set<Category> categoryset = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Account(Name name, Description description, TransactionList transactionList) {
        this(name, description, transactionList, 0);
    }

    /**
     * Every field must be present and not null.
     */
    public Account(Name name, Description description, TransactionList transactionList, long balance) {
        requireAllNonNull(name, transactionList);
        this.name = name;
        this.description = description;
        this.transactionList = transactionList;
        this.balance = balance;
        this.balanceLongProperty.set(balance);
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
     * Checks if the balance is within the range of long.
     * @param balance
     */
    public void checkBalanceValidity(long balance) throws NumberFormatException {
        if (balance >= Long.MAX_VALUE || balance <= Long.MIN_VALUE) {
            throw new NumberFormatException(
                    String.format("The balance is no longer within the valid range.")
            );
        }
    }

    /**
     * Add a transaction to the transactionList.
     * @param toAdd
     */
    public void addTransaction(Transaction toAdd) throws NumberFormatException {
        this.transactionList.add(toAdd);
        if (toAdd.getDirection().equals(Direction.IN)) {
            checkBalanceValidity(balance + toAdd.getAmount().toLong());
            balance = balance + toAdd.getAmount().toLong();
        } else {
            checkBalanceValidity(balance - toAdd.getAmount().toLong());
            balance = balance - toAdd.getAmount().toLong();
        }
        balanceLongProperty.set(balance);
    }

    /**
     * Update the transaction.
     * @param txnIndex
     * @param editedTxn
     */
    public void updateTransaction(Index txnIndex, Transaction editedTxn) {
        Transaction targetedTransaction = transactionList.getTransaction(txnIndex);
        Amount targetedAmount = targetedTransaction.getAmount();
        Direction targetedDirection = targetedTransaction.getDirection();

        if (targetedAmount != editedTxn.getAmount() || targetedDirection != editedTxn.getDirection()) {

            if (targetedDirection.equals(Direction.IN)) {
                balance = balance - targetedAmount.toLong();
            } else {
                balance = balance + targetedAmount.toLong();
            }

            if (editedTxn.getDirection().equals(Direction.IN)) {
                balance = balance + editedTxn.getAmount().toLong();
            } else {
                balance = balance - editedTxn.getAmount().toLong();
            }
        }
        balanceLongProperty.set(balance);
        this.transactionList.setTransaction(txnIndex, editedTxn);
    }

    /**
     * Deletes a transaction from the transactionList.
     * @param toDelete
     */
    public void deleteTransaction(Transaction toDelete) throws NumberFormatException {
        this.transactionList.remove(toDelete);
        if (toDelete.getDirection().equals(Direction.IN)) {
            checkBalanceValidity(balance + toDelete.getAmount().toLong());
            balance = balance - toDelete.getAmount().toLong();
        } else {
            checkBalanceValidity(balance - toDelete.getAmount().toLong());
            balance = balance - toDelete.getAmount().toLong();
        }
        balanceLongProperty.set(balance);
    }

    /**
     * This extractor method helps to update the UI when the internal properties of accounts are changed.
     */
    public static Callback<Account, Observable[]> extractor() {
        return new Callback<Account, Observable[]>() {
            @Override
            public Observable[] call(Account param) {
                return new Observable[]{param.isActiveBooleanProperty, param.balanceLongProperty };
            }
        };
    }

    public boolean isActive() {
        return isActiveBooleanProperty.getValue();
    }

    public void setActive() {
        this.isActiveBooleanProperty.set(true);
    }

    public void setInactive() {
        this.isActiveBooleanProperty.set(false);
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
                && otherAccount.getTransactionList().equals(getTransactionList())
                && otherAccount.getBalance() == getBalance();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, transactionList, balance);
    }

    @Override
    public String toString() {
        return getName().toString() + " (" + getDescription().toString() + ")";
    }

    public String getIncome() {
        long income = 0;
        for (Transaction transaction: transactionList) {
            if (transaction.getDirection().equals(Direction.IN)) {
                income = income + transaction.getAmount().toLong();
            }
        }

        return String.format("%s%d.%02d", CURRENCY_SIGN, income / 100, income % 100);
    }

    public String getExpense() {
        long expense = 0;
        for (Transaction transaction: transactionList) {
            if (transaction.getDirection().equals(Direction.OUT)) {
                expense = expense + transaction.getAmount().toLong();
            }
        }

        return String.format("%s%d.%02d", CURRENCY_SIGN, expense / 100, expense % 100);
    }

    public Set<Category> getCategories() {
        for (Transaction transaction: transactionList) {
            categoryset.addAll(transaction.getCategories());
        }

        return categoryset;
    }

    public String getBalanceString() {
        if (balance >= 0) {
            return String.format("%s%d.%02d", CURRENCY_SIGN, balance / 100, balance % 100);
        } else {
            return "-" + String.format("%s%d.%02d", CURRENCY_SIGN, Math.abs(balance / 100), Math.abs(balance % 100));
        }
    }

    public String getAccountInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Report of Account " + this.toString() + "\n")
                     .append("\n")
                     .append("Total balance: " + this.getBalanceString() + "\n")
                     .append("Income: " + this.getIncome() + "\n")
                     .append("Expenses: " + this.getExpense() + "\n")
                     .append("Categories: " + this.getCategories() + "\n");
        return stringBuilder.toString();
    }
}
