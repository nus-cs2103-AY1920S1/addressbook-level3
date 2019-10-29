package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.account.exception.DefaultAccountCannotBeDeletedException;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.model.transaction.exceptions.TransactionNotFoundException;
import javafx.collections.ObservableList;

/**
 * Manages the accounts in a list of accounts.
 */
public class AccountsManager {

    private static Account defaultAccount = new Account(new Name("DEFAULT"), new TransactionList());
    private final UniqueAccountList accounts;

    /**
     * Creates a new list of accounts.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
        this.accounts.add(defaultAccount);
    }

    /**
     * Creates and fills a new list of accounts.
     * The default account is always set to the first account in the list.
     * @param accounts A list of accounts with which to fill the new list.
     */
    public AccountsManager(List<Account> accounts) {
        requireNonNull(accounts);
        this.accounts = new UniqueAccountList(accounts);
        defaultAccount = this.accounts.getAccountByIndex(Index.fromZeroBased(0));
    }

    public Account getDefaultAccount() {
        return defaultAccount;
    }

    /**
     * Retrieves the list of accounts.
     */
    // TODO Probably need to change this to work with the UI.
    public ObservableList<Account> getAccountsList() {
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
    public void deleteAccount (Account toDelete) {
        if (accounts.contains(toDelete)) {
            if (defaultAccount.isSameAccount(toDelete)){
                throw new DefaultAccountCannotBeDeletedException();
            }
            accounts.remove(toDelete);
        } else {
            throw new AccountNotFoundException();
        }
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
     * Removes the current Transaction from its respective Account within the AccountBook.
     * The transaction should exist within the AccountBook before executing this.
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
