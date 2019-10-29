package budgetbuddy.model.account;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.account.exception.DuplicateAccountException;
import budgetbuddy.model.attributes.Name;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of accounts that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueAccountList implements Iterable<Account> {

    private final ObservableList<Account> internalList = FXCollections.observableArrayList();
    private final ObservableList<Account> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public UniqueAccountList() {}

    public UniqueAccountList(List<Account> toBeCopied) {
        requireNonNull(toBeCopied);
        setAll(toBeCopied);
    }

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(Account toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(acc -> accountsAreEquivalent(toCheck, acc));
    }

    /**
     * Adds an account to the list.
     * The account must not already exist in the list.
     */
    public void add(Account toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the account {@code target} in the list with {@code editedAccount}.
     * {@code target} must exist in the list.
     * The account identity of {@code editedAccount} must not be the same as another existing account in the list.
     */
    public void replace(Account target, Account editedAccount) {
        requireAllNonNull(target, editedAccount);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AccountNotFoundException();
        }

        if (!accountsAreEquivalent(target, editedAccount) && contains(editedAccount)) {
            throw new DuplicateAccountException();
        }

        internalList.set(index, editedAccount);
    }

    /**
     * Removes the equivalent account from the list.
     * The account must exist in the list.
     */
    public void remove(Account toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AccountNotFoundException();
        }
    }

    /**
     * Retrieves an account from the list equivalent to the given account.
     *
     * @return the account, or null if no such account exists
     */
    public Account get(Account toGet) {
        requireNonNull(toGet);

        for (Account account : internalUnmodifiableList) {
            if (accountsAreEquivalent(account, toGet)) {
                return account;
            }
        }

        return null;
    }

    /**
     * Retrieves an account from the list by index.
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Account get(Index index) {
        return internalList.get(index.getZeroBased());
    }

    /**
     * Retrieves an account from the list by name.
     *
     * @return the account, or null if no such account exists
     */
    public Account get(Name name) {
        requireNonNull(name);

        for (Account account : internalUnmodifiableList) {
            if (account.getName().equals(name)) {
                return account;
            }
        }

        return null;
    }

    public void setAll(UniqueAccountList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code accounts}.
     * {@code accounts} must not contain duplicate accounts.
     */
    public void setAll(List<Account> accounts) {
        requireAllNonNull(accounts);
        if (!accountsAreUnique(accounts)) {
            throw new DuplicateAccountException();
        }

        internalList.setAll(accounts);
    }

    /**
     * Returns the number of accounts in the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Retrieves the index of the account equivalent to the given account.
     *
     * @return the index, or null if no such account exists
     */
    public Index indexOfEquivalent(Account toGet) {
        requireNonNull(toGet);

        for (int i = 0; i < internalList.size(); ++i) {
            if (accountsAreEquivalent(internalList.get(i), toGet)) {
                return Index.fromZeroBased(i);
            }
        }

        return null;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Account> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Account> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAccountList // instanceof handles nulls
                && internalList.equals(((UniqueAccountList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code accounts} contains only unique accounts.
     */
    private boolean accountsAreUnique(List<Account> accounts) {
        for (int i = 0; i < accounts.size() - 1; i++) {
            for (int j = i + 1; j < accounts.size(); j++) {
                if (accountsAreEquivalent(accounts.get(i), accounts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the two accounts are identical, or have the same name.
     */
    private static boolean accountsAreEquivalent(Account a1, Account a2) {
        if (a1 == null && a2 != null || a1 != null && a2 == null) {
            return false;
        }

        return a1 == a2 || a1.getName().equals(a2.getName());
    }
}

