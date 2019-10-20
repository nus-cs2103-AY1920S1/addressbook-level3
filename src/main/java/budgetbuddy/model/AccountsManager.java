package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.attributes.Name;
import javafx.collections.ObservableList;

/**
 * Manages the loans of each person in a list of persons.
 */
public class AccountsManager {

    private final UniqueAccountList accounts;

    /**
     * Creates a new (empty) list of accounts.
     */
    public AccountsManager() {
        this.accounts = new UniqueAccountList();
    }

    /**
     * Creates and fills a new list of accounts.
     * @param accounts A list of accounts with which to fill the new list.
     */
    public AccountsManager(List<Account> accounts) {
        requireNonNull(accounts);
        this.accounts = new UniqueAccountList(accounts);
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
     * @param toDelete The target account for deletion.
     */
    public void deleteAccount (Account toDelete) {
        if (accounts.contains(toDelete)) {
            accounts.remove(toDelete);
        } else {
            throw new AccountNotFoundException();
        }
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
        return accounts.equals(otherAccountsManager.getAccountsList());
    }
}
