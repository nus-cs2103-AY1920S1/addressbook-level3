package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.Name;
import budgetbuddy.model.account.UniqueAccountList;
import budgetbuddy.model.account.exception.AccountNotFoundException;
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
     * Creates and fills a new list of accpimts.
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
     * Deletes a person's loan, targeted using a given loan.
     * @param toDelete The loan used to identify the target loan for deletion.
     */
    /**public void deleteLoan(Loan toDelete) {
        if (persons.contains(toDelete.getPerson())) {
            Person targetPerson = persons.get(toDelete.getPerson());
            targetPerson.deleteLoan(targetPerson.getLoan(toDelete));
            if (!targetPerson.hasLoansRemaining()) {
                persons.remove(targetPerson);
            }
        } else {
            throw new PersonNotFoundException();
        }
    }*/


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
