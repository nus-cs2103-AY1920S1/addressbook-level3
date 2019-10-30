package seedu.address.model.reminders.conditions;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.exceptions.DuplicateEntryException;
import seedu.address.model.person.exceptions.EntryNotFoundException;
import seedu.address.model.reminders.Reminder;

/**
 * Instantiated in addressbook. Ensures that conditions are kept up to date.
 */
public class ConditionsManager implements Iterable<Condition> {
    private final ObservableList<Condition> generalConditionsList = FXCollections.observableArrayList();
    private final ObservableList<Condition> generalConditionsUnmodifiableList =
            FXCollections.unmodifiableObservableList(generalConditionsList);
    /**
     * When an object of type subclass of Entry is added, the conditions in the generalConditionList
     * have to be updated to see if any conditions are met.
     * @param beingAdded
     * @return
     */
    public void addEntryUpdate(Entry beingAdded) {
        for (Condition condition : generalConditionsList) {
            condition.addEntryUpdate(beingAdded);
        }
    }
    /**
     * When an object of type subclass of Entry is removed, the conditions in the generalConditionList
     * have to be updated to see if any conditions are met.
     * @param beingRemove
     * @return
     */
    public void deleteEntryUpdate(Entry beingRemove) {
        for (Condition condition : generalConditionsList) {
            condition.removeEntryUpdate(beingRemove);
        }
    }
    /**
     * When an object of type subclass of Entry is replaced by another, the conditions in the generalConditionList
     * have to be updated to see if any conditions are met.
     * @param beingRemove
     * @return
     */
    public void setEntryUpdate(Entry beingRemove, Entry beingAdded) {
        if (beingRemove.getTracker().isPresent()) {
            EntrySpecificCondition entryCondition = beingAdded.getTracker().get();
            beingAdded.setTracker(entryCondition);
            entryCondition.update();
        }
        deleteEntryUpdate(beingRemove);
        addEntryUpdate(beingAdded);
    }
    /**
     * Returns true if the list contains an equivalent condition as the given argument.
     */
    public boolean contains(Condition toCheck) {
        requireNonNull(toCheck);
        return generalConditionsList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Condition toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEntryException();
        }
        generalConditionsList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setCondition(Condition target, Condition editedCondition) {
        requireAllNonNull(target, editedCondition);
        assert(generalConditionsList.contains(editedCondition));
        int index = generalConditionsList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }
        generalConditionsList.remove(editedCondition);
        PropertyChangeListener[] listeners = target.getSupport().getPropertyChangeListeners();
        for (PropertyChangeListener listener : listeners) {
            Reminder reminder = (Reminder) listener;
            reminder.removeCondition(target);
            reminder.addCondition(editedCondition);
        }
        generalConditionsList.set(index, editedCondition);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Condition toRemove) {
        requireNonNull(toRemove);
        if (!generalConditionsList.contains(toRemove)) {
            throw new EntryNotFoundException();
        }
        PropertyChangeListener[] listeners = toRemove.getSupport().getPropertyChangeListeners();
        for (PropertyChangeListener listener : listeners) {
            Reminder reminder = (Reminder) listener;
            reminder.removeCondition(toRemove);
        }
        generalConditionsList.remove(toRemove);
    }

    public void setEntries(ConditionsManager replacement) {
        requireNonNull(replacement);
        generalConditionsList.setAll(replacement.generalConditionsList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEntries(List<Condition> entries) {
        requireAllNonNull(entries);
        generalConditionsList.setAll(entries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Condition> asUnmodifiableObservableList() {
        return generalConditionsUnmodifiableList;
    }



    @Override
    public Iterator<Condition> iterator() {
        return generalConditionsList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConditionsManager // instanceof handles nulls
                && generalConditionsList.equals(((ConditionsManager) other).generalConditionsList));
    }

    @Override
    public int hashCode() {
        return generalConditionsList.hashCode();
    }
}
