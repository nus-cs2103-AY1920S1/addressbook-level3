package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exceptions.AccountNotFoundException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Manages the accounts in a list of accounts.
 */
public class AccountsManager {
    private final UniqueAccountList accounts;
    private final FilteredList<Account> filteredAccounts;

    private Index activeAccountIndex;
    private final TransactionList activeTransactionList;

    /**
     * Creates a new list of accounts.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        filteredAccounts = new FilteredList<>(this.getAccounts());
        activeAccountIndex = Index.fromZeroBased(0);
        // TODO add proper default data
        addAccount(new Account(new Name("Default"), new Description("Default"), new TransactionList()));
        activeTransactionList = new TransactionList();
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
    }

    /**
     * Creates and fills a new list of accounts.
     * The default account is always set to the first account in the list.
     * @param accounts A list of accounts with which to fill the new list.
     */
    public AccountsManager(List<Account> accounts, Index activeAccountIndex) {
        requireNonNull(accounts);
        this.accounts = new UniqueAccountList(accounts);
        this.activeAccountIndex = activeAccountIndex;
        filteredAccounts = new FilteredList<>(this.getAccounts());
        activeTransactionList = new TransactionList();
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
    }

    /**
     * Returns an unmodifiable view of the list of Account
     */
    public ObservableList<Account> getFilteredAccountList() {
        return filteredAccounts;
    }

    /**
     * Returns an unmodifiable view of the current active account's transactionlist.
     */
    public ObservableList<Transaction> getActiveTransactionList() {
        return activeTransactionList.asUnmodifiableObservableList();
    }

    /**
     * Checks if a given account is currently the active account.
     * @param testAccount The account to be checked
     */
    public boolean isActiveAccount(Account testAccount) {
        return UniqueAccountList.accountsAreEquivalent(testAccount, getActiveAccount());
    }

    /**
     * Retrieves the list of accounts.
     */
    public ObservableList<Account> getAccounts() {
        return accounts.asUnmodifiableObservableList();
    }

    /**
     * Reset the filteredAccountList so that it contains all the accounts.
     */
    public void resetFilteredAccountList() {
        filteredAccounts.setPredicate(s -> true);
        //activeAccountIndex is reset to the first account
        activeAccountIndex = Index.fromZeroBased(0);
    }

    /**
     * Adds a given account to its specified account in the list.
     * @param toAdd The account to add.
     */
    public void addAccount(Account toAdd) {
        accounts.add(toAdd);
        activeAccountIndex = accounts.indexOfEquivalent(toAdd);
    }

    /**
     * Replaces a target account with the given account.
     * @param toEdit The index of the target account to replace.
     * @param editedAccount The edited account to replace the target account with.
     */
    public void editAccount(Index toEdit, Account editedAccount) throws AccountNotFoundException {
        accounts.replace(accounts.get(toEdit), editedAccount);
        activeAccountIndex = accounts.indexOfEquivalent(editedAccount);
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
            //activeAccountIndex is reset to the first account.
            activeAccountIndex = Index.fromZeroBased(0);
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
        activeAccountIndex = Index.fromZeroBased(0);
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
     *
     * @param toGet The index of the target account.
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Account getAccount(Index toGet) throws IndexOutOfBoundsException {
        return accounts.get(toGet);
    }

    /**
     * Returns the account with the given name.
     *
     * @return the account, or null if no such account exists
     */
    public Account getAccount(Name toGet) {
        return accounts.get(toGet);
    }

    public Index getActiveAccountIndex() {
        return activeAccountIndex;
    }

    public void setActiveAccountIndex(Index activeAccountIndex) {
        requireNonNull(activeAccountIndex);
        if (activeAccountIndex.getZeroBased() >= accounts.size()) {
            throw new IndexOutOfBoundsException("Active account index out of bounds");
        }
        this.activeAccountIndex = activeAccountIndex;
    }

    /**
     * Gets the active account.
     */
    public Account getActiveAccount() {
        return accounts.get(activeAccountIndex);
    }

    /**
     * Sets the active account. The account must already be contained in the manager.
     *
     * @throws AccountNotFoundException if the account could not be found
     */
    public void setActiveAccount(Account account) {
        Index newActiveIndex = accounts.indexOfEquivalent(account);
        if (newActiveIndex == null) {
            throw new AccountNotFoundException();
        }

        activeAccountIndex = newActiveIndex;
    }

    /**
     * Switches the account source for the TransactionList
     */
    public void transactionListSwitchSource(Account account) {
        setActiveAccount(account);
        activeTransactionList.setAll(account.getTransactionList());
    }

    /**
     * Updates the transactionList linked to the currentActiveAccount
     */
    public void transactionListUpdateSource() {
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
    }
}
