package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;

/**
 * Wraps all data at the account-book level
 * Duplicates are not allowed (by .isSameAccount comparison)
 */
public class AccountBook implements ReadOnlyAccountBook {

    private final UniqueAccountList accounts;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        accounts = new UniqueAccountList();
    }

    public AccountBook() {}

    /**
     * Creates an AccountBook using the Accounts in the {@code toBeCopied}
     */
    public AccountBook(ReadOnlyAccountBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAccountBook newData) {
        requireNonNull(newData);

        setAccounts(newData.getAccountList());
    }

    //// account-level operations

    /**
     * Returns true if an account with the same identity as {@code account} exists in the account book.
     */
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return accounts.contains(account);
    }

    /**
     * Adds an account to the account book.
     * The account must not already exist in the account book.
     */
    public void addAccount(Account a) {
        accounts.add(a);
    }

    /**
     * Replaces the given account {@code target} in the list with {@code editedAccount}.
     * {@code target} must exist in the account book.
     */
    public void setAccount(Account target, Account editedAccount) {
        requireNonNull(editedAccount);

        accounts.setAccount(target, editedAccount);
    }

    /**
     * Replaces the contents of the account list with {@code accounts}.
     * {@code accounts} must not contain duplicate accounts.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }

    /**
     * Removes {@code key} from this {@code AccountBook}.
     * {@code key} must exist in the account book.
     */
    public void removeAccount(Account key) {
        accounts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return accounts.asUnmodifiableObservableList().size() + " accounts";
    }

    @Override
    public ObservableList<Account> getAccountList() {
        return accounts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountBook // instanceof handles nulls
                && accounts.equals(((AccountBook) other).accounts));
    }

    @Override
    public int hashCode() {
        return accounts.hashCode();
    }
}
