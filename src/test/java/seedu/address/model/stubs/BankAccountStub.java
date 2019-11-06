package seedu.address.model.stubs;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.UniqueBudgetList;
import seedu.address.model.transaction.UniqueTransactionList;

/**
 * Bank account of the user.
 */
public class BankAccountStub implements ReadOnlyBankAccount {
    private Amount balance;
    private UniqueBudgetList budgets;
    private UniqueTransactionList transactions;

    public BankAccountStub() {
        balance = Amount.zero();
        budgets = new UniqueBudgetList();
        transactions = new UniqueTransactionList();
    }

    @Override
    public ObservableList<BankAccountOperation> getTransactionHistory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<BankAccountOperation> getSortedTransactionHistory(Comparator<BankAccountOperation> t) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Amount getBalance() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Budget> getBudgetHistory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Projection> getProjectionHistory() {
        return null;
    }

    @Override
    public boolean has(BankAccountOperation transaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean has(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean has(Projection projection) {
        throw new AssertionError("This method should not be called.");
    }
}
