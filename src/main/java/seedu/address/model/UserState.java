package seedu.address.model;

import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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

    public void resetData(ReadOnlyBankAccount bankAccount, ReadOnlyLedger ledger) {
        requireAllNonNull(bankAccount, ledger);
        this.bankAccount.resetData(bankAccount);
        this.ledger.resetData(ledger);
    }

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

    public void removeTransaction(BankAccountOperation transaction) {
        bankAccount.removeTransaction(transaction);
    }

    public void removeBudget(Budget budget) {
        bankAccount.removeBudget(budget);
    }

    public void setTransaction(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit) {
        bankAccount.setTransaction(transactionTarget, transactionEdit);
    }

    public void setBudget(Budget budgetTarget, Budget budgetEdit) {
        bankAccount.setBudget(budgetTarget, budgetEdit);
    }

    public void addTransaction(BankAccountOperation transaction) {
        bankAccount.addTransaction(transaction);
    }

    public void addBudget(Budget budget) {
        bankAccount.addBudget(budget);
    }

    public void setTransactions(List<BankAccountOperation> transactionHistory) {
        bankAccount.setTransactions(transactionHistory);
    }

    public void addLedgerOperation(LedgerOperation operation) {
        ledger.addOperation(operation);
    }

    public boolean hasTransaction(BankAccountOperation txn) {
        return bankAccount.hasTransaction(txn);
    }

    public boolean hasBudget(Budget budget) {
        return bankAccount.hasBudget(budget);
    }

    // TODO?
    // public boolean hasLedger(LedgerOperation ledgerOperation) {
    //     return ledger.hasLedger(ledger);
    // }
}
