package seedu.address.model.expenditure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class ExpenditureList implements Iterable<Expenditure> {

    public final ObservableList<Expenditure> internalList = FXCollections.observableArrayList();
    public final ObservableList<Expenditure> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds an expenditure to the list.
     * The expenditure must not already exist in the list.
     */
    public void add(Expenditure toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }


    /**
     * Replaces the expenditure {@code target} in the list with {@code editedExpenditure}.
     * {@code target} must exist in the list.
     * The expenditure identity of {@code editedExpenditure} must not be the same as another existing expenditure in the list.
     */
    public void set(Expenditure target, Expenditure edited) throws ExpenditureNotFoundException {
        requireAllNonNull(target, edited);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenditureNotFoundException();
        }

        internalList.set(index, edited);
    }

    public void set(List<Expenditure> occurrences) {
        requireAllNonNull(occurrences);
        internalList.setAll(occurrences);
    }

    /**
     * Removes the equivalent expenditure from the list.
     * The expenditure must exist in the list.
     */
    public void remove(Expenditure toRemove) throws ExpenditureNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ExpenditureNotFoundException();
        }
    }

    /**
     * Removes the item at the specified index.
     *
     * @param index The index of the item to remove.
     */
    public void remove(Index index) {
        requireNonNull(index);
        internalList.remove(index.getZeroBased());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expenditure> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Expenditure> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenditureList // instanceof handles nulls
                && internalList.equals(((ExpenditureList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
