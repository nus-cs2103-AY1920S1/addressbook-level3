package budgetbuddy.model;

import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_DESCENDING_DATE;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exceptions.AccountNotFoundException;
import budgetbuddy.model.account.exceptions.EmptyAccountListException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.attributes.SignedAmount;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.storage.export.HtmlExporter;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;


/**
 * Manages the accounts in a list of accounts.
 */
public class AccountsManager {
    private final UniqueAccountList accounts;
    private final FilteredList<Account> filteredAccounts;

    public static final Index DEFAULT_INDEX = Index.fromZeroBased(0);

    private Index activeAccountIndex = DEFAULT_INDEX;
    private final TransactionList activeTransactionList;
    private final SortedList<Transaction> sortedTransactions;
    private final FilteredList<Transaction> filteredTransactions;



    /**
     * Creates a new list of accounts, with a default account set as the active account.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        filteredAccounts = new FilteredList<>(this.getAccounts());
        // TODO add proper default data
        addAccount(new Account(new Name("Default"), new Description("Default"), new TransactionList()));
        setActiveAccountByIndex(DEFAULT_INDEX);
        activeTransactionList = new TransactionList();
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
        sortedTransactions = new SortedList<>(activeTransactionList.asUnmodifiableObservableList());
        filteredTransactions = new FilteredList<>(sortedTransactions);
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
        activeTransactionList = new TransactionList();
        sortedTransactions = new SortedList<>(activeTransactionList.asUnmodifiableObservableList());
        filteredTransactions = new FilteredList<>(sortedTransactions);
        try {
            setActiveAccountByIndex(activeAccountIndex);
        } catch (IndexOutOfBoundsException e) {
            //the index provided is out of bounds, so we set to the default.
            setActiveAccountByIndex(DEFAULT_INDEX);
        }
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
    }

    /**
     * Returns an unmodifiable view of the list of filtered Accounts
     */
    public ObservableList<Account> getFilteredAccountList() {
        return filteredAccounts;
    }

    /**
     * Returns an unmodifiable view of the list of filtered Transactions.
     */
    public FilteredList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
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
        //filteredAccounts should not be empty
        assert !filteredAccounts.isEmpty();
        //activeAccountIndex is reset to the first account
        setActiveAccountByIndex(DEFAULT_INDEX);
    }

    /**
     * Adds a given account to its specified account in the list.
     * @param toAdd The account to add.
     */
    public void addAccount(Account toAdd) throws IndexOutOfBoundsException {
        //whenever we add an account, we reset the list if it is filtered
        resetFilteredAccountList();
        accounts.add(toAdd);
        Index targetIndex = accounts.indexOfEquivalent(toAdd);
        if (targetIndex != null) {
            //the new account will be the active account
            setActiveAccountByIndex(targetIndex);
        } else {
            setActiveAccountByIndex(DEFAULT_INDEX);
        }
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
        setActiveAccountByIndex(accounts.indexOfEquivalent(editedAccount));
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

        if (accounts.size() < toDelete.getOneBased()) {
            throw new AccountNotFoundException();
        }

        Account accountToDelete = filteredAccounts.get(toDelete.getZeroBased());
        boolean isActiveAccount = accountToDelete.isActive();
        accounts.remove(accountToDelete);
        if (filteredAccounts.size() == 0) {
            // there was only one account left on the filtered list
            // (it had to have been the active account)
            // reset the filtered account list and set the first one to active
            unsetActiveAccount();
            resetFilteredAccountList();
            setActiveAccountByIndex(DEFAULT_INDEX);
        } else if (isActiveAccount) {
            // we removed the active account, reset it to the first account
            setActiveAccountByIndex(DEFAULT_INDEX);
        } else if (activeAccountIndex.getZeroBased() > toDelete.getZeroBased()) {
            // we removed an account before the active account (by index)
            // the active account's index has now decreased by 1, so update it
            setActiveAccountByIndex(Index.fromZeroBased(activeAccountIndex.getZeroBased() - 1));
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
        setActiveAccountByIndex(DEFAULT_INDEX);
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
     * @param toSet the Index of the account to be set to the active account.
     */
    public void setActiveAccountByIndex(Index toSet) throws IndexOutOfBoundsException, EmptyAccountListException {
        if (!filteredAccounts.isEmpty()) {
            Account newActiveAccount = filteredAccounts.get(toSet.getZeroBased());
            transactionListSwitchSource(newActiveAccount);
            unsetActiveAccount();
            newActiveAccount.setActive();
            activeAccountIndex = toSet;
        } else {
            //the list of filtered accounts should never be empty
            throw new EmptyAccountListException();
        }
    }

    /**
     * Sets the provided Account to the active account.
     * It resets the filter on the filteredAccountList, if any.
     */
    public void setActiveAccount(Account account) {
        unsetActiveAccount();
        resetFilteredAccountList();
        Index targetIndex = accounts.indexOfEquivalent(account);
        if (targetIndex != null) {
            setActiveAccountByIndex(targetIndex);
        } else {
            setActiveAccountByIndex(DEFAULT_INDEX);
        }
    }

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     */
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    /**
     * Resets the filter of the filtered transaction list.
     */
    public void resetFilteredTransactionList() {
        filteredTransactions.setPredicate(t -> true);
    }

    /**
     * Updates the sorted transaction list with a new transaction comparator.
     * The comparator must not be null.
     */
    public void updateSortedTransactionList(Comparator<Transaction> comparator) {
        requireNonNull(comparator);
        sortedTransactions.setComparator(comparator);
    }

    /**
     * Resets the sorted transaction list to the default.
     */
    public void resetSortedTransactionList() {
        sortedTransactions.setComparator(SORT_BY_DESCENDING_DATE);
    }

    /**
     * Gets the nett outflow/inflow of money within a filtered transaction list.
     * When the filter is default, the value should be the same as account balance.
     */
    public SignedAmount getFilteredTransactionListNettFlow() {
        long total = 0;
        for (Transaction t : filteredTransactions) {
            total += t.getDirection().equals(Direction.IN)
                    ? t.getAmount().toLong()
                    : -t.getAmount().toLong();
        }
        return new SignedAmount(Math.abs(total), total >= 0);
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
        //when we switch the source account of the transactionList,
        //if the account does not exist in the FilteredAccountList,
        //the account list filter gets cleared.
        if (!filteredAccounts.contains(account)) {
            resetFilteredAccountList();
        }
        Index newActiveIndex = accounts.indexOfEquivalent(account);
        if (activeTransactionList != null) {
            activeTransactionList.setAll(account.getTransactionList());
        }
        if (sortedTransactions != null) {
            resetSortedTransactionList();
        }
    }

    /**
     * Updates the transactionList linked to the currentActiveAccount.
     */
    public void transactionListUpdateSource() {
        activeTransactionList.setAll(getActiveAccount().getTransactionList());
        resetSortedTransactionList();
    }

    /**
     * Exports the overview report of all accounts.
     * @throws IOException
     */
    public void exportReport() throws IOException {
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

        reportAllAccount.append("</body>\n" + "</html>\n");

        HtmlExporter.export(reportAllAccount.toString());
    }
}
