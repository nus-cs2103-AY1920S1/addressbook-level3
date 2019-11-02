package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.transaction.UniqueBudgetList;
import seedu.address.model.transaction.UniqueTransactionList;

/**
 * Bank account of the user.
 */
public class BankAccount implements ReadOnlyBankAccount {
    private Amount balance;
    private UniqueBudgetList budgets;
    private UniqueTransactionList transactions;

    public BankAccount() {
        balance = Amount.zero();
        budgets = new UniqueBudgetList();
        transactions = new UniqueTransactionList();
    }

    public BankAccount(ReadOnlyBankAccount bankAccount) {
        this();
        resetData(bankAccount);
    }

    /**
     * Resets the existing data of this {@code BankAccount} with {@code newData}.
     */
    public void resetData(ReadOnlyBankAccount newData) {
        requireNonNull(newData);
        setBalance(newData.getBalance());
        setTransactions(newData.getTransactionHistory());
        setBudgets(newData.getBudgetHistory());
    }

    public void setBalance(Amount balance) {
        this.balance.setAmount(balance);
    }

    public void setTransactions(List<BankAccountOperation> transactionHistory) {
        this.transactions.setTransactions(transactionHistory);
    }

    public void setBudgets(List<Budget> budgetHistory) {
        this.budgets.setBudgets(budgetHistory);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the bank account.
     * The transaction identity of {@code editedTransaction} must not be the same as
     * another existing transaction in the bank account.
     */
    public void setTransaction(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit) {
        requireNonNull(transactionEdit);

        transactions.setTransaction(transactionTarget, transactionEdit);
    }

    /**
     * Replaces the given budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the bank account.
     * The budget identity of {@code editedBudget} must not be the same as
     * another existing budget in the bank account.
     */
    public void setBudget(Budget budgetTarget, Budget budgetEdit) {
        requireNonNull(budgetEdit);

        budgets.setBudget(budgetTarget, budgetEdit);
    }

    /**
     * Adds a transaction to the bank account.
     * Updates {@code balance} and {@code budgets} respectively.
     *
     * @param txn Transaction to be added to bank account.
     */
    public void add(BankAccountOperation txn) {
        transactions.add(txn);
        updateBudgets(txn);
        Amount newBalance = txn.handleBalance(this.balance);
        this.balance = newBalance;
    }

    /**
     * Adds a transaction to the bank account.
     * Updates {@code budget}.
     *
     * @param bud Budget to be added to bank account.
     */
    public void add(Budget bud) {
        budgets.add(bud);
    }

    /**
     * Removes {@code key} from this {@code BankAccount}.
     * {@code key} must exist in the bank account.
     */
    public void remove(BankAccountOperation key) {
        transactions.remove(key);
        this.balance = this.balance.subtractAmount(key.getAmount());
    }

    /**
     * Removes {@code key} from this {@code BankAccount}.
     * {@code key} must exist in the bank account.
     */
    public void remove(Budget key) {
        budgets.remove(key);
    }

    /**
     * Checks if transaction exists in bank account.
     *
     * @param transaction Transaction to be checked.
     * @return true if transaction is in bank account, else otherwise.
     */
    public boolean has(BankAccountOperation transaction) {
        requireNonNull(transaction);
        return transactions.contains(transaction);
    }

    /**
     * Checks if budget exists in bank account.
     */
    public boolean has(Budget budget) {
        requireNonNull(budget);
        return budgets.contains(budget);
    }

    /**
     * Updates each budget in {@code budgets} when OutTransaction is made.
     *
     * @payram txn Transaction can be either InTransaction or OutTransaction.
     */
    private void updateBudgets(BankAccountOperation txn) {
        if (txn instanceof OutTransaction) {
            for (Budget bd : budgets) {
                bd.updateBudget(txn.getAmount());
            }
        }
    }

    @Override
    public ObservableList<BankAccountOperation> getTransactionHistory() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Budget> getBudgetHistory() {
        return budgets.asUnmodifiableObservableList();
    }

    @Override
    public SortedList<BankAccountOperation> getSortedTransactionHistory(Comparator<BankAccountOperation> t) {
        return getTransactionHistory().sorted(t);
    }

    @Override
    public Amount getBalance() {
        return this.balance;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BankAccount)) {
            return false;
        }

        BankAccount otherBankAccount = (BankAccount) other;
        return this.transactions.equals(otherBankAccount.transactions)
            && this.budgets.equals(otherBankAccount.budgets)
            && this.balance.equals(otherBankAccount.balance);
    }
}
