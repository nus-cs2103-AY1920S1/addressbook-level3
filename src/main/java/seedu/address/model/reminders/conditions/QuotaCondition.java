package seedu.address.model.reminders.conditions;

import java.util.function.Predicate;

import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;
/**
 * Condition is met when entry amount is greater or equal to specified quota.
 */
public class QuotaCondition extends Condition {
    private long quota;
    private Predicate<Entry> quotaPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            return entry.getAmount().value >= quota;
        }
    };
    public QuotaCondition(Description desc, Long quota) {
        super(desc);
        this.quota = quota;
    }
    public long getQuota() {
        return quota;
    }
}
