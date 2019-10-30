package budgetbuddy.model.account;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
//import java.util.Optional;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.exception.AccountNotFoundException;
import budgetbuddy.model.account.exception.DuplicateAccountException;
//import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;
//import budgetbuddy.model.transaction.TransactionList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of accounts that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueAccountList implements Iterable<Account> {

    private final ObservableList<Account> internalList = FXCollections.observableArrayList();
    private final ObservableList<Account> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public UniqueAccountList() {}

    public UniqueAccountList(List<Account> toBeCopied) {
        requireNonNull(toBeCopied);
        setAccounts(toBeCopied);
    }

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(Account toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAccount);
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
     * @param toGet The equivalent account (identical attributes to the target account).
     * @return The retrieved account.
     * @throws AccountNotFoundException if account is not in the list.
     */
    public Account get(Account toGet) {
        requireNonNull(toGet);

        Account targetAccount = null;
        for (Account account : internalUnmodifiableList) {
            if (account.isSameAccount(toGet)) {
                targetAccount = account;
            }
        }

        if (targetAccount == null) {
            throw new AccountNotFoundException();
        }

        return targetAccount;
    }

    /**
     * Retrieves an account from the list by index.
     * @param index The index of the account to be obtained.
     * @return the account at that index within the
     */
    public Account getAccountByIndex(Index index) {
        return internalList.get(index.getZeroBased());
    }

    public void setAccounts(UniqueAccountList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code accounts}.
     * {@code accounts} must not contain duplicate accounts.
     */
    public void setAccounts(List<Account> accounts) {
        requireAllNonNull(accounts);
        if (!accountsAreUnique(accounts)) {
            throw new DuplicateAccountException();
        }

        internalList.setAll(accounts);
    }

    /**
     * Gets account that matches the UniqueAccountList by name
     * TODO IMPLEMENT, right now only returns a new account
     */
    /*public Optional<Account> getAccount(String accountName) {
        return Optional.of(new Account(new Name("default"), new TransactionList()));
    }*/
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Account> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    /**
     * TODO: implement getTransactionList, right now only returns a new observableArrayList
     */
    public ObservableList<Transaction> getTransactionList() {
        return FXCollections.observableArrayList();
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
                if (accounts.get(i).isSameAccount(accounts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

