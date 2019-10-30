package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.account.exception.DefaultAccountCannotBeDeletedException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.model.transaction.exceptions.TransactionNotFoundException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Manages the accounts in a list of accounts.
 */
public class AccountsManager {

    private static Account defaultAccount = new Account(new Name("DEFAULT"),
            new Description("null"), new TransactionList());

    private final FilteredList<Account> filteredAccounts;
    private final UniqueAccountList accounts;

    /**
     * Creates a new list of accounts.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        this.accounts.add(defaultAccount);
        filteredAccounts = new FilteredList<>(this.getAccountsList(), s -> true);
    }

    /**
     * Creates and fills a new list of accounts.
     * The default account is always set to the first account in the list.
     * @param accounts A list of accounts with which to fill the new list.
     */
    public AccountsManager(List<Account> accounts) {
        requireNonNull(accounts);
        this.accounts = new UniqueAccountList(accounts);
        filteredAccounts = new FilteredList<>(this.getAccountsList(), s -> true);
        defaultAccount = this.accounts.getAccountByIndex(Index.fromZeroBased(0));
    }

    public Account getDefaultAccount() {
        return defaultAccount;
    }

    /**
     * Checks if a given account is currently the default account.
     * @param testAccount The account to be checked
     */
    public boolean isDefaultAccount(Account testAccount) {
        return testAccount.isSameAccount(defaultAccount);
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
     * If the account to be deleted is the default account, an error is thrown to change the default account
     * before it can be deleted.
     * @param toDelete The target account for deletion.
     */
    public void deleteAccount(Account toDelete) {
        if (accounts.contains(toDelete)) {
            if (defaultAccount.isSameAccount(toDelete)) {
                throw new DefaultAccountCannotBeDeletedException();
            }
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
     * Adds the current Transaction to its respective Account.
     * The transaction should have a valid account.
     * If the account is not found within the AccountsManager, an AccountNotFoundException is thrown.
     * @param toAdd
     */
    public void addTransaction(Transaction toAdd) throws AccountNotFoundException {
        Account transactionAccount = toAdd.getAccount();
        Account managerAccount = transactionAccount != null
                ? accounts.get(transactionAccount)
                : getDefaultAccount();
        toAdd.setAccount(managerAccount);
        managerAccount.addTransaction(toAdd);
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


}
