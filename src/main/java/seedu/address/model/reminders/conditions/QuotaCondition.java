package seedu.address.model.reminders.conditions;

import java.util.function.Predicate;

import seedu.address.model.person.Entry;
/**
 * Condition is met when entry amount is greater or equal to specified quota.
 */
public class QuotaCondition extends Condition {
    private double quota;
    private Predicate<Entry> quotaPredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            return entry.getAmount().value >= quota;
        }
    };
    public QuotaCondition(Double quota) {
        super("Quota Condition");
        this.quota = quota;
    }
    public double getQuota() {
        return quota;
    }
}
