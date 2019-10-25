package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.model.transaction.exceptions.TransactionNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Manages the accounts in a list of accounts.
 */
public class AccountsManager {

    private static Account defaultAccount = new Account(new Name("DEFAULT"),
            new Description(""), new TransactionList());

    private final FilteredList<Account> filteredAccounts;
    private final UniqueAccountList accounts;

    private final ObservableList<Account> internalList = FXCollections.observableArrayList();
    private final ObservableList<Account> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Creates a new list of accounts.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        filteredAccounts = new FilteredList<>(this.getAccounts(), s -> true);
        this.accounts.add(defaultAccount);
        this.internalList.add(defaultAccount);
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
        defaultAccount = this.accounts.getAccountByIndex(Index.fromZeroBased(0));
        this.internalList.setAll(accounts);
    }

    public Account getDefaultAccount() {
        return defaultAccount;
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
        return internalUnmodifiableList;
    }

    /**
     * Adds a given account to its specified account in the list.
     * @param toAdd The account to add.
     */
    public void addAccount(Account toAdd) {
        internalList.add(toAdd);
    }

    /**
     * Replaces a target account with the given account.
     * @param toEdit The index of the target account to replace.
     * @param editedAccount The edited account to replace the target account with.
     */
    public void editAccount(Index toEdit, Account editedAccount) throws AccountNotFoundException {
        checkIndexValidity(toEdit);
        internalList.set(toEdit.getZeroBased(), editedAccount);
    }

    /**
     * Deletes an account.
     * If the account to be deleted is the default account, an error is thrown to change the default account
     * before it can be deleted.
     * @param toDelete The target account for deletion.
     */
    public void deleteAccount(Account toDelete) {
        if (internalList.contains(toDelete)) {
            internalList.remove(toDelete);
        } else {
            throw new AccountNotFoundException();
        }
    }

    /**
     * Returns the current number of accounts in the list.
     * @return The current number of accounts in the list as an {@code int}.
     */
    public int getAccountsCount() {
        return getAccounts().size();
    }


    /**
     * Checks if a given index exceeds the number of accounts currently in the list.
     * @param toCheck The index to check.
     * @throws AccountNotFoundException If the index exceeds the current number of accounts.
     */
    private void checkIndexValidity(Index toCheck) throws AccountNotFoundException {
        if (toCheck.getOneBased() > getAccountsCount()) {
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
        return internalList.equals(otherAccountsManager.internalList);
    }

    /**
     * Returns the account at the specified index in the list.
     * @param toGet The index of the target account.
     * @throws AccountNotFoundException If the account is not in the list.
     */
    public Account getAccount(Index toGet) throws AccountNotFoundException {
        checkIndexValidity(toGet);
        return getAccounts().get(toGet.getZeroBased());
    }


}
