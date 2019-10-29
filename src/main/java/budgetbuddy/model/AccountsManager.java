package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.exceptions.TransactionNotFoundException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Manages the accounts in a list of accounts.
 */
public class AccountsManager {
    private final FilteredList<Account> filteredAccounts;
    private final UniqueAccountList accounts;

    /**
     * Creates a new list of accounts.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        filteredAccounts = new FilteredList<>(this.getAccounts(), s -> true);
    }

    /**
     * Creates and fills a new list of accounts.
     * The default account is always set to the first account in the list.
     * @param accounts A list of accounts with which to fill the new list.
     */
    public AccountsManager(List<Account> accounts) {
        requireNonNull(accounts);
        this.accounts = new UniqueAccountList(accounts);
        filteredAccounts = new FilteredList<>(this.getAccounts(), s -> true);
    }

    /**
     * Returns an unmodifiable view of the list of Account
     */
    public ObservableList<Account> getFilteredAccountList() {
        return filteredAccounts;
    }

    /**
     * Retrieves the list of accounts.
     */
    public ObservableList<Account> getAccounts() {
        return accounts.asUnmodifiableObservableList();
    }

    /**
     * Adds a given account to its specified account in the list.
     * @param toAdd The account to add.
     */
    public void addAccount(Account toAdd) {
        accounts.add(toAdd);
    }

    /**
     * Replaces a target account with the given account.
     * @param toEdit The index of the target account to replace.
     * @param editedAccount The edited account to replace the target account with.
     */
    public void editAccount(Index toEdit, Account editedAccount) throws AccountNotFoundException {
        accounts.setAccount(accounts.getAccountByIndex(toEdit), editedAccount);
    }

    /**
     * Deletes an account.
     * If the account to be deleted is the default account, an error is thrown to change the default account
     * before it can be deleted.
     * @param toDelete The target account for deletion.
     */
    public void deleteAccount(Account toDelete) {
        if (accounts.contains(toDelete)) {
            accounts.remove(toDelete);
        } else {
            throw new AccountNotFoundException();
        }
    }

    /**
     * Returns the current number of accounts in the list.
     * @return The current number of accounts in the list as an {@code int}.
     */
    public int size() {
        return getAccounts().size();
    }

    /**
     * Updates the filter of the filtered account list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredAccountList(Predicate<Account> predicate) {
        requireNonNull(predicate);
        filteredAccounts.setPredicate(predicate);
    }

    /**
     * Adds the current Transaction to its respective Account.
     * The transaction should have a valid account by now.
     * @param toAdd
     */
    public void addTransaction(Transaction toAdd) {
        Account account = toAdd.getAccount();
        account.addTransaction(toAdd);
    }

    /**
     * Removes the current Transaction from its respective Account.
     * The transaction should exist within the AccountsManager before executing this.
     * @param toDelete the transaction to be deleted
     */
    public void removeTransaction(Transaction toDelete) throws TransactionNotFoundException {
        Account account = toDelete.getAccount();
        account.deleteTransaction(toDelete);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AccountsManager)) {
            return false;
        }

        AccountsManager otherAccountsManager = (AccountsManager) other;
        return accounts.equals(otherAccountsManager.accounts);
    }

    /**
     * Returns the account at the specified index in the list.
     * @param toGet The index of the target account.
     * @throws AccountNotFoundException If the account is not in the list.
     */
    public Account getAccount(Index toGet) throws AccountNotFoundException {
        return accounts.getAccountByIndex(toGet);
    }
}
