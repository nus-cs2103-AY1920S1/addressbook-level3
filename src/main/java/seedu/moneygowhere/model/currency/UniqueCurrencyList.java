package seedu.moneygowhere.model.currency;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moneygowhere.model.currency.exceptions.CurrencyNotFoundException;
import seedu.moneygowhere.model.currency.exceptions.DuplicateCurrencyException;

/**
 * A list of Currencies.
 * Supports a minimal set of list operations.
 */
public class UniqueCurrencyList implements Iterable<Currency> {

    private final ObservableList<Currency> internalList = FXCollections.observableArrayList();
    private final ObservableList<Currency> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Currency as the given argument.
     */
    public boolean contains(Currency toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCurrency);
    }

    /**
     * Adds a Currency to the list.
     */
    public void add(Currency toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            int index = internalList.indexOf(toAdd);
            internalList.set(index, toAdd);
        } else {
            internalList.add(toAdd);
        }
    }

    /**
     * Replaces the Currency {@code target} in the list with {@code editedCurrency}.
     * {@code target} must exist in the list.
     * If the currency being edited already exists, a duplicate currency exception is thrown.
     */
    public void setCurrency(Currency target, Currency editedCurrency) {
        requireAllNonNull(target, editedCurrency);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CurrencyNotFoundException();
        }

        if (!target.isSameCurrency(editedCurrency) && contains(editedCurrency)) {
            throw new DuplicateCurrencyException();
        }

        internalList.set(index, editedCurrency);
    }

    /**
     * Removes the equivalent Currency from the list.
     * The Currency must exist in the list.
     */
    public void remove(Currency toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CurrencyNotFoundException();
        }
    }

    public void setCurrencies(UniqueCurrencyList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code currencies}.
     */
    public void setCurrencies(List<Currency> currencies) {
        requireAllNonNull(currencies);

        if (hasDuplicateCurrencies(currencies)) {
            throw new DuplicateCurrencyException();
        }

        internalList.setAll(currencies);
    }

    /**
     * Determines if the currency list has duplicate currencies.
     * @param currencies the list of currencies
     * @return true if it has duplicates
     */
    public boolean hasDuplicateCurrencies(List<Currency> currencies) {
        Set<Currency> seenCurrencies = new HashSet<>();

        for (Currency currency : currencies) {
            if (seenCurrencies.contains(currency)) {
                return true;
            }
            seenCurrencies.add(currency);
        }

        return false;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Currency> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Currency> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCurrencyList // instanceof handles nulls
                && internalList.equals(((UniqueCurrencyList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
