package seedu.address.model.reminders.conditions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

import seedu.address.model.person.Entry;

/**
 * Implements entry specific condition.
 */
public class EntrySpecificCondition extends Condition implements PropertyChangeListener {
    private Entry targetEntry;
    private Period bufferPeriod;
    private Period timeBeforeDeadline;
    private LocalDate currDate;
    private Predicate<Entry> entrySpecificPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            assert(targetEntry == entry);
            timeBeforeDeadline = Period.between(currDate, targetEntry.getDate().getDate());
            return !((bufferPeriod.minus(timeBeforeDeadline)).isNegative());
        }
    };
    public EntrySpecificCondition(Entry entry, Period bufferPeriod) {
        super("Entry Specific Condition");
        this.targetEntry = entry;
        this.bufferPeriod = bufferPeriod;
        super.setPred(entrySpecificPredicate);
    }

    public Entry getEntry() {
        return targetEntry;
    }
    public void setEntry(Entry entry) {
        targetEntry = entry;
    }

    /**
     * TimeUtil periodically updates currDate,
     * which in turn triggers condition to call propertyChange and update reminder.
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        assert(evt.getPropertyName().equals("currDate"));
        currDate = (LocalDate) evt.getNewValue();
        addEntryUpdate(targetEntry);
    }

    @Override
    /**
     * checks if condition is met. Triggered when entry is added/ entry is replacing another entry.
     * @param entry
     */
    public void addEntryUpdate(Entry entry) {
        Period oldPeriod = timeBeforeDeadline;
        if (getPred().test(entry)) {
            getSupport().firePropertyChange("timeBeforeDeadline", oldPeriod, timeBeforeDeadline);
        }
    }
    /**
     * Wrapper method for addEntryUpdate(Entry entry)
     */
    public void update() {
        addEntryUpdate(targetEntry);
    }
}
