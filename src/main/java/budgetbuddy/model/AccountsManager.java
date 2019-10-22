package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Manages the loans of each person in a list of persons.
 */
public class AccountsManager {

    private final UniqueAccountList accounts;

    private final FilteredList<Account> filteredAccounts;

    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        filteredAccounts = new FilteredList<>(this.getAccountsList(), s -> true);
    }

    /**
     * Creates and fills a new list of accounts.
     * @param accounts A list of accounts with which to fill the new list.
     */
    public AccountsManager(List<Account> accounts) {
        requireNonNull(accounts);
        this.accounts = new UniqueAccountList(accounts);
        filteredAccounts = new FilteredList<>(this.getAccountsList(), s -> true);
    }


    /**
     * Retrieves the list of accounts.
     */
    // TODO Probably need to change this to work with the UI.
    public ObservableList<Account> getAccountsList() {
        return accounts.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the list of Account
     */
    public ObservableList<Account> getFilteredAccountList() {
        return filteredAccounts;
    }


    /**
     * Adds a given account to its specified account in the list.
     * @param toAdd The account to add.
     */
    public void addAccount(Account toAdd) {
        accounts.add(toAdd);
    }

    /**
     * Edits an account's Name
     */
    public void editAccount(Account targetAccount, Name name) {
        if (accounts.contains(targetAccount)) {
            accounts.get(targetAccount).setName(targetAccount, name);
        } else {
            throw new AccountNotFoundException();
        }
    }

    /**
     * Deletes an account.
     * @param toDelete The target account for deletion.
     */
    public void deleteAccount (Account toDelete) {
        if (accounts.contains(toDelete)) {
            accounts.remove(toDelete);
        } else {
            throw new AccountNotFoundException();
        }
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
     * Returns an unmodifiable view of the transaction list.
     */
    public ObservableList<Transaction> getTransactionList() {
        return accounts.getTransactionList();
    }

    /**
     * Adds a transaction to the AccountBook
     */
    //TODO implement addTransaction
    public void addTransaction(Transaction toAdd) {
        Account accountToCheck = toAdd.getAccount();
        if (accounts.contains(accountToCheck)) {

        }
    }

    //TODO implement removeTransaction
    public void removeTransaction(Transaction toDelete){

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
}
