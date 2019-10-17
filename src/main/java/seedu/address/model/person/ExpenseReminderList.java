package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.exceptions.DuplicateEntryException;
import seedu.address.model.person.exceptions.EntryNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class ExpenseReminderList implements Iterable<ExpenseReminder> {

    private final ObservableList<ExpenseReminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<ExpenseReminder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ExpenseReminder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameReminder);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(ExpenseReminder toAdd) {
        requireNonNull(toAdd);

        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setExpenseReminder(ExpenseReminder target, ExpenseReminder editedExpenseReminder) {
        requireAllNonNull(target, editedExpenseReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }

        if (!target.equals(editedExpenseReminder) && contains(editedExpenseReminder)) {
            throw new DuplicateEntryException();
        }

        internalList.set(index, editedExpenseReminder);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(ExpenseReminder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntries(ExpenseReminderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEntries(List<ExpenseReminder> entries) {
        requireAllNonNull(entries);

        internalList.setAll(entries);
    }

    /**
     * updates the status of all reminders in ExpenseReminderList
     */
    public void updateList() {
        for (ExpenseReminder reminder : internalList) {
            reminder.updateStatus();
        }
    }

    /**
     * Get list of reminders to be displayed on main page.
     */
    public ObservableList<ExpenseReminder> getDisplay() {
        FilteredList<ExpenseReminder> displayList = new FilteredList<>(this.asUnmodifiableObservableList());
        displayList.setPredicate(new ExpenseReminderIsActive());
        return displayList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ExpenseReminder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ExpenseReminder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseReminderList // instanceof handles nulls
                && internalList.equals(((ExpenseReminderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

/**
 * Predicate to filter reminders to be displayed.
 */
class ExpenseReminderIsActive implements Predicate<ExpenseReminder> {
    @Override
    public boolean test(ExpenseReminder entry) {
        return entry.getStatus();
    }
}
