package seedu.address.model.reminders.conditions;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

import seedu.address.model.person.Entry;

/**
 * Implements wish reminder
 */
public class EntrySpecificCondition extends Condition {
    private Entry targetEntry;
    private Period bufferPeriod;
    private Period timeBeforeDeadline;
    private Predicate<Entry> entrySpecificPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            assert(targetEntry == entry);
            LocalDate currDate = LocalDate.now();
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
