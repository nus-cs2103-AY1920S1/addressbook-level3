package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expense.exceptions.DuplicateEventException;

/**
 * A list of expenses that enforces uniqueness between its elements and does not allow nulls.
 * A expense is considered unique by comparing using {@code Expense#isSameExpense(Expense)}.
 * As such, adding and updating of expenses uses Expense#isSameExpense(Expense) for equality so
 * as to ensure that the expense being added or updated is unique in terms of identity in
 * the UniqueExpenseList. However, the removal of a expense uses Expense#equals(Object) so
 * as to ensure that the expense with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent expense as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Adds a expense to the list.
     * The expense must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException(); // rmb to add DuplicateEventException
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the list.
     * The expense identity of {@code editedExpense} must not be the same as another existing expense in the list.
     */
    //    IMPLEMENT AFTER WK 7
    //    public void setExpense(Expense target, Expense editedExpense) {
    //        requireAllNonNull(target, editedExpense);
    //
    //        int index = internalList.indexOf(target);
    //        if (index == -1) {
    //            throw new ExpenseNotFoundException();
    //        }
    //
    //        if (!target.isSameExpense(editedExpense) && contains(editedExpense)) {
    //            throw new DuplicateExpenseException();
    //        }
    //
    //        internalList.set(index, editedExpense);
    //    } IMPLEMENT AFTER WK 7

    /**
     * Removes the equivalent expense from the list.
     * The expense must exist in the list.
     */
    //    IMPLEMENT AFTER WK 7
    //    public void remove(Expense toRemove) {
    //        requireNonNull(toRemove);
    //        if (!internalList.remove(toRemove)) {
    //            throw new ExpenseNotFoundException();
    //        }
    //    } IMPLEMENT AFTER WK 7

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }

        internalList.setAll(events);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code events} contains only unique events.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

