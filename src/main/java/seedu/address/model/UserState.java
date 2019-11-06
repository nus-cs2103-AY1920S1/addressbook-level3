package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

/**
 * User state of the user.
 */
public class UserState implements ReadOnlyUserState {
    private BankAccount bankAccount;
    private Ledger ledger;

    public UserState() {
        bankAccount = new BankAccount();
        ledger = new Ledger();
    }

    public UserState(ReadOnlyBankAccount bankAccount, ReadOnlyLedger ledger) {
        this();
        resetData(bankAccount, ledger);
    }

    public UserState(ReadOnlyUserState initialState) {
        this();
        resetData(initialState.getBankAccount(), initialState.getLedger());
    }

    private void resetData(ReadOnlyBankAccount bankAccount, ReadOnlyLedger ledger) {
        requireAllNonNull(bankAccount, ledger);
        this.bankAccount.resetData(bankAccount);
        this.ledger.resetData(ledger);
    }

    /**
     * Resets the existing data of this {@code UserState} with {@code userState}.
     */
    public void resetData(ReadOnlyUserState userState) {
        requireNonNull(userState);
        resetData(userState.getBankAccount(), userState.getLedger());
    }

    @Override
    public ReadOnlyBankAccount getBankAccount() {
        return bankAccount;
    }

    @Override
    public ReadOnlyLedger getLedger() {
        return ledger;
    }

    public void remove(BankAccountOperation transaction) {
        bankAccount.remove(transaction);
    }

    public void remove(Budget budget) {
        bankAccount.remove(budget);
    }

    public void remove(Projection projection) {
        bankAccount.remove(projection);
    }

    public void remove(LedgerOperation ledgerOperation) {
        ledger.remove(ledgerOperation);
    }


    public void set(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit) {
        bankAccount.setTransaction(transactionTarget, transactionEdit);
    }

    public void set(Budget budgetTarget, Budget budgetEdit) {
        bankAccount.setBudget(budgetTarget, budgetEdit);
    }

    public void set(LedgerOperation target, LedgerOperation source) {
        ledger.set(target, source);
    }

    public void set(List<BankAccountOperation> transactionHistory) {
        bankAccount.setTransactions(transactionHistory);
    }

    public void add(BankAccountOperation transaction) {
        bankAccount.add(transaction);
    }

    public void add(LedgerOperation operation) {
        ledger.addOperation(operation);
    }

    public void add(Budget budget) {
        bankAccount.add(budget);
    }

    public void add(Projection projection) {
        bankAccount.add(projection);
    }

    public boolean has(BankAccountOperation txn) {
        return bankAccount.has(txn);
    }

    public boolean has(Projection projection) {
        return bankAccount.has(projection);
    }

    public boolean has(Budget budget) {
        return bankAccount.has(budget);
    }

    public boolean has(LedgerOperation ledgerOperation) {
        return ledger.has(ledgerOperation);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserState)) {
            return false;
        }

        UserState otherUserState = (UserState) other;
        return this.ledger.equals(otherUserState.ledger)
            && this.bankAccount.equals(otherUserState.bankAccount);
    }
}
