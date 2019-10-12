package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.account.exception.AccountNotFoundException;
import seedu.address.model.account.exception.DuplicateAccountException;


/**
 * A list of accounts that enforces uniqueness between its elements and does not allow nulls.
 * An account is considered unique by comparing using {@code Account#isSameAccount(Account)}. As such, adding and updating of
 * accounts uses Account#isSameAccount(Account) for equality.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueAccountList implements Iterable<Account> {

    private final ObservableList<Account> internalList = FXCollections.observableArrayList();
    private final ObservableList<Account> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Account toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAccount);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Account toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setAccount(Account target, Account editedAccount) {
        requireAllNonNull(target, editedAccount);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AccountNotFoundException();
        }

        if (!target.isSameAccount(editedAccount) && contains(editedAccount)) {
            throw new DuplicateAccountException();
        }

        internalList.set(index, editedAccount);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Account toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AccountNotFoundException();
        }
    }

    public void setAccounts(UniqueAccountList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAccounts(List<Account> accounts) {
        requireAllNonNull(accounts);
        if (!accountsAreUnique(accounts)) {
            throw new DuplicateAccountException();
        }

        internalList.setAll(accounts);
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean accountsAreUnique(List<Account> accounts) {
        for (int i = 0; i < accounts.size() - 1; i++) {
            for (int j = i + 1; j < accounts.size(); j++) {
                if (accounts.get(i).isSameAccount(accounts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
