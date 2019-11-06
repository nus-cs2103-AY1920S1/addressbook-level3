package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exceptions.AccountNotFoundException;
import budgetbuddy.model.account.exceptions.EmptyAccountListException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.storage.export.HtmlExporter;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * Manages the accounts in a list of accounts.
 */
public class AccountsManager {
    private final UniqueAccountList accounts;
    private final FilteredList<Account> filteredAccounts;

    private Index activeAccountIndex = Index.fromZeroBased(0);
    private final TransactionList activeTransactionList;

    /**
     * Creates a new list of accounts, with a default account set as the active account.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        filteredAccounts = new FilteredList<>(this.getAccounts());
        // TODO add proper default data
        addAccount(new Account(new Name("Default"), new Description("Default"), new TransactionList()));
        setActiveAccount(Index.fromZeroBased(0));
        activeTransactionList = new TransactionList();
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
    }

    /**
     * Creates and fills a new list of accounts, given an activeAccountIndex.
     * The active account is always set to the first account in the list.
     * @param accounts A list of accounts with which to fill the new list.
     */
    public AccountsManager(List<Account> accounts, Index activeAccountIndex) {
        requireNonNull(accounts);
        this.accounts = new UniqueAccountList(accounts);
        filteredAccounts = new FilteredList<>(this.getAccounts());
        setActiveAccount(activeAccountIndex);
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
     * Retrieves the list of accounts.
     */
    public ObservableList<Account> getAccounts() {
        return accounts.asUnmodifiableObservableList();
    }

    /**
     * Reset the filteredAccountList so that it contains all the accounts.
     */
    public void resetFilteredAccountList() {
        unsetActiveAccount();
        filteredAccounts.setPredicate(s -> false);
        filteredAccounts.setPredicate(s -> true);
        //activeAccountIndex is reset to the first account
        activeAccountIndex = Index.fromZeroBased(0);
    }

    /**
     * Adds a given account to its specified account in the list.
     * @param toAdd The account to add.
     */
    public void addAccount(Account toAdd) {
        //whenever we add an account, we reset the list if it is filtered
        resetFilteredAccountList();
        accounts.add(toAdd);
        //the new account will be the active account
        //(it is always added in the last position of the account list)
        unsetActiveAccount();
        setActiveAccount(Index.fromOneBased(accounts.size()));
    }

    /**
     * Replaces a target account with the given account.
     * @param toEdit The index of the target account to replace.
     * @param editedAccount The edited account to replace the target account with.
     */
    public void editAccount(Index toEdit, Account editedAccount) throws AccountNotFoundException {
        accounts.replace(filteredAccounts.get(toEdit.getZeroBased()), editedAccount);
        //editing the account may cause it to no longer be part of the filtered list,
        //so we have to reset the filtered account list.
        resetFilteredAccountList();
        switchActiveAccount(accounts.indexOfEquivalent(editedAccount));
    }

    /**
     * Deletes an account.
     * @param toDelete The target account for deletion.
     */
    public void deleteAccount(Index toDelete) throws EmptyAccountListException, AccountNotFoundException {

        if (accounts.size() <= 1) {
            //the last account in the list should not be deleted
            throw new EmptyAccountListException();
        }

        Account accountToDelete = filteredAccounts.get(toDelete.getZeroBased());
        if (filteredAccounts.size() == 1) {
            //there is one account left on the filtered list
            accounts.remove(accountToDelete);
            unsetActiveAccount();
            resetFilteredAccountList();
            setActiveAccount(Index.fromZeroBased(0));
        } else {
            accounts.remove(accountToDelete);
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
    public void updateFilteredAccountList(Predicate<Account> predicate) throws EmptyAccountListException {
        requireNonNull(predicate);
        unsetActiveAccount();
        filteredAccounts.setPredicate(predicate);
        if (filteredAccounts.size() == 0) {
            throw new EmptyAccountListException();
        }
        setActiveAccount(Index.fromZeroBased(0));
    }

    /**
     * Switches the active account to the index specified.
     */
    public void switchActiveAccount(Index targetAccountIndex) throws IndexOutOfBoundsException {
        if (getFilteredAccountList().size() >= targetAccountIndex.getOneBased()) {
            unsetActiveAccount();
            setActiveAccount(targetAccountIndex);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Unsets the boolean flag on any active accounts.
     */
    public void unsetActiveAccount() {
        accounts.forEach(Account::setInactive);
    }

    /**
     * Sets the provided Index to the active account.
     * The previous ActiveAccount will also be de-marked, so there can only be
     * one active account at any time.
     * @param toSet the account to be set to the active account.
     */
    public void setActiveAccount(Index toSet) {
        if (!filteredAccounts.isEmpty()) {
            Account newActiveAccount = filteredAccounts.get(toSet.getZeroBased());
            newActiveAccount.setActive();
        }
        activeAccountIndex = toSet;
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
        return filteredAccounts.get(toGet.getZeroBased());
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

    /**
     * Gets the active account.
     */
    public Account getActiveAccount() {
        return getFilteredAccountList().get(activeAccountIndex.getZeroBased());
    }


    /**
     * Switches the account source for the TransactionList
     */
    public void transactionListSwitchSource(Account account) {
        unsetActiveAccount();
        //when we switch the source of the account, the account list filter gets cleared
        //as transactionLists from any account can be edited(not just those that are filtered)
        resetFilteredAccountList();
        Index newActiveIndex = accounts.indexOfEquivalent(account);
        setActiveAccount(newActiveIndex);
        account.getTransactionList().sortByDescendingDate();
        activeTransactionList.setAll(account.getTransactionList());
    }

    /**
     * Updates the transactionList linked to the currentActiveAccount.
     */
    public void transactionListUpdateSource() {
        getActiveAccount().getTransactionList().sortByDescendingDate();
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
    }

    /**
     * Exports the overview report of all accounts.
     * @return boolean
     * @throws IOException
     */
    public boolean exportReport() throws IOException {
        StringBuilder reportAllAccount = new StringBuilder();

        reportAllAccount.append("<!DOCTYPE html>\n<html>\n<body>\n" + "<center><h2>")
                        .append("<b>Overview of All Accounts in Budget Buddy</b></h2></center>\n");

        for (Account account: filteredAccounts) {
            reportAllAccount.append("<br><br>\n " + "<b>Name : </b>" + account.getName().toString() + "<br>\n")
                    .append("<b>Description : </b>" + account.getDescription().toString() + "<br>\n")
                    .append("<b>Total Balance : </b>" + account.getBalanceString() + "<br>\n")
                    .append("<b>Total Expenses : </b>" + account.getExpense() + "<br>\n")
                    .append("<b>Total Income : </b>" + account.getIncome() + "<br>\n")
                    .append("<b>Categories : </b>" + account.getCategories() + "<br>\n");
        }

        reportAllAccount.append("</body>\n" + "</html>");

        return HtmlExporter.export(reportAllAccount.toString());
    }
}
