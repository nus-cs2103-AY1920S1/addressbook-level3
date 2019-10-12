package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

/**
 * Import various classes related
 */
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

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return accounts.contains(account);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addAccount(Account a) {
        accounts.add(a);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setAccounts(Account target, Account editedAccount) {
        requireNonNull(editedAccount);

        accounts.setAccount(target, editedAccount);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeAccount(Account key) {
        accounts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return accounts.asUnmodifiableObservableList().size() + " accounts";
        // TODO: refine later
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
