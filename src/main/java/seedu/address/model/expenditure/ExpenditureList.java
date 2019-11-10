package seedu.address.model.expenditure;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.expenditure.exceptions.DuplicateExpenditureException;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;
import seedu.address.model.expenditure.exceptions.ExpenditureNotRemovableException;

/**
 * List containing {@code Expenditure}.
 */
public class ExpenditureList implements Iterable<Expenditure> {

    public final ObservableList<Expenditure> internalList = FXCollections.observableArrayList();
    public final ObservableList<Expenditure> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Expenditure toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameExpenditure);
    }

    /**
     * Adds an expenditure to the list.
     * The expenditure must not already exist in the list.
     */
    public void add(Expenditure toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExpenditureException();
        }
        internalList.add(toAdd);
        sort();
    }

    /**
     * Sort the expenditures in the list.
     * Expenditures not assigned a day come first, followed by other expenditures in increasing order of day number.
     */
    public void sort() {
        Collections.sort(internalList, (e1, e2) -> {
            if (e1.getDayNumber().isPresent() && e2.getDayNumber().isPresent()) {
                return Integer.parseInt(e1.getDayNumber().get().toString())
                        - Integer.parseInt(e2.getDayNumber().get().toString());
            } else if (e1.getDayNumber().isPresent()) {
                return Integer.parseInt(e1.getDayNumber().get().toString());
            } else if (e1.getDayNumber().isPresent()) {
                return Integer.parseInt(e2.getDayNumber().get().toString());
            } else {
                return 0;
            }
        });
    }


    /**
     * Replaces the expenditure {@code target} in the list with {@code editedExpenditure}.
     * {@code target} must exist in the list.
     * The expenditure identity of {@code editedExpenditure} must not be the same as another in the list.
     */
    public void set(Expenditure target, Expenditure edited) throws ExpenditureNotFoundException {
        requireAllNonNull(target, edited);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenditureNotFoundException();
        }
        if (!target.isSameExpenditure(edited) && contains(edited)) {
            throw new DuplicateExpenditureException();
        }
        internalList.set(index, edited);
        sort();
    }

    public void set(List<Expenditure> occurrences) {
        requireAllNonNull(occurrences);
        if (!expendituresAreUnique(occurrences)) {
            throw new DuplicateExpenditureException();
        }
        internalList.setAll(occurrences);
        sort();
    }

    /**
     * Removes the equivalent expenditure from the list.
     * The expenditure must exist in the list.
     */
    public void remove(Expenditure toRemove) throws ExpenditureNotFoundException {
        requireNonNull(toRemove);
        List<Expenditure> events = new ArrayList<>();
        if (!internalList.stream().anyMatch(toRemove:: isSameExpenditure)) {
            throw new ExpenditureNotFoundException();
        }
        internalList.stream().filter(toRemove:: isSameExpenditure).forEach(i -> events.add(i));
        internalList.remove(events.get(0));
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
     * Removes the equivalent expenditure from the list.
     * The expenditure must exist in the list.
     */
    public void removeByUser(Expenditure toRemove) throws ExpenditureNotFoundException,
            ExpenditureNotRemovableException {
        requireNonNull(toRemove);
        if (toRemove.getRemovability()) {
            if (!internalList.remove(toRemove)) {
                throw new ExpenditureNotFoundException();
            }
        } else {
            throw new ExpenditureNotRemovableException();
        }
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

    /**
     * Returns true if the list contains only unique expenditures.
     */
    private boolean expendituresAreUnique(List<Expenditure> occurrence) {
        for (int i = 0; i < occurrence.size() - 1; i++) {
            for (int j = i + 1; j < occurrence.size(); j++) {
                if (occurrence.get(i).isSameExpenditure(occurrence.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
