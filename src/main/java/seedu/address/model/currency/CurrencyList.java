package seedu.address.model.currency;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.currency.exceptions.CurrencyNotFoundException;
import seedu.address.model.currency.exceptions.CurrencyNotRemovableException;
import seedu.address.model.currency.exceptions.DuplicateCurrencyException;
import seedu.address.model.itinerary.Name;

/**
 * List containing {@code CustomisedCurrency}s.
 */
public class CurrencyList implements Iterable<CustomisedCurrency> {

    public final ObservableList<CustomisedCurrency> internalList = FXCollections.observableArrayList();
    public final ObservableList<CustomisedCurrency> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent {@code CustomisedCurrency} as the given argument.
     */
    public boolean contains(CustomisedCurrency toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCustomisedCurrency);
    }

    /**
     * Adds an {@code CustomisedCurrency}.
     * The currency must not already exist in the list.
     */
    public void add(CustomisedCurrency toAdd) throws DuplicateCurrencyException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCurrencyException();
        }
        internalList.add(toAdd);
    }

    /**
     * Moves an {@code CustomisedCurrency} to the front of the list.
     */
    public void promote(CustomisedCurrency toPromote) throws CurrencyNotFoundException {
        requireNonNull(toPromote);
        if (!contains(toPromote)) {
            throw new CurrencyNotFoundException();
        }
        internalList.remove(toPromote);
        internalList.add(0, toPromote);
    }


    /**
     * Replaces the currency {@code target} in the list with {@code editedCustomisedCurrency}.
     * {@code target} must exist in the list.
     * The currency identity of {@code editedCustomisedCurrency} must not be the same as another in the list.
     */
    public void set(CustomisedCurrency target, CustomisedCurrency edited) throws CurrencyNotFoundException {
        requireAllNonNull(target, edited);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CurrencyNotFoundException();
        }
        if (!target.isSameCustomisedCurrency(edited) && contains(edited)) {
            throw new DuplicateCurrencyException();
        }
        internalList.set(index, edited);
    }

    public void set(List<CustomisedCurrency> occurrences) {
        requireAllNonNull(occurrences);
        if (!currenciesAreUnique(occurrences)) {
            throw new DuplicateCurrencyException();
        }
        internalList.setAll(occurrences);
    }

    /**
     * Removes the equivalent {@code CustomisedCurrency} from the list.
     * The {@code CustomisedCurrency} must exist in the list.
     */
    public void remove(CustomisedCurrency toRemove) throws CurrencyNotFoundException, CurrencyNotRemovableException {
        requireNonNull(toRemove);
        if (toRemove.isSameCustomisedCurrency(
                new CustomisedCurrency(new Name("SGD"), new Symbol("1"), new Rate("1.00")))) {
            throw new CurrencyNotRemovableException();
        }
        if (!internalList.remove(toRemove)) {
            throw new CurrencyNotFoundException();
        }
    }

    /**
     * Removes the {@code CustomisedCurrency} at the specified index.
     *
     * @param index The index of the {@code CustomisedCurrency} to remove.
     */
    public void remove(Index index) throws CurrencyNotRemovableException {
        requireNonNull(index);
        if (internalList.get(index.getZeroBased())
                .isSameCustomisedCurrency(
                        new CustomisedCurrency(new Name("SGD"), new Symbol("1"), new Rate("1.00")))) {
            throw new CurrencyNotRemovableException();
        }
        internalList.remove(index.getZeroBased());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CustomisedCurrency> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CustomisedCurrency> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CurrencyList // instanceof handles nulls
                && internalList.equals(((CurrencyList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if the list contains only unique {@code CustomisedCurrency}s.
     */
    private boolean currenciesAreUnique(List<CustomisedCurrency> occurrence) {
        for (int i = 0; i < occurrence.size() - 1; i++) {
            for (int j = i + 1; j < occurrence.size(); j++) {
                if (occurrence.get(i).isSameCustomisedCurrency(occurrence.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
