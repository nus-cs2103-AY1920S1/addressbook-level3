package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.category.Category;
import seedu.address.model.projection.Projection;
import seedu.address.model.projection.UniqueProjectionList;
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
    private UniqueProjectionList projections;

    public BankAccount() {
        balance = Amount.zero();
        budgets = new UniqueBudgetList();
        transactions = new UniqueTransactionList();
        projections = new UniqueProjectionList();
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
        setProjections(newData.getProjectionHistory());
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

    public void setProjections(List<Projection> projectionHistory) {
        this.projections.setProjections(projectionHistory);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * Updates the {@code budgets} with the {@code editedTransaction} amount if it is an OutTransaction
     * {@code target} must exist in the bank account.
     * The transaction identity of {@code editedTransaction} must not be the same as
     * another existing transaction in the bank account.
     */
    public void setTransaction(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit) {
        requireNonNull(transactionEdit);
        this.balance = this.balance.subtractAmount(transactionTarget.getAmount());
        this.balance = this.balance.addAmount(transactionEdit.getAmount());
        updateBudgets(transactionTarget, transactionEdit);
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
        updateBudgets(txn, false);
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
     * Adds a transaction to the bank account.
     * Updates {@code projections}.
     *
     * @param projection Projection to be added to bank account.
     */
    public void add(Projection projection) {
        projections.add(projection);
    }

    /**
     * Removes {@code key} from this {@code BankAccount}.
     * {@code key} must exist in the bank account.
     */
    public void remove(BankAccountOperation key) {
        transactions.remove(key);
        updateBudgets(key, true);
        this.balance = this.balance.subtractAmount(key.getAmount());
    }

    /**
     * Removes {@code key} from this {@code BankAccount}.
     * {@code key} must exist in the bank account.
     */
    public void remove(Projection key) {
        projections.remove(key);
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
     * Checks if projection exists in bank account.
     */
    public boolean has(Projection projection) {
        requireNonNull(projection);
        return projections.contains(projection);
    }

    /**
     * Updates each budget in {@code budgets} when OutTransaction is made.
     * Each budget is updated only if it belongs to the same {@code Category} as the OutTransaction,
     * and if OutTransaction is dated after Today, before the deadline.
     *
     * @param txn Transaction can be either InTransaction or OutTransaction.
     */
    private void updateBudgets(BankAccountOperation txn, boolean isRemoveTransaction) {
        if (txn instanceof OutTransaction) {
            Amount outAmount = txn.getAmount();
            Set<Category> outCategories = txn.getCategories();
            for (Budget bd : budgets) {
                boolean beforeDeadline = txn.getDate().compareTo(bd.getDeadline()) < 0;
                if (beforeDeadline) {
                    Budget newBd = bd.updateBudget(outAmount, outCategories, isRemoveTransaction);
                    setBudget(bd, newBd);

                }
            }
        }
    }

    /**
     * Updates each budget in {@code budgets} when OutTransaction is updated/changed.
     *
     * @param transactionTarget accepts the Transaction to be changed.
     * @param transactionEdit accepts the Transaction to be edited.
     * Only updates when transactionTarget and transactionEdit are both OutTransactions
     */
    private void updateBudgets(BankAccountOperation transactionTarget,
                               BankAccountOperation transactionEdit) {
        boolean isSameTransactionCategory = transactionTarget.getCategories()
                .equals(transactionEdit.getCategories());
        if (transactionTarget instanceof OutTransaction
                && transactionEdit instanceof OutTransaction) {
            Amount amountToReplace = transactionTarget.getAmount();
            Amount amountReplacement = transactionEdit.getAmount();
            Set<Category> outCategories = transactionEdit.getCategories();
            for (Budget bd : budgets) {
                Budget newBd = bd.updateBudget(amountToReplace,
                        amountReplacement, outCategories, isSameTransactionCategory);
                setBudget(bd, newBd);
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
    public ObservableList<Projection> getProjectionHistory() {
        return projections.asUnmodifiableObservableList();
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
        return this.budgets.equals(otherBankAccount.budgets)
            && this.balance.equals(otherBankAccount.balance)
            && this.transactions.equals(otherBankAccount.transactions);
    }
}
